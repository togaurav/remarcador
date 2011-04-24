/**
 * 
 */
package cl.wamtech.remarcador.dao;

import java.util.List;
import java.util.Map;

import cl.wamtech.remarcador.util.IPersistente;




/**
 * @author jorge
 *
 */
public interface RemarcadorDao {
	
	public static String REMARCADOR_CALCULADO_INICIAL = "get.remarcador.calculado.inicial";
	public static String REMARCADOR_CALCULADO_FINAL = "get.remarcador.calculado.final";
	public static String REMARCADORES_MEDIDOS_POR_PERIODO = "get.remarcadores.medidos.por.periodo";
	public static String DETALLE_REMARCADOR = "get.detalle.remarcador";
	public static String VARIABLE_POR_REMARCADOR = "get.variable.por.remarcador";
	public static String USUARIO_LOGIN = "get.usuario.login";
	
	/**
	 * 
	 * @param ipersistente
	 */
	public void creaActualiza(IPersistente ipersistente);
	
	/**
	 * 
	 * @param object
	 * @param criterios
	 * @return
	 */
	public List getCriterioObject(Class clase, Map criterios);
	
	/**
	 * 
	 * @param ipersistente
	 */
	public void eliminar(IPersistente ipersistente);
	
	/**
	 * 
	 * @param clase
	 * @param id
	 * @return
	 */
	public Object getObject(Class clase, int id);
	
	/**
	 * 
	 * @param nombreQuery
	 * @param parametros
	 * @param json
	 * @return
	 */
	public Object getObject(String nombreQuery, Map<String, Object> parametros, boolean json);
	
	/**
	 * 
	 * @param nombreQuery
	 * @param parametros
	 * @param desde
	 * @param cantidad
	 * @param json
	 * @return
	 */
	public List getObjects(String nombreQuery, Map parametros, int desde, int cantidad, boolean json);
}
