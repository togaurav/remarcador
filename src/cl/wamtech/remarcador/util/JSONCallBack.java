package cl.wamtech.remarcador.util;

import org.json.JSONObject;

/**
 * 
 * @author vutreras
 *
 */
public interface JSONCallBack {

	/**
	 * metodo invocado por el util parser de json
	 * @param jsonObject - Objeto json
	 * @param bean - bean creado desde el objeto json
	 * @return bean creado desde el objeto json
	 * @throws Exception
	 */
	Object process(JSONObject jsonObject, Object bean) throws Exception;
}
