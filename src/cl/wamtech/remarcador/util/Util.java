/**
 * 
 */
package cl.wamtech.remarcador.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author vutreras
 *
 */
public class Util {

	protected static final Log log = LogFactory.getLog(Util.class);
	
	public static final int REGLA_DECIMAL_TO_STRING = 1;
	
	public static final int REGLA_EXCLUIR_NULL = 8;
	
	public static final int REGLA_EXCLUIR_VACIO = 18;
	
	public static final int REGLA_EXCLUIR_NUMBER = 28;
	
	private static JsonConfig jsonConfig = new JsonConfig();
	
	 private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	 
	static{
		
		jsonConfig.setJsonPropertyFilter( new PropertyFilter(){  
				public boolean apply( Object source, String name, Object value ) {  
					if( value != null){// && Number.class.isAssignableFrom( value.getClass() ) ){  
						return false;  
					}  
					return true;  
				}  
		});
		
		log.info("----------------------- Init UTIL --------------------");
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"MM/dd/yyyy"}) );
	}
	
	private static Util instance;
	
	/**
	 * implementa singleton
	 * @return
	 */
	public synchronized static Util getInstance() {
		if (instance == null) {
			instance = new Util();
		}
		return instance;
	}
	
	/**
	 * es privado para implementar singleton
	 */
	private Util() {
	}

	/**
	 * retorna true la clase del parametro es del tipo Decimal
	 * @param tipo
	 * @return
	 */
	private static boolean isDecimal(Class tipo) {
		return (tipo.equals(Double.TYPE) || tipo.equals(Double.class) || tipo.equals(Float.TYPE) || tipo.equals(Float.class));
	}
	
	/**
	 * 
	 * @param arr
	 * @param beans
	 * @param reglas
	 * @return
	 */
	private static net.sf.json.JSONArray filter(net.sf.json.JSONArray arr, List beans, int reglas) {
		for (int i = 0; i < arr.size(); i++) {
			filter(arr.getJSONObject(i), beans.get(i), reglas);
		}
		return arr;
	}
	
	/**
	 * 
	 * @param jo
	 * @param bean
	 * @param reglas
	 * @return
	 */
	private static net.sf.json.JSONObject filter(net.sf.json.JSONObject jo, Object bean, int reglas) {
		
		if (jo == null) {
			throw new IllegalArgumentException("jo es null");
		}
		
		if (bean == null) {
			throw new IllegalArgumentException("bean es null");
		}
		
		Method[] m = bean.getClass().getMethods();
		
		for (int i = 0; i < m.length; i++) {
			
			String name = m[i].getName();

			if (name.startsWith("get")) {
			
				name = Character.toLowerCase(name.charAt(3)) + name.substring(4);
				
				if ("class".equals(name)) continue;
				
				Class tipo = m[i].getReturnType();
				Object valor =  null;

				try {
					Object[] o = null;
					valor = m[i].invoke(bean, o);
				} catch (Exception e) {
					e.printStackTrace();
				}

				int regla = reglas;
				
				switch (regla) {
					case REGLA_DECIMAL_TO_STRING:
						
						if (isDecimal(tipo)){
							jo.put(name, String.valueOf(valor));
						} else if (tipo.isAssignableFrom(List.class) || tipo.isAssignableFrom(Collection.class)) {
							
							List lst = (List)valor;
							JSONArray array = new JSONArray();
							
							for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
								Object v = (Object) iterator.next();
								Class tipo2 = v.getClass();
							
								if (isDecimal(tipo2)){
									array.add(String.valueOf(v));
								} else {
									array.add(v);
								}
								
							}
							
							jo.put(name, array);
						}
					
					break;
				}
			}
		}
		return jo;
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 * @throws JSONException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static JSONObject beanToJson(Object bean, boolean excluirNulos) throws JSONException{
		if (bean == null) {
			throw new IllegalArgumentException("bean es null");
		}
		net.sf.json.JSONObject jo = (excluirNulos) ? net.sf.json.JSONObject.fromObject(bean, jsonConfig) : net.sf.json.JSONObject.fromObject(bean);
		String json = String.valueOf(jo);
		return new JSONObject2(json);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 * @throws JSONException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static JSONObject beanToJson(Object bean, boolean excluirNulos, int reglas) throws JSONException{
		if (bean == null) {
			throw new IllegalArgumentException("bean es null");
		}
		net.sf.json.JSONObject jo = (excluirNulos) ? net.sf.json.JSONObject.fromObject(bean, jsonConfig) : net.sf.json.JSONObject.fromObject(bean);
		if (reglas != 0) {
			jo = filter(jo, bean, reglas);
		}
		String json = String.valueOf(jo);
		return new JSONObject2(json);
	}
	
	/**
	 * 
	 * @param json
	 * @param clase
	 * @return
	 */
	public static Object jsonToBean(String json, Class clase) {
		
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"MM/dd/yyyy"}) );
		
		return net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(json), clase);
	}
	
	/**
	 * 
	 * @param json
	 * @param clase
	 * @return
	 */
	public static Object jsonToBean(String json, Class clase, int reglas) {
		
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"MM/dd/yyyy"}) );

		JsonConfig jconf = new JsonConfig();
		
		switch (reglas) {
			case Util.REGLA_EXCLUIR_NULL:
				jconf.setJsonPropertyFilter( new PropertyFilter(){  
					public boolean apply( Object source, String name, Object value ) {  
						if( value != null){  
							return false;  
						}  
						return true;  
					}  
				});
			break;
			
			case Util.REGLA_EXCLUIR_VACIO:
				jconf.setJsonPropertyFilter( new PropertyFilter(){  
					public boolean apply( Object source, String name, Object value ) {  
						if(!"".equals(value)){  
							return false;  
						}  
						return true;  
					}  
				});
			break;

			case Util.REGLA_EXCLUIR_NULL | Util.REGLA_EXCLUIR_VACIO:
				jconf.setJsonPropertyFilter( new PropertyFilter(){  
					public boolean apply( Object source, String name, Object value ) {  
						if( value != null && !"".equals(value)){  
							return false;  
						}  
						return true;  
					}  
				});
			break;
		}
		
		return net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(json, jconf), clase);
	}
	
	/**
	 * 
	 * @param beans
	 * @return
	 */
	public static JSONArray listMapToJsonArray(Collection beans) {
		if (beans != null && !beans.isEmpty()) {
			JSONArray lst2 = new JSONArray();
			for (Iterator iterator = beans.iterator(); iterator.hasNext();) {
				Map jMap = (Map)iterator.next();
				lst2.add(new JSONObject2(jMap));
			}
			return lst2;
		} else {
			return new JSONArray();
		}
	}
	
	/**
	 * 
	 * @param beans
	 * @return
	 * @throws JSONException
	 */
	public static JSONArray collectionToJsonArray(Collection beans, boolean excluirNulos) throws JSONException{
		if (beans != null && !beans.isEmpty()) {
			
			net.sf.json.JSONArray arr = (excluirNulos) ? net.sf.json.JSONArray.fromObject(beans, jsonConfig) : net.sf.json.JSONArray.fromObject(beans);
			
			String json = String.valueOf(arr);
			return new JSONArray(json);
		} else {
			return new JSONArray();
		}
	}
	
	/**
	 * 
	 * @param beans
	 * @return
	 * @throws JSONException
	 */
	public static JSONArray listToJsonArray(List beans, boolean excluirNulos, int reglas) throws JSONException{
		if (beans != null && !beans.isEmpty()) {
			
			net.sf.json.JSONArray arr = (excluirNulos) ? net.sf.json.JSONArray.fromObject(beans, jsonConfig) : net.sf.json.JSONArray.fromObject(beans);
			
			if (reglas != 0) {
				arr = filter(arr, beans, reglas);
			}
			
			String json = String.valueOf(arr);
			return new JSONArray(json);
		} else {
			return new JSONArray();
		}
	}
	
	/**
	 * 
	 * @param json
	 * @param clase
	 * @return
	 */
	public static Collection jsonArrayToCollection(String json, Class clase) {
		return net.sf.json.JSONArray.toCollection(net.sf.json.JSONArray.fromObject(json), clase);
	}
	
	/**
	 * 
	 * @param json
	 * @param clase
	 * @return
	 */
	public static Collection jsonArrayToCollection(String json) {
		return net.sf.json.JSONArray.toCollection(net.sf.json.JSONArray.fromObject(json));
	}
	
	/**
	 * 
	 * @param json
	 * @param clase
	 * @return
	 * @throws JSONException 
	 */
	public static Object jsonToBean(String json, Class clase, JSONCallBack callBack) throws Exception {
		Object bean = net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(json), clase);
		JSONObject jsonObject = new JSONObject2(json);
		return callBack.process(jsonObject, bean); 
	}
	
	/**
	 * @deprecated
	 * @param fecha
	 * @return
	 */
	public static int stringToIntDate(String fecha) {
		if (fecha != null) {
			String[] s = fecha.split(DateUtil.DEFAULT_SEPARATOR_VIEW_FORMAT_DATE);
			return Integer.parseInt(s[2] + s[1] + s[0]);
		} else {
			return 0;
		}
	}
	
	/**
	 * @deprecated
	 * @param fecha
	 * @return
	 */
	public static String intToStringDate(int fecha) {
		if (fecha > 0) {
			String f = String.valueOf(fecha);
			String s = DateUtil.DEFAULT_SEPARATOR_VIEW_FORMAT_DATE;
			return (f.substring(6) + s + f.substring(4, 6) + s + f.substring(0, 4));
		} else {
			return null;
		}
	}
	
	/**
	 * @deprecated
	 * @param fecha
	 * @return
	 */
	public static String intToStringTime(int hora) {
		if (hora > 0) {
			String f = String.valueOf(hora);
			String s = DateUtil.DEFAULT_SEPARATOR_VIEW_FORMAT_TIME;
			return (f.substring(0,2) + s + f.substring(2,4) + s + f.substring(4,6));
		} else {
			return null;
		}
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public static Integer actualDateToInteger() {
		Calendar c = Calendar.getInstance();
		int anio = c.get(Calendar.YEAR);
		String mes = StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dia = StringUtils.leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0');
		return new Integer(anio + mes + dia);
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public static int actualDateToInt() {
		return actualDateToInteger().intValue();
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public static String actualDateToString() {
		return actualDateToInteger().toString();
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public static Integer actualTimeToInteger() {
		Calendar c = Calendar.getInstance();
		int hora = c.get(Calendar.HOUR_OF_DAY);
		String min = StringUtils.leftPad(String.valueOf(c.get(Calendar.MINUTE)), 2, '0');
		String seg = StringUtils.leftPad(String.valueOf(c.get(Calendar.SECOND)), 2, '0');
		return new Integer(hora + min + seg);
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public static int actualTimeToInt() {
		return actualTimeToInteger().intValue();
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public static String actualTimeToString() {
		return actualTimeToString(null);
	}
	
	/**
	 * @deprecated
	 * @see DateUtil.actualTimeToString
	 * @return
	 */
	public static String actualTimeToString(String separador) {
		Calendar c = Calendar.getInstance();
		String hora = StringUtils.leftPad(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), 2, '0');
		String min = StringUtils.leftPad(String.valueOf(c.get(Calendar.MINUTE)), 2, '0');
		String seg = StringUtils.leftPad(String.valueOf(c.get(Calendar.SECOND)), 2, '0');
		separador = (separador == null) ? ":" : separador;
		return hora + separador + min + separador + seg;
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public static Integer futuraDateToInteger(int dias) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 30);
		int anio = c.get(Calendar.YEAR);
		String mes = StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dia = StringUtils.leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0');
		return new Integer(anio + mes + dia);
	}
	
	/**
	 * @deprecated
	 * @param sFecha
	 * @param fecha
	 * @return
	 */
	public static int resuelveFecha(String sFecha, int fecha) {
		return (!StringUtils.isEmpty(sFecha)) ? stringToIntDate(sFecha) : fecha;
	}
	
	/**
	 * @deprecated
	 * @param fecha
	 * @param sFecha
	 * @return
	 */
	public static String resuelveFecha(int fecha, String sFecha) {
		return (fecha <= 0) ? sFecha :  intToStringDate(fecha);
	}
	
	/**
	 * Retorna la fecha en el formato dd MM yyyy del Timestamp pasado como parametro.
	 * @deprecated
	 * @param fecha
	 * @param separador separador de campos de la fecha.
	 * @return
	 */
	public static String formatDate(Date fecha, String formato){
		String retorno = null;
		if(fecha != null && formato != null){
			DateFormat formatter = new SimpleDateFormat(formato);
	        formatter.setTimeZone(TimeZone.getDefault());
			retorno = formatter.format(fecha);
		}
		return retorno;
	}
	
	/**
	 * Encoding base64
	 * @param src
	 * @return
	 */
	public static String encodeString(String src) {
		byte[] encodedBytes  = Base64.encodeBase64(src.getBytes());
		String sEncode = new String(encodedBytes);
		if (log.isDebugEnabled()){
			log.debug("string enconded: [" + sEncode.length() + "][" + sEncode + "]");
		}
		return sEncode;
	}
	
	/**
	 * decoding Base64
	 * @param src
	 * @return
	 */
	public static String decodeString(String src) {
		String decodedString = new String(Base64.decodeBase64(src.getBytes()));
		if (log.isDebugEnabled()){
			log.debug("string decoded: [" + decodedString + "]");
		}
		return decodedString;
	}
	
	/**
	 * decoding Base64 con charset (UTF-8, UTF-16, etc...)
	 * @param src
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String decodeString(String src, String charset) throws UnsupportedEncodingException {
		String decodedString = new String(Base64.decodeBase64(src.getBytes()));
		if (log.isDebugEnabled()){
			log.debug("string decoded: [" + decodedString + "]");
		}
		if (charset == null){
			return decodedString;
		} else {
			return new String(decodedString.getBytes(), charset);
		}
	}
	  
	 // endsWith
    //-----------------------------------------------------------------------

    /**
     * <p>Check if a String ends with a specified suffix.</p>
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered to be equal. The comparison is case sensitive.</p>
     *
     * <pre>
     * StringUtils.endsWith(null, null)      = true
     * StringUtils.endsWith(null, "abcdef")  = false
     * StringUtils.endsWith("def", null)     = false
     * StringUtils.endsWith("def", "abcdef") = true
     * StringUtils.endsWith("def", "ABCDEF") = false
     * </pre>
     *
     * @see java.lang.String#endsWith(String)
     * @param str  the String to check, may be null
     * @param suffix the suffix to find, may be null
     * @return <code>true</code> if the String ends with the suffix, case sensitive, or
     *  both <code>null</code>
     * @since 2.4
     */
    public static boolean endsWith(String str, String suffix) {
        return endsWith(str, suffix, false);
    }

    /**
     * <p>Case insensitive check if a String ends with a specified suffix.</p>
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered to be equal. The comparison is case insensitive.</p>
     *
     * <pre>
     * StringUtils.endsWithIgnoreCase(null, null)      = true
     * StringUtils.endsWithIgnoreCase(null, "abcdef")  = false
     * StringUtils.endsWithIgnoreCase("def", null)     = false
     * StringUtils.endsWithIgnoreCase("def", "abcdef") = true
     * StringUtils.endsWithIgnoreCase("def", "ABCDEF") = false
     * </pre>
     *
     * @see java.lang.String#endsWith(String)
     * @param str  the String to check, may be null
     * @param suffix the suffix to find, may be null
     * @return <code>true</code> if the String ends with the suffix, case insensitive, or
     *  both <code>null</code>
     * @since 2.4
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return endsWith(str, suffix, true);
    }

    /**
     * <p>Check if a String ends with a specified suffix (optionally case insensitive).</p>
     *
     * @see java.lang.String#endsWith(String)
     * @param str  the String to check, may be null
     * @param suffix the suffix to find, may be null
     * @param ignoreCase inidicates whether the compare should ignore case
     *  (case insensitive) or not.
     * @return <code>true</code> if the String starts with the prefix or
     *  both <code>null</code>
     */
    private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        int strOffset = str.length() - suffix.length();
        return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
    }
	
    /**
     * retorna los bytes de un inputStream
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] getBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		final int BUF_SIZE = 1 << 8; //1KiB buffer
		byte[] buffer = new byte[BUF_SIZE];
		int bytesRead = -1;
		while((bytesRead = in.read(buffer)) > -1) {
		      out.write(buffer, 0, bytesRead);
		}
		try {
			in.close();
			out.close();
		} catch(IOException ioex) {
			
		}
		byte[] imageBytes = out.toByteArray();
		return imageBytes;
	}

    /**
     * retorna los bytes como string de un inputStream
     * @param in
     * @return
     * @throws IOException
     */
    public static String getString(InputStream in) throws IOException {
		return new String(getBytes(in));
	}
    
    /**
     * 
     * @param src
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getJSONBoolean(JSONObject src, String key, boolean defaultValue) {
    	if (src == null) {
    		throw new IllegalArgumentException("src es null");
    	}
    	if (key == null) {
    		throw new IllegalArgumentException("key es null");
    	}
    	if (!src.isNull(key)) {
			try {
				return src.getBoolean(key);
			} catch(Exception e) {
				return defaultValue;
			}
    	} else {
    		return defaultValue;
    	}
	}
    
    /**
     * 
     * @param src
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getJSONString(JSONObject src, String key, String defaultValue) {
		if (src == null) {
    		throw new IllegalArgumentException("src es null");
    	}
    	if (key == null) {
    		throw new IllegalArgumentException("key es null");
    	}
    	if (!src.isNull(key)) {
			try {
				return src.getString(key);
			} catch(Exception e) {
				return defaultValue;
			}
    	} else {
    		return defaultValue;
    	}
	}
    
    /**
     * 
     * @param src
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getJSONInt(JSONObject src, String key, int defaultValue) {
    	if (src == null) {
    		throw new IllegalArgumentException("src es null");
    	}
    	if (key == null) {
    		throw new IllegalArgumentException("key es null");
    	}
    	if (!src.isNull(key)) {
			try {
				return src.getInt(key);
			} catch(Exception e) {
				return defaultValue;
			}
    	} else {
    		return defaultValue;
    	}
	}
	
    /**
     * 
     * @param src
     * @param key
     * @param defaultValue
     * @return
     */
	public static long getJSONLong(JSONObject src, String key, long defaultValue) {
		if (src == null) {
    		throw new IllegalArgumentException("src es null");
    	}
    	if (key == null) {
    		throw new IllegalArgumentException("key es null");
    	}
    	if (!src.isNull(key)) {
			try {
				return src.getLong(key);
			} catch(Exception e) {
				return defaultValue;
			}
    	} else {
    		return defaultValue;
    	}
	}
	
	/**
     * 
     * @param src
     * @param key
     * @param defaultValue
     * @return
     */
	public static float getJSONFloat(JSONObject src, String key, float defaultValue) {
		if (src == null) {
    		throw new IllegalArgumentException("src es null");
    	}
    	if (key == null) {
    		throw new IllegalArgumentException("key es null");
    	}
    	if (!src.isNull(key)) {
			try {
				return (float)src.getDouble(key);
			} catch(Exception e) {
				return defaultValue;
			}
    	} else {
    		return defaultValue;
    	}
	}
	
	/**
     * 
     * @param src
     * @param key
     * @param defaultValue
     * @return
     */
	public static double getJSONDouble(JSONObject src, String key, double defaultValue) {
		if (src == null) {
    		throw new IllegalArgumentException("src es null");
    	}
    	if (key == null) {
    		throw new IllegalArgumentException("key es null");
    	}
    	if (!src.isNull(key)) {
			try {
				return src.getDouble(key);
			} catch(Exception e) {
				return defaultValue;
			}
    	} else {
    		return defaultValue;
    	}
	}
	
	/**
	 * 
	 * @param src
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(JSONObject src, String key) {
		if (src == null) {
    		throw new IllegalArgumentException("src es null");
    	}
    	if (key == null) {
    		throw new IllegalArgumentException("key es null");
    	}
    	if (!src.isNull(key)) {
			try {
				return src.getJSONObject(key);
			} catch(Exception e) {
				return null;
			}
    	} else {
    		return null;
    	}
	}
	
	/**
	 * 
	 * @param src
	 * @param key
	 * @return
	 */
	public static JSONArray getJSONArray(JSONObject src, String key) {
		if (src == null) {
    		throw new IllegalArgumentException("src es null");
    	}
    	if (key == null) {
    		throw new IllegalArgumentException("key es null");
    	}
    	if (!src.isNull(key)) {
			try {
				return src.getJSONArray(key);
			} catch(Exception e) {
				return null;
			}
    	} else {
    		return null;
    	}
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static Integer toInteger( Object string ) {
		return toInteger( string, 0 );
	}
	
	/**
	 * 
	 * @param obj
	 * @param valueDefault
	 * @return
	 */
    public static Integer toInteger(Object obj,int valueDefault){
    	try{
    		if(obj == null){
        		return new Integer( valueDefault );
        	}else{
        		return new Integer( obj.toString() );
        	}
    	}catch(Exception nfex){
    		log.warn("Excepcion controlada en parseo toInteger, Se retornara valor default: " + valueDefault + " causa:[" + nfex.getMessage() + "]");
    		return new Integer( valueDefault );
    	}
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo int.
     * @param obj objeto que se desea transformar a int.
     * @return
     */
    public static int toInt(Object obj){
    	return toInt(obj,0);
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo int.
     * @param obj objeto que se desea transformar a int.
     * @param valueDefault valor por defecto en caso que el objeto no se pueda pasar al tipo int.
     * @return
     */
    public static int toInt(Object obj,int valueDefault){
    	try{
    		if(obj == null){
        		return valueDefault;
        	}else{
        		return Integer.parseInt(obj.toString());
        	}
    	}catch(NumberFormatException nfex){
    		log.warn("Excepcion controlada en parseo toInt, Se retornara valor default: " + valueDefault + " causa:[" + nfex.getMessage() + "]");
    		return valueDefault; 	
    	}
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo long.
     * @param obj objeto que se desea transformar a long.
     * @return
     */
    public static long toLong(Object obj){
    	return toLong(obj,0);
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo long.
     * @param obj objeto que se desea transformar a long.
     * @param valueDefault valor por defecto en caso que el objeto no se pueda pasar al tipo long.
     * @return
     */
    public static long toLong(Object obj,int valueDefault){
    	try{
    		if(obj == null){
        		return valueDefault;
        	}else{
        		return Long.parseLong(obj.toString());
        	}
    	}catch(NumberFormatException nfex){
    		log.warn("Excepcion controlada en parseo toLong, Se retornara valor default: " + valueDefault + " causa:[" + nfex.getMessage() + "]");
    		return valueDefault; 	
    	}
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo boolean.
     * @param obj
     * @param valueDefault
     * @return
     */
    public static boolean toBoolean(Object obj,boolean valueDefault){
    	try{
    		if(obj == null){
    			return valueDefault;
    		}else{    			
    			String v = obj.toString().toLowerCase().trim();
    			return ( ("1".equals(v) || "true".equals(v) || "on".equals(v)) ? true : false);
    		}	
    	}catch(Exception nfex){
    		log.warn("Excepcion controlada en parseo toBoolean, Se retornara valor default: " + valueDefault + " causa:[" + nfex.getMessage() + "]");
    		return valueDefault; 	
    	}
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo double.
     * @param obj objeto que se desea transformar a double.
     * @return
     */
    public static double toDouble(Object obj){
    	return toDouble(obj,(double)0.0);
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo double.
     * @param obj objeto que se desea transformar a double.
     * @param valueDefault valor por defecto en caso que el objeto no se pueda pasar al tipo double.
     * @return
     */
    public static double toDouble(Object obj,double valueDefault){
    	try{
    		if(obj == null){
        		return valueDefault;
        	}else{
        		return Double.parseDouble(obj.toString());
        	}
    	}catch(NumberFormatException nfex){
    		log.warn("Excepcion controlada en parseo toInt, Se retornara valor default: " + valueDefault + " causa:[" + nfex.getMessage() + "]");
    		return valueDefault; 	
    	}
    }
    
	/**
	 * fix para strings que son usados en documentos XML
	 * @param in
	 * @return
	 */
	public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.
        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }
	
	/**
	 * fix para strings que son usados en documentos XML
	 * @param in
	 * @return
	 */
	public static String stripNonValidXMLCharacters(Object in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.
        String in2 = in.toString();
        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in2.length(); i++) {
            current = in2.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }
    
	/**
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection c){
		return !isNotEmpty(c);
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Collection c){
		return c != null && !c.isEmpty();
	}
	
	/**
	 * limpia de excesos de espacios " " un string
	 * Ejemplo: " Como te llamai          ?, me llamo       Juan    "
	 * La salida seria: "Como te llamai ?, me llamo Juan"  
	 * @param src
	 * @return
	 */
	public static String trimComplete(String src) {
		
		if (StringUtils.isBlank(src)){
			return src;
		} else {
			String[] ss = src.split(" ");
			StringBuffer nueva = new StringBuffer();
			for (int i = 0; i < ss.length; i++) {
				if (!ss[i].equals("")){
					nueva.append(ss[i]);
					if (i < ss.length-1){
						nueva.append(" ");
					}
				}
			}
			return nueva.toString();
		}
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public static String cleanTextForVelocity(Object in) {
		String in2 = stripNonValidXMLCharacters(in);
		if (in2 == null || ("".equals(in2))) return ""; // vacancy test.
		in2 = in2.replaceAll("&lt;","<").replaceAll("&gt;",">");
		return "<![CDATA[" + in2 + "]]>";
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public static String cleanTextForVelocity(String in) {
		String in2 = stripNonValidXMLCharacters(in);
		if (in2 == null || ("".equals(in2))) return ""; // vacancy test.
		in2 = in2.replaceAll("&lt;","<").replaceAll("&gt;",">");
		return "<![CDATA[" + in2 + "]]>";
	}
	
	/**
	 * Realiza una busqueda de string - utiliza el estandar sql que permite utilizar el caracter %
	 * @param {string} - str - string que se desea buscar
	 * @param {boolean} - casesve - true: case-sensitive
	 * @example
	 *  	var criterio = '%tinuum%';
	 *  	
	 *  	System.out.println( criterio.inStr('Hola continuum') ); 	//resultado true
	 *  
	 *  	var criterio = '%tinuu';
	 *  	
	 *  	console.info( criterio.inStr('Hola continuum') ); 	//resultado false
	 *  @return true: se encontro el valor, false: no se encontro el valor
	 */
	public static boolean inStr(String str1, String str2, boolean casesve){
		
		if (StringUtils.isNotBlank(str1) && StringUtils.isNotBlank(str2)) {
		
			str2 = (!casesve) ? str2.toLowerCase() : str2;
			
			if (str1.startsWith("%") && str1.endsWith("%")) { //start and end
				String s = str1.substring(1,str1.length()-1);
				boolean ok = str2.indexOf((!casesve) ? s.toLowerCase() : s) != -1;
				return ok;
			}else if (str1.startsWith("%")) {	//start
				String s = str1.substring(1);
				boolean ok = str2.endsWith((!casesve) ? s.toLowerCase() : s);
				return ok;
			} else if (str1.endsWith("%")) { //end
				String s = str1.substring(0,str1.length()-1);
				boolean ok = str2.startsWith((!casesve) ? s.toLowerCase() : s);
				return ok;
			} else {
				String s = str1;
				boolean ok = str2.indexOf((!casesve) ? s.toLowerCase() : s) != -1;
				return ok;
			}
			
		} else {
			return false;
		}
	}
	
	/**
	 * pagina una lista
	 * @param lista
	 * @param desde
	 * @param cantidad
	 * @param count
	 * @return
	 */
	public static List paginaLista(List lista, int desde, int cantidad, int count) {
		
		if (isEmpty(lista)) return lista;
		
		int d = 0;
		int h = 0;
		
		int hasta = cantidad;
		
		if (desde < 0) {
			desde = 0;
		}
		
		if(cantidad > 0) {
			hasta = desde + cantidad;
		}
		
        if (hasta < count) {
        	
        	if (desde <= 0) {
            	d = desde;
            	h = hasta;
        	} else {
        		d = desde - 1;
        		h = hasta;
        	}

        } else {
        	
        	if (desde <= 0) {
            	d = desde;
            	h = count;
        	} else {
        		d = desde - 1;
        		h = count;
        	}
        }
        
        if ((h-d) > cantidad) {
        	h = h - ((h-d) - cantidad);
        }
        
        return lista.subList(d, h);
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String scapeTagsHTML(String s) {
		if (StringUtils.isNotBlank(s)){
			return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		} else {
			return s;
		}
	}
	
	/**
	 * retorna el digito verificador
	 */
	public static char getDigitoVerificador(int rut) {
		//Codigo sacado desde un foro de internet
		int M=0,S=1,T=rut;
		for(;T!=0;T/=10)S=(S+T%10*(9-M++%6))%11;
		return (char)(S!=0?S+47:75);
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static String getVal(String v) {
		return getVal(v, "");
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static String getVal(Object v) {
		return getVal(v, "");
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static String getVal(String v, String default_) {
		return StringUtils.isBlank(v) ? default_ : v.trim();
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static String getVal(Object v, String default_) {
		if (v == null) {
			return default_;
		} else {
			return StringUtils.isBlank(v.toString()) ? default_ : v.toString().trim();
		}
	}
	
	/**
	 * retorna un string en el tipico formato numerico usado para procedimientos cobol "0000" ceros a la izquierda para completar un largo
	 * Ej: toIntPad(12, 4) => 0012
	 * @param v
	 * @return
	 */
	public static String toIntPad(String v, int length) {
		return StringUtils.isBlank(v) ? StringUtils.leftPad("",length,"0") : StringUtils.leftPad(v.trim(),length,"0");
	}
	
	/**
	 * retorna un string en el tipico formato numerico usado para procedimientos cobol "0000" ceros a la izquierda para completar un largo
	 * Ej: toIntPad(12, 4) => 0012
	 * @param v
	 * @return
	 */
	public static String toIntPad(Object v, int length) {
		if (v == null) {
			return StringUtils.leftPad("",length,"0");
		} else {
			return toIntPad(v.toString(), length);
		}
	}
	
	/**
	 * genera un hashMap con las claves y valores pasadas como parametro
	 * EJ: map("descripcion","dato adicional 1","codigo","dato_adi_2")
	 * @param args
	 * @return
	 */
	public static Map map(Object ... args){
		Map m = new HashMap();
		
		if (args.length%2 == 0) {
		
			for (int i=0;i < args.length; i+=2)   {
				m.put(args[i], args[i+1]);
			}
			
		} else {
			throw new IllegalArgumentException("Numero de parametros [key, value] incorrectos");
		}
		
		return m;
	}
	
	/**
	 * genera un arrayList con los valores pasados como parametro
	 * EJ: list("descripcion","dato adicional 1","codigo","dato_adi_2")
	 * @param args
	 * @return
	 */
	public static List list(Object ... args){
		List m = new ArrayList();
		
		for (int i=0;i < args.length; i++)   {
			m.add(args[i]);
		}
		
		return m;
	}
	
	/**
	 * Tranforma un objeto simple en un Map, excluye analisis de dependencias
	 * @param obj
	 * @return
	 */
	public static Map simpleBeanToMap(Object obj) {
		
		if (obj == null) return null;
		
		Method[] metodos = obj.getClass().getMethods();
		
		if (metodos != null) {
			Map map = new HashMap();
			for (int i = 0; i < metodos.length; i++) {
				Method m = metodos[i];
				String name = m.getName();
				boolean isGet = name.startsWith("get");
				boolean isIs = name.startsWith("is");
				if (isGet || isIs) {
					Object valor = null;
					try {
						Object[] o = null;
						valor = m.invoke(obj, o);
					} catch (Exception e) {
					} 			
					name = Character.toLowerCase(m.getName().charAt(isGet ? 3 : 2)) + name.substring(isGet ? 4 : 3);
					map.put(name, valor);
				}
			}
			return map;
		} else {
			return null;
		}
	}
	
	/**
	 * Transforma un objeto simple en un JSONObject2, excluye analisis de dependencias
	 * @param obj
	 * @return
	 */
	public static JSONObject simpleBeanToJSONObject(Object obj) {
		return new JSONObject2(simpleBeanToMap(obj));
	}
	
	/**
	 * 
	 * @param args
	 * @throws JSONException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws JSONException, IOException {
		
		//System.out.println(actualTimeToString(""));
		
//		String s = "http://des-cm03/CMBGenericWebService/CMBGetPIDUrl?pid=85%203%20ICM8%20icmnlsdb6%20IT_eas59%2026%20A1001001A09B18B51012J7990118%20A09B18B51012J799011%2014%201057&server=icmnlsdb&dsType=ICM";
//		
//		System.out.println(s.length());
//				
//		String epa = "{\"compatibleCascoSeguridad\":true,\"compatibleEspacioConfinado\":true,\"compatibleGafasSeguridad\":false,\"compatibleMascaraSoldar\":false,\"compatibleRespirador\":false,\"fabricante\":{\"id\":15,\"nombre\":\"Acme\"},\"fechaIngreso\":null,\"id\":14,\"instruccionesCuidadoSeparado\":true,\"instruccionesUsoSeparado\":true,\"modelo\":\"Modelo 4\",\"nombreComercial\":\"EpaNomCom 4\",\"normasTesteo\":\"\",\"observacion\":\"\",\"otraCaracteristica\":\"otras cosas\",\"rutaImagen\":\"\",\"rutaImagenCaja\":\"\",\"rutaImagenTecnica\":\"\",\"tipoClasificacion\":{\"codigoSUBCL\":\"tg\",\"grupo\":{\"clasificacion\":\"grupo g\",\"id\":2,\"nombre\":\"g\"},\"id\":4,\"nombre\":\"tipo g\"}}";			
//		String mediciones = "[{\"a02\":0,\"a03\":0,\"a04\":0,\"a05\":0,\"a06\":0,\"a07\":0,\"a08\":0,\"a09\":0,\"a10\":0,\"a11\":0,\"d02\":0,\"d03\":0,\"d04\":0,\"d05\":0,\"d06\":0,\"d07\":0,\"d08\":0,\"d09\":0,\"d10\":0,\"d11\":0,\"datosParaCalculo\":false,\"fechaIngreso\":null,\"hmlH\":0,\"hmlL\":0,\"hmlM\":0,\"id\":-1,\"idEpa\":0,\"idLaboratorio\":0,\"linkInforme\":\"\",\"nrr\":0,\"observaciones\":\"\",\"snr\":0},{\"a02\":0,\"a03\":0,\"a04\":0,\"a05\":0,\"a06\":0,\"a07\":0,\"a08\":0,\"a09\":0,\"a10\":0,\"a11\":0,\"d02\":0,\"d03\":0,\"d04\":0,\"d05\":0,\"d06\":0,\"d07\":0,\"d08\":0,\"d09\":0,\"d10\":0,\"d11\":0,\"datosParaCalculo\":false,\"fechaIngreso\":null,\"hmlH\":0,\"hmlL\":0,\"hmlM\":0,\"id\":-1,\"idEpa\":0,\"idLaboratorio\":0,\"linkInforme\":\"\",\"nrr\":0,\"observaciones\":\"\",\"snr\":0}]";
//
//		Object bean = jsonToBean(epa, Epa.class);
//		System.out.println("bean: " + bean);
//		
//		JSONObject json_object = beanToJson(bean, false);
//		System.out.println("json_object: " + json_object);
//		
//		Collection cMediciones = jsonArrayToCollection(mediciones, Medicion.class);
//		System.out.println("collection: " + cMediciones);
//		
//		JSONArray json_array = collectionToJsonArray(cMediciones, true);
//		System.out.println("json_array: " + json_array);
//		
//		FuenteCritica f = new FuenteCritica();
//		
//		System.out.println(Util.beanToJson(f, true, Util.REGLA_DECIMAL_TO_STRING));
//		
//		List lst = new ArrayList();
//		lst.add(new FuenteCritica());
//		lst.add(new FuenteCritica());
//		lst.add(new FuenteCritica());
//		lst.add(new FuenteCritica());
//
//		System.out.println(Util.listToJsonArray(lst, false, Util.REGLA_DECIMAL_TO_STRING));
//		System.out.println(Util.inStr("%gen%", "DIRECCION GENERAL DE AERO", false));
//		System.out.println(getDigitoVerificador(76778900));
		System.out.println(validaIp("1.0.0."));
	}
	
	/**
	 * Valida si un string en una ip bien formada
	 * @param ip
	 * @return
	 */
	public static boolean validaIp(final String ip){
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();	    	    
	}
}
