package cl.wamtech.remarcador.util;

import java.text.NumberFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author vutreras
 *
 */
public class NumberUtil {
	
	protected static final Log log = LogFactory.getLog(NumberUtil.class);

	private static NumberFormat nf = NumberFormat.getInstance(DateUtil.locale);
	
	private static NumberUtil instance;
	
	/**
	 * implementa singleton
	 * @return
	 */
	public static NumberUtil getInstance() {
		if (instance == null) {
			instance = new NumberUtil();
		}
		return instance;
	}
	
	/**
	 * es privado para implementar singleton
	 */
	private NumberUtil() {
	}
	
	/**
	 * 
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(Object obj) {
		return nf.format(toLong(obj,0));
	}
	
	/**
	 * 
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(int obj) {
		return nf.format(obj);
	}
	
	/**
	 * 
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(Integer obj) {
		return nf.format(obj);
	}
	
	/**
	 * 
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(long obj) {
		return nf.format(obj);
	}
	
	/**
	 * 
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(Long obj) {
		return nf.format(obj);
	}
	
	/**
	 * 
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(double obj) {
		return nf.format(obj);
	}
	
	/**
	 * 
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(Double obj) {
		return nf.format(obj);
	}
	
	/**
	 * 
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(String obj) {
		return nf.format(obj);
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	public Integer toInteger( Object string ) {
		return toInteger( string, 0 );
	}
	
	/**
	 * 
	 * @param obj
	 * @param valueDefault
	 * @return
	 */
    public Integer toInteger(Object obj,int valueDefault){
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
    public int toInt(Object obj){
    	return toInt(obj,0);
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo int.
     * @param obj objeto que se desea transformar a int.
     * @param valueDefault valor por defecto en caso que el objeto no se pueda pasar al tipo int.
     * @return
     */
    public int toInt(Object obj,int valueDefault){
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
    public long toLong(Object obj){
    	return toLong(obj,0);
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo long.
     * @param obj objeto que se desea transformar a long.
     * @param valueDefault valor por defecto en caso que el objeto no se pueda pasar al tipo long.
     * @return
     */
    public long toLong(Object obj,int valueDefault){
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
    public boolean toBoolean(Object obj,boolean valueDefault){
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
    public double toDouble(Object obj){
    	return toDouble(obj,(double)0.0);
    }
    
    /**
     * Transforma el objeto pasado como parametro al tipo double.
     * @param obj objeto que se desea transformar a double.
     * @param valueDefault valor por defecto en caso que el objeto no se pueda pasar al tipo double.
     * @return
     */
    public double toDouble(Object obj,double valueDefault){
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
}
