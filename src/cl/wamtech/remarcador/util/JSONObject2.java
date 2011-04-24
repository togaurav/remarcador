package cl.wamtech.remarcador.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Extiende a JSONObjet agregando mas metodos utiles. 
 * @author vutreras
 *
 */
public class JSONObject2 extends JSONObject implements Cloneable{

	public JSONObject2() {
		super();
	}

	public JSONObject2(JSONTokener x) throws JSONException {
		super(x);
	}

	public JSONObject2(Map map) {
		super(map);
	}

	public JSONObject2(String string) throws JSONException {
		super(string);
	}

	public JSONObject2(JSONObject arg0, String[] arg1) throws JSONException {
		super(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * @see org.json.JSONObject#get(java.lang.String)
	 */
	public Object get(String key) {
		try {
			if (isNull(key)) {
				return null;
			} else {
				return super.get(key);
			}
		} catch(Exception ex) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws JSONException
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		try {
			if (isNull(key)) {
				return defaultValue;
			} else {
				return super.getBoolean(key);
			}
		} catch(Exception ex) {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws JSONException
	 */
	public double getDouble(String key, double defaultValue) {
		try {
			if (isNull(key)) {
				return defaultValue;
			} else {
				return super.getDouble(key);
			}
		} catch(Exception ex) {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws JSONException
	 */
	public int getInt(String key, int defaultValue) {
		try {
			if (isNull(key)) {
				return defaultValue;
			} else {
				return super.getInt(key);
			}
		} catch(Exception ex) {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws JSONException
	 */
	public long getLong(String key, long defaultValue) {
		try {
			if (isNull(key)) {
				return defaultValue;
			} else {
				return super.getLong(key);
			}
		} catch(Exception ex) {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws JSONException
	 */
	public String getString(String key, String defaultValue) {
		try {
			if (isNull(key)) {
				return defaultValue;
			} else {
				return super.getString(key);
			}
		} catch(Exception ex) {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param emptyArray true si se desea retornar un objeto vacio si el valor no existe
	 * @return
	 * @throws JSONException
	 */
	public JSONArray getJSONArray(String key, boolean emptyArray) {
		try {
			if (isNull(key)) {
				return (emptyArray) ? new JSONArray() : null;
			} else {
				return super.getJSONArray(key);
			}
		} catch(Exception ex) {
			return (emptyArray) ? new JSONArray() : null;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param emptyObject true si se desea retornar un objeto vacio si el valor no existe
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getJSONObject(String key, boolean emptyObject) {
		try {
			if (isNull(key)) {
				return (emptyObject) ? new JSONObject2() : null;
			} else {
				return super.getJSONObject(key);
			}
		} catch(Exception ex) {
			return (emptyObject) ? new JSONObject2() : null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
	    return new JSONObject2((HashMap)this.toHashMap().clone());
	}
}
