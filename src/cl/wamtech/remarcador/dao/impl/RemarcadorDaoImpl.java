package cl.wamtech.remarcador.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import cl.wamtech.remarcador.dao.RemarcadorDao;
import cl.wamtech.remarcador.util.IPersistente;
import cl.wamtech.remarcador.util.IbatisBaseDao;

/**
 * @author jorge
 *
 */
public class RemarcadorDaoImpl extends IbatisBaseDao implements RemarcadorDao {
	
	/*
	 * (non-Javadoc)
	 * @see cl.wamtech.remarcador.dao.RemarcadorDao#creaActualiza(cl.wamtech.remarcador.util.IPersistente)
	 */
	public void creaActualiza(IPersistente ipersistente) {
		super.insertOrUpdateObject(ipersistente);
	}

	/*
	 * (non-Javadoc)
	 * @see cl.wamtech.remarcador.util.IbatisBaseDao#getCriterioObject(java.lang.Class, java.util.Map)
	 */
	public List getCriterioObject(Class clase, Map criterios) {
		return super.getCriterioObject(clase, criterios);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cl.wamtech.remarcador.dao.RemarcadorDao#eliminar(cl.wamtech.remarcador.util.IPersistente)
	 */
	public void eliminar(IPersistente ipersistente) {
		this.deleteObject(ipersistente);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cl.wamtech.remarcador.util.IbatisBaseDao#getObject(java.lang.Class, int)
	 */
	public Object getObject(Class clase, int id) {
		return this.getObject(clase, id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cl.wamtech.remarcador.dao.RemarcadorDao#getObject(java.lang.String, java.util.Map, boolean)
	 */
	public Object getObject(String nombreQuery, Map parametros, boolean json) {
		Object o = (Object) super.getSqlMapClientTemplate().queryForObject(nombreQuery, parametros);
		return returnObj(json, o);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cl.wamtech.remarcador.dao.RemarcadorDao#getObjects(java.lang.String, java.util.Map, int, int, boolean)
	 */
	public List getObjects(String nombreQuery, Map parametros, int desde, int cantidad, boolean json) {
		List o = super.getSqlMapClientTemplate().queryForList(nombreQuery, parametros, desde, cantidad);
		return transformar(o, json);
	}

	/**
	 * 
	 * @param json
	 * @param o
	 * @return
	 */
	private Object returnObj(boolean json, Object o) {
		if (o == null) return null;
		
		if (json) {
			return new JSONObject((Map)o);
		} else {
			return o;
		}
	}
	
	/**
	 * 
	 * @param o
	 * @param json
	 * @return
	 */
	private List transformar(List o, boolean json) {
		
		if (o == null) return null;
		
		if (json) {

			List lista = (List)o;
			
			if (lista != null) {
			
				JSONArray array = new JSONArray();
				
				for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();
					array.add(new JSONObject((Map)object));
				}
				
				return array;
				
			} else {
				return null;
			}

		} else {
			return o;
		}
	}
	
}