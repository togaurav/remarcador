/**
 * 
 */
package cl.wamtech.remarcador.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * Clase abstracta de servicios comunes para la capa dao usando ibatis.
 * @author vutreras
 *
 */
public class IbatisBaseDao {
	
	protected static final Log log = LogFactory.getLog(IbatisBaseDao.class);

	protected SqlMapClientTemplate sqlMapClientTemplate;
	
	protected JdbcTemplate jdbcTemplate;
	
	protected DataSource dataSource;
	
	/**
	 * 
	 */
	public IbatisBaseDao() {
		super();
	}

	/**
	 * @return the sqlMapClientTemplate
	 */
	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}



	/**
	 * @param sqlMapClientTemplate the sqlMapClientTemplate to set
	 */
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	
	
	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		
		if (this.jdbcTemplate == null) {
			if (this.getDataSource() == null) {
				throw new IllegalArgumentException("Debe definir un DataSource.");
			}
			this.jdbcTemplate = new JdbcTemplate(this.getDataSource());
		}
		return this.jdbcTemplate;
	}



	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}



	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}



	/**
	 * 
	 * @param clase
	 * @return
	 */
	protected String getShortClassName(Class clase){
		String largeName = clase.getName();
		int index = largeName.lastIndexOf('.');
		return largeName.substring(index + 1);
	}

	/**
	 * retorna true si el objeto es persistente.
	 * @param ip
	 * @return
	 */
	public boolean isPersistente(IPersistente ip) {
		return (ip.getId() > -1);
	}
	
	/**
	 * 
	 * @param tabla
	 * @param columna
	 * @param valor
	 * @param columnaId
	 * @param id
	 * @param tipo
	 * @return
	 */
	public boolean updateColumna(String tabla, String columna, Object valor, String columnaId, Object id, String tipo){
		
		if("string".equals(tipo) || "char".equals(tipo)){
			valor = "'" + valor  + "'";
		} else if (valor != null){
			valor = valor.toString().replaceAll(",", ".");
		}
		
		String where = "";
		
		if (columnaId != null) {
			where = " WHERE " + columnaId + " = " + id;
		}
		
		String sql = "UPDATE " + tabla + " SET " + columna + " = " + valor + where;
		if (log.isDebugEnabled()) {
			log.debug("Params[tabla: " + tabla + ", columna: " + columna + ", valor: " + valor + ", columnaId: " + columnaId + ", id: " + id + ", tipo: " + tipo + "]");
		}
		if (log.isDebugEnabled()) {
			log.debug("realizando update: " + sql);
		}
		return this.getJdbcTemplate().update(sql) == 1;
	}
	
	/**
	 * inserta un objeto
	 * @param ipersistente
	 */
	public void insertObject(IPersistente ipersistente) {
		if (log.isDebugEnabled()) {
			log.debug("Insertando el objeto: " + ipersistente);
		}
		String name = this.getShortClassName(ipersistente.getClass());
		Object key = this.getSqlMapClientTemplate().insert("insert." + name, ipersistente);
		if(key != null){
			try {
				ipersistente.setId(Integer.parseInt(key.toString()));
			} catch(NumberFormatException ex) {
				
			}
		}
	}
	
	/**
	 * actualiza un objeto
	 * @param ipersistente
	 */
	public boolean updateObject(IPersistente ipersistente) {
		if (log.isDebugEnabled()) {
			log.debug("Actualizando el objeto: " + ipersistente);
		}
		String name = this.getShortClassName(ipersistente.getClass());
		return this.getSqlMapClientTemplate().update("update." + name, ipersistente) > -1;
	}
	
	/**
	 * inserta o actualiza un objeto dependiendo de su id
	 * @param ipersistente
	 */
	public void insertOrUpdateObject(IPersistente ipersistente) {
		this.insertOrUpdateObject(ipersistente, false);
	}
	
	/**
	 * retorna el objeto con el mismo id desde la lista pasada como parametro
	 * @param l
	 * @param id
	 * @return
	 */
	public boolean isObjectSelf(List l, int id) {
		boolean is = false;
		for (Iterator iterator = l.iterator(); !is && iterator.hasNext();) {
			IPersistente p = (IPersistente) iterator.next();
			is = id == p.getId();
		}
		return is;
	}
	
	/**
	 * inserta o actualiza un objeto dependiendo de su id validando si ya existe.
	 * @param ipersistente
	 */
	public void insertOrUpdateObject(IPersistente ipersistente, boolean validaExistencia) throws DuplicateRegisterException{
		if (!this.isPersistente(ipersistente)) {
			if (validaExistencia) {
				List lst = this.getEqualsObject(ipersistente);
				if (lst != null && !lst.isEmpty()) {
					throw new DuplicateRegisterException("El objeto ya existe en el sistema.");
				} else {
					this.insertObject(ipersistente);
				}
			} else {
				this.insertObject(ipersistente);
			}
		} else {
			if (validaExistencia) {
				List lst = this.getEqualsObject(ipersistente);
				if (lst != null && !lst.isEmpty() && !isObjectSelf(lst, ipersistente.getId()) ) {
					throw new DuplicateRegisterException("El objeto ya existe en el sistema.");
				} else {
					this.updateObject(ipersistente);
				}
			} else {
				this.updateObject(ipersistente);
			}
		}
	}
	
	/**
	 * borra un objeto
	 * @param ipersistente
	 */
	public boolean deleteObject(IPersistente ipersistente) {
		String name = this.getShortClassName(ipersistente.getClass());
		if (log.isDebugEnabled()) {
			log.debug("Borrando el objeto: " + name + "(" + ipersistente.getId() + ")");
		}
		return this.getSqlMapClientTemplate().delete("delete." + name, ipersistente) > 0;
	}
	
	/**
	 * borra un objeto validando sus dependencias, si el objeto tiene dependencias no puede ser borrado
	 * @param ipersistente
	 */
	public boolean deleteObject(IPersistente ipersistente, boolean validaDependencia) throws DataIntegrityViolationException{
		if (this.isPersistente(ipersistente)) {
			if (validaDependencia) {
				Integer count = this.getCountDependsObject(ipersistente);
				if (count != null && count.intValue() > 0) {
					String name = this.getShortClassName(ipersistente.getClass());
					if (log.isDebugEnabled()) {
						log.debug("El objeto " + name + " tiene un total de [" + count + "] dependencias y no puede ser borrado.");
					}
					throw new DataIntegrityViolationException("El objeto " + name + "(" + ipersistente.getId() + ") no puede ser borrado, tiene dependencia de datos.");
				} else {
					return this.deleteObject(ipersistente);
				}
			} else {
				return this.deleteObject(ipersistente);
			}
		} else {
			return false;
		}
	}
	
	
	/**
	 * retorna el total de objetos similares al objeto pasado como parametro
	 * @param ipersistente
	 * @return
	 */
	public Integer getCountDependsObject(IPersistente ipersistente) {
		
		String name = this.getShortClassName(ipersistente.getClass());
		
		if (log.isDebugEnabled()) {
			log.debug("Buscando los objetos dependientes de: " + name + "(" + ipersistente.getId() + ")");
		}
		
		try {
			Integer count = (Integer)this.getSqlMapClientTemplate().queryForObject("get.count.depends." + name, ipersistente);
			if (log.isDebugEnabled()) {
				log.debug("El objeto: " + name + "(" + ipersistente.getId() + "), tiene: " + count + " dependencias");
			}
			return count;
		} catch(Exception ex) {
			log.warn("No se logro encontrar el mapeo, causa: " + ex.getMessage());
			return null;
		} 
	}
	
	/**
	 * busca todos los objetos iguales
	 * @param ipersistente
	 * @return
	 */
	public List getEqualsObject(IPersistente ipersistente) {
		
		String name = this.getShortClassName(ipersistente.getClass());
		
		if (log.isDebugEnabled()) {
			log.debug("Buscando objetos equals al objeto: " + name + "(" + ipersistente.getId() + ")");
		}
		
		try {
			
			Map criterios = this.objectToCriterios(ipersistente);

			if (log.isInfoEnabled()) {
				log.info("Buscando el objeto con los criterios: " + criterios);
			}
			
			List lstEquals = this.getSqlMapClientTemplate().queryForList("get.equals." + name, criterios);

			if (log.isDebugEnabled()) {
				log.debug("El objeto: " + name + "(" + ipersistente.getId() + "), tiene los siguientes objetos iguales: " + lstEquals);
			}
			
			return lstEquals;
		} catch(Exception ex) {
			log.warn("No se logro encontrar el mapeo, causa: " + ex.getMessage());
			return null;
		} 
	}
	
	/**
	 * busca un objeto por su id
	 * @param clase
	 * @param id
	 * @return
	 */
	public Object getObject(Class clase, int id) {
		String name = this.getShortClassName(clase);
		if (log.isDebugEnabled()) {
			log.debug("Buscando el objeto: " + name + "(" + id + ")");
		}
		try {
			Object obj = this.getSqlMapClientTemplate().queryForObject("get." + name, new Integer(id));
			if (log.isDebugEnabled()) {
				log.debug("El objeto encontrado para la clase: " + name + " y el id: " + id + " fue: " + obj);
			}
			return obj; 
		} catch(Exception ex) {
			log.warn("No se logro encontrar el mapeo, causa: " + ex.getMessage());
			return null;
		} 
	}
	
	/**
	 * busca los objetos por criterios de busqueda
	 * @param clase
	 * @param id
	 * @return
	 */
	public List getCriterioObject(Class clase, Map criterios) {
		String name = this.getShortClassName(clase);
		if (log.isInfoEnabled()) {
			log.info("Buscando el listado de objetos: " + name + " con criterios: " + criterios);
		}
		try {
			Map criteriosFiltrados = filtrarCriterios(criterios);
			if (log.isInfoEnabled()) {
				log.info("criterios filtrados: " + criteriosFiltrados);
			}	
			List lst = this.getSqlMapClientTemplate().queryForList("get.criterio." + name, criteriosFiltrados);
			if (log.isDebugEnabled()) {
				log.debug("El listado de objetos encontrados para la clase: " + name + " y criterios: " + criterios+ " fue: " + lst);
			}
			return lst; 
		} catch(Exception ex) {
		    ex.printStackTrace();
			log.warn("No se logro encontrar el mapeo, causa: " + ex.getMessage());
			return null;
		} 
	}
	
	/**
	 * busca el listado de objetos
	 * @param clase
	 * @return
	 */
	public List getListObject(Class clase) {
		String name = this.getShortClassName(clase);
		if (log.isDebugEnabled()) {
			log.debug("Buscando el listado de objetos: " + name);
		}
		return this.getSqlMapClientTemplate().queryForList("get.list." + name,null);
	}
	
	/**
	 * busca el listado de objetos con paginado 
	 * @param clase
	 * @param desde
	 * @param cantidad
	 * @return
	 */
	public List getListObject(Class clase, int desde, int cantidad) {
		String name = this.getShortClassName(clase);
		if (log.isDebugEnabled()) {
			log.debug("Buscando el listado de objetos: " + name + ", desde: " + desde + ", cantidad: " + cantidad);
		}
		return this.getSqlMapClientTemplate().queryForList("get.list." + name,null, desde, cantidad);
	}

	/**
	 * 
	 * @param clase
	 * @return
	 */
	public int getCountListObject(Class clase) {
		String name = this.getShortClassName(clase);
		if (log.isDebugEnabled()) {
			log.debug("Buscando el total de objetos: " + name);
		}

		try {
			Integer count = (Integer)this.getSqlMapClientTemplate().queryForObject("get.count.list." + name,null);
			return count.intValue(); 
		} catch(Exception ex) {
			log.warn("No se logro encontrar el mapeo, causa: " + ex.getMessage());
			return 0;
		} 
	}
	
	/**
	 * 
	 * @param ip1
	 * @param ip2
	 * @return
	 */
	public Object getRelObject(IPersistente ip1, IPersistente ip2, String extraIdQuery) {
		
		if (ip1 == null) {
			throw new IllegalArgumentException("ip1 es null");
		}
		
		if (ip2 == null) {
			throw new IllegalArgumentException("ip2 es null");
		}
		
		if (extraIdQuery == null){
			extraIdQuery = "";
		}
		
		if (log.isDebugEnabled()) {
			log.debug("Buscando relacion entre los objetos: " + ip1 + " y " + ip2);
		}
		String nameIp1 = this.getShortClassName(ip1.getClass());
		String nameIp2 = this.getShortClassName(ip2.getClass());
		Map params = new HashMap();
		params.put("id1", new Integer(ip1.getId()));
		params.put("id2", new Integer(ip2.getId()));
		return this.getSqlMapClientTemplate().queryForObject("get.rel." + nameIp1 + "." + nameIp2 + extraIdQuery, params);
	}	
	
	
	/**
	 * 
	 * @param ip1
	 * @param ip2
	 * @return
	 */
	public boolean deleteRelObject(IPersistente ip1, IPersistente ip2, String extraIdQuery) {
		
		if (ip1 == null) {
			throw new IllegalArgumentException("ip1 es null");
		}
		
		if (ip2 == null) {
			throw new IllegalArgumentException("ip2 es null");
		}
		
		if (extraIdQuery == null){
			extraIdQuery = "";
		}
		
		if (log.isDebugEnabled()) {
			log.debug("Borrando relacion entre los objetos: " + ip1 + " y " + ip2);
		}
		String nameIp1 = this.getShortClassName(ip1.getClass());
		String nameIp2 = this.getShortClassName(ip2.getClass());
		Map params = new HashMap();
		params.put("id1", new Integer(ip1.getId()));
		params.put("id2", new Integer(ip2.getId()));
		return this.getSqlMapClientTemplate().delete("delete.rel." + nameIp1 + "." + nameIp2 + extraIdQuery, params) > 0;
	}	
	
	
	/**
	 * 
	 * @param ip1
	 * @param ip2
	 * @return
	 */
	public void insertRelObject(IPersistente ip1, IPersistente ip2, String extraIdQuery) {
		
		if (ip1 == null) {
			throw new IllegalArgumentException("ip1 es null");
		}
		
		if (ip2 == null) {
			throw new IllegalArgumentException("ip2 es null");
		}
		
		if (extraIdQuery == null){
			extraIdQuery = "";
		}
		
		if (!this.isPersistente(ip1)) {
			this.insertOrUpdateObject(ip1);
		}
		
		if (!this.isPersistente(ip2)) {
			this.insertOrUpdateObject(ip2);
		}

		if (log.isDebugEnabled()) {
			log.debug("Creando relacion entre los objetos: " + ip1 + " y " + ip2);
		}
		String nameIp1 = this.getShortClassName(ip1.getClass());
		String nameIp2 = this.getShortClassName(ip2.getClass());
		Map params = new HashMap();
		params.put("id1", new Integer(ip1.getId()));
		params.put("id2", new Integer(ip2.getId()));
		this.getSqlMapClientTemplate().insert("insert.rel." + nameIp1 + "." + nameIp2 + extraIdQuery, params);
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private Map objectToCriterios(Object obj) {
		
		if (obj == null) return null;
		
		Method[] metodos = obj.getClass().getMethods();
		
		if (metodos != null) {
			Map criterios = new HashMap();
			for (int i = 0; i < metodos.length; i++) {
				Method m = metodos[i];
				String name = m.getName(); 
				boolean isGet = name.startsWith("get");
				boolean isIs = name.startsWith("is");
				
				if (isGet || isIs) {
					Object valor = null;
					try {
						valor = m.invoke(obj);
					} catch (Exception e) {
					} 			
					if (m.getReturnType().isPrimitive() || !(valor instanceof IPersistente)) {
						name = Character.toLowerCase(m.getName().charAt(isGet ? 3 : 2)) + name.substring(isGet ? 4 : 3);
						if (valor instanceof String){
							criterios.put(name, (valor != null) ? valor.toString().trim().toUpperCase() : null);
						} else {
							criterios.put(name, valor);
						}
					} else {
						
						Map criteriosObjHijo = objectToCriterios(valor);
						String nameClassObjHijo = this.getShortClassName(valor.getClass());
						
						Map newCriteriosObjHijo = new HashMap();
						
						for (Iterator iterator = criteriosObjHijo.keySet().iterator(); iterator.hasNext();) {
							String key = (String) iterator.next();
							newCriteriosObjHijo.put(nameClassObjHijo + "_" + key, criteriosObjHijo.get(key));
						}
					
						criterios.putAll(newCriteriosObjHijo);
					}
				}
			}
			return criterios;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param criterios
	 * @return
	 */
	protected Map filtrarCriterios(Map criterios) {
		if (criterios != null) {
			Set keys = criterios.keySet();
			if (keys != null && !keys.isEmpty()) {
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object valor = criterios.get(key);
					if (valor instanceof String) {
						criterios.put(key, valor.toString().trim().toUpperCase());
					}
				}
			}
		}
		return criterios;
	}
}
