/**
 * 
 */
package cl.wamtech.remarcador.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author vutreras
 *
 */
public class DateUtil {

	public static final Locale locale = new Locale("es", "CL");
	
	private static final String[] parsers = {"MM/dd/yyyy"};
	

	public static final String DEFAULT_SEPARATOR_VIEW_FORMAT_DATE = "/";
	
	public static final String DEFAULT_SEPARATOR_VIEW_FORMAT_TIME = ":";
	
	public static final String DEFAULT_VIEW_FORMAT_DATE = "dd" + DEFAULT_SEPARATOR_VIEW_FORMAT_DATE + "MM" + DEFAULT_SEPARATOR_VIEW_FORMAT_DATE + "yyyy";
	
	public static final String DEFAULT_VIEW_FORMAT_TIME = "hh" + DEFAULT_SEPARATOR_VIEW_FORMAT_TIME + "mm" + DEFAULT_SEPARATOR_VIEW_FORMAT_TIME + "ss";
	
	
	
	public static final String DATE_PATRON_DD_DE_MMMM_DE_YYYY = "dd # MMMM # yyyy";
	
	public static final String DATE_PATRON_YYYYMMDD = "yyyyMMdd";
	public static final String DATE_PATRON_YYYY_MM_DD = "yyyy/MM/dd";
	
	public static final String DATE_PATRON_DDMMYYYY = "ddMMyyyy";
	public static final String DATE_PATRON_DD_MM_YYYY = "dd/MM/yyyy";	
	
	public static final String DATE_PATRON_MMYYYY = "MMyyyy";
	public static final String DATE_PATRON_MM_YYYY = "MM/yyyy";
	
	public static final String DATE_PATRON_YYYYMM = "yyyyMM";
	public static final String DATE_PATRON_YYYY_MM = "yyyy/MM";

	public static final String DATE_PATRON_HHMMSS = "HHmmss";
	public static final String DATE_PATRON_HH_MM_SS = "HH:mm:ss";
	
	public static final String DATE_PATRON_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	
	public static final SimpleDateFormat _FORMAT_YYYYMMDD = new SimpleDateFormat(DATE_PATRON_YYYYMMDD);
	public static final SimpleDateFormat _FORMAT_YYYY_MM_DD = new SimpleDateFormat(DATE_PATRON_YYYY_MM_DD);

	public static final SimpleDateFormat _FORMAT_DDMMYYYY = new SimpleDateFormat(DATE_PATRON_DDMMYYYY);
	public static final SimpleDateFormat _FORMAT_DD_MM_YYYY = new SimpleDateFormat(DATE_PATRON_DD_MM_YYYY);
	
	public static final SimpleDateFormat _FORMAT_YYYYMM = new SimpleDateFormat(DATE_PATRON_YYYYMM);
	public static final SimpleDateFormat _FORMAT_YYYY_MM = new SimpleDateFormat(DATE_PATRON_YYYY_MM);
	
	public static final SimpleDateFormat _FORMAT_MMYYYY = new SimpleDateFormat(DATE_PATRON_MMYYYY);
	public static final SimpleDateFormat _FORMAT_MM_YYYY = new SimpleDateFormat(DATE_PATRON_MM_YYYY);
	
	public static final SimpleDateFormat _FORMAT_HHMMSS = new SimpleDateFormat(DATE_PATRON_HHMMSS);
	public static final SimpleDateFormat _FORMAT_HH_MM_SS = new SimpleDateFormat(DATE_PATRON_HH_MM_SS);

	private static DateUtil instance;
	
	/**
	 * implementa singleton
	 * @return
	 */
	public static DateUtil getInstance() {
		if (instance == null) {
			instance = new DateUtil();
		}
		return instance;
	}
	
	/**
	 * es privado para implementar singleton
	 */
	private DateUtil() {
	}
	
	/**
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseStrDate(String strDate) throws ParseException {
		return DateUtils.parseDate(strDate,parsers);
	}
	
	/**
	 * @param strDate
	 * @param parser
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStrDate(String strDate, String parser) throws ParseException {
		if(StringUtils.isBlank(parser))
			return parseStrDate(strDate);
		else{
			String[] parserTemp = {parser};
			return DateUtils.parseDate(strDate,parserTemp);
		}
	}
	
	/**
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date parseIntTime(int strDate) throws ParseException {
		String[] parserTemp = {DATE_PATRON_HHMMSS};
		String hora = strDate + "";
		if (hora != null && hora.length() <= 6) {
			hora = StringUtils.leftPad(hora, 6, '0');
			hora = hora.substring(0,2) + hora.substring(2,4) + hora.substring(4,6);
			return DateUtils.parseDate(hora,parserTemp);
		}
		
		return null;
	}
	
	/**
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date parseIntDate(int strDate) throws ParseException {
		String[] parserTemp = {DATE_PATRON_YYYYMMDD};
		String fecha = strDate + "";
		if (fecha != null && fecha.length() == 8 && strDate >= 19700101) {
			fecha = StringUtils.leftPad(fecha, 8, '0');
			fecha = fecha.substring(0, 4) + fecha.substring(4, 6) + fecha.substring(6);
			return DateUtils.parseDate(fecha,parserTemp);
		}
		return null;
	}
	
	/**
	 * transforma un objeto en el formato dd/MM/yyyy a un entero en formato yyyyMMdd
	 * @param fecha
	 * @return
	 */
	public static int objectToIntDate(Object fecha) {
		if (fecha != null) {
			String f = String.valueOf(fecha);
			String[] s = f.split(DEFAULT_SEPARATOR_VIEW_FORMAT_DATE);
			return Integer.parseInt(s[2] + s[1] + s[0]);
		} else {
			return 0;
		}
	}
	
	/**
	 * transforma un objeto en el formato dd/MM/yyyy a un entero en formato yyyyMMdd
	 * @param fecha
	 * @return
	 */
	public static int stringToIntDate(String fecha) {
		return objectToIntDate(fecha);
	}

	/**
	 * transforma un objeto en el formato yyyyMMdd a un String en formato dd/MM/yyyy
	 * @param fecha
	 * @return
	 */
	public static String intToStringDate(String fecha) {
		if (fecha != null) {
			String f = fecha.trim();
			if (f.length() == 8) {
				f = StringUtils.leftPad(f, 8, '0');
				String s = DEFAULT_SEPARATOR_VIEW_FORMAT_DATE;
				return (f.substring(6) + s + f.substring(4, 6) + s + f.substring(0, 4));
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * transforma un objeto en el formato yyyyMMdd a un String en formato dd/MM/yyyy 
	 * @param fecha
	 * @return
	 */
	public static String intToStringDate(Object fecha) {
		if (fecha != null) {
			return intToStringDate(fecha.toString());
		} else {
			return null;
		}
	}
	
	/**
	 * transforma un objeto en el formato yyyyMMdd a un String en formato dd/MM/yyyy
	 * @param fecha
	 * @return
	 */
	public static String intToStringDate(int fecha) {
		return intToStringDate(String.valueOf(fecha));
	}
	
	/**
	 * transforma un objeto en el formato hhmmss a un String en formato hh:mm:ss
	 * @param hora
	 * @return
	 */
	public static String intToStringTime(String hora) {
		if (hora != null) {
			String f = hora.trim();
			if (f.length() == 6) {
				String s = DEFAULT_SEPARATOR_VIEW_FORMAT_TIME;
				f = StringUtils.leftPad(f, 6, '0');
				return (f.substring(0,2) + s + f.substring(2,4) + s + f.substring(4,6));
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * transforma un objeto en el formato hhmmss a un String en formato hh:mm:ss
	 * @param hora
	 * @return
	 */
	public static String intToStringTime(Object hora) {
		if (hora != null) {
			return intToStringTime(hora.toString());
		} else {
			return null;
		}
	}
	
	/**
	 * transforma un objeto en el formato hhmmss a un String en formato hh:mm:ss
	 * @param fecha
	 * @return
	 */
	public static String intToStringTime(int hora) {
	    	String h = StringUtils.leftPad(String.valueOf(hora), 6, '0');
		return intToStringTime(h);
	}
	
	/**
	 * retorna la fecha actual en el formato yyyyMMdd
	 * @return
	 */
	public static Integer actualDateToInteger() {
		Calendar c = Calendar.getInstance(locale);
		int anio = c.get(Calendar.YEAR);
		String mes = StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dia = StringUtils.leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0');
		return new Integer(anio + mes + dia);
	}
	
	/**
	 * retorna la fecha actual en el formato yyyyMMdd
	 * @return
	 */
	public static int actualDateToInt() {
		return actualDateToInteger().intValue();
	}
	
	/**
	 * retorna la fecha actual en el formato dd/MM/yyyy
	 * @return
	 */
	public static String actualDateToString() {
		return actualDateToString(null);
	}
	
	/**
	 * retorna la fecha actual en el formato ddMMyyyy 
	 * @param separador
	 * @return
	 */
	public static String actualDateToString(String separador) {
		Calendar c = Calendar.getInstance(locale);
		int anio = c.get(Calendar.YEAR);
		String mes = StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dia = StringUtils.leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0');
		separador = (separador == null) ? DateUtil.DEFAULT_SEPARATOR_VIEW_FORMAT_DATE : separador;
		return dia + separador + mes + separador + anio;
	}
	
	/**
	 * retorna la hora actual en el formato hhmmss
	 * @return
	 */
	public static Integer actualTimeToInteger() {
		Calendar c = Calendar.getInstance(locale);
		int hora = c.get(Calendar.HOUR_OF_DAY);
		String min = StringUtils.leftPad(String.valueOf(c.get(Calendar.MINUTE)), 2, '0');
		String seg = StringUtils.leftPad(String.valueOf(c.get(Calendar.SECOND)), 2, '0');
		return new Integer(hora + min + seg);
	}
	
	/**
	 * retorna la hora actual en el formato hhmmss
	 * @return
	 */
	public static int actualTimeToInt() {
		return actualTimeToInteger().intValue();
	}
	
	/**
	 * retorna la hora actual en el formato hhmmss
	 * @return
	 */
	public static String actualTimeToString() {
		return actualTimeToString(null);
	}
	
	/**
	 * retorna la hora actual en el formato hhmmss con el separador pasado como parametro
	 * @return
	 */
	public static String actualTimeToString(String separador) {
		Calendar c = Calendar.getInstance(locale);
		String hora = StringUtils.leftPad(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), 2, '0');
		String min = StringUtils.leftPad(String.valueOf(c.get(Calendar.MINUTE)), 2, '0');
		String seg = StringUtils.leftPad(String.valueOf(c.get(Calendar.SECOND)), 2, '0');
		separador = (separador == null) ? DateUtil.DEFAULT_SEPARATOR_VIEW_FORMAT_TIME : separador;
		return hora + separador + min + separador + seg;
	}
	
	/**
	 * suma los dias a la fecha actual y la retorna en el formato dd/MM/yyyy
	 * @param dias
	 * @return
	 */
	public static Integer futuraDateToInteger(int dias) {
		Calendar c = Calendar.getInstance(locale);
		c.add(Calendar.DAY_OF_MONTH, dias);
		int anio = c.get(Calendar.YEAR);
		String mes = StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dia = StringUtils.leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0');
		return new Integer(anio + mes + dia);
	}
	
	/**
	 * suma los dias a la fecha actual y la retorna en el formato dd/MM/yyyy
	 * @param dias
	 * @return
	 */
	public static String futuraDateToString(int dias) {
		return intToStringDate(futuraDateToInteger(dias));
	}
	
	/**
	 * suma los dias a la fecha pasada como parametro y la retorna en el formato yyyyMMdd
	 * @param fecha
	 * @param dias
	 * @return
	 */
	public static Integer futuraDateToInteger(Date fecha, int dias) {
		Calendar c = Calendar.getInstance(locale);
		c.setTime(fecha);
		c.add(Calendar.DAY_OF_MONTH, dias);
		int anio = c.get(Calendar.YEAR);
		String mes = StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dia = StringUtils.leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0');
		return new Integer(anio + mes + dia);
	}
	
	/**
	 * suma los dias a la fecha pasada como parametro y la retorna en el formato dd/MM/yyyy
	 * @param fecha
	 * @param dias
	 * @return
	 */
	public static String futuraDateToString(Date fecha, int dias) {
		return intToStringDate(futuraDateToInteger(fecha, dias));
	}
	
	/**
	 * 
	 * @param sFecha
	 * @param fecha
	 * @return
	 */
	public static int resuelveFecha(String sFecha, int fecha) {
		return (!StringUtils.isEmpty(sFecha)) ? stringToIntDate(sFecha) : fecha;
	}
	
	/**
	 * 
	 * @param fecha
	 * @param sFecha
	 * @return
	 */
	public static String resuelveFecha(int fecha, String sFecha) {
		return (fecha <= 0) ? sFecha :  intToStringDate(fecha);
	}
	
    
    /**
     * 
     * @param fecha
     * @param patron
     * @return
     */
	public static String formatDate(Date fecha, String patron){
		String retorno = null;
		if(patron != null && patron != null){
			if (DATE_PATRON_DD_DE_MMMM_DE_YYYY.equals(patron)) {
				retorno = DateFormatUtils.format(fecha, "dd # MMMM # yyyy").replaceAll("#", "de");
			} else {
				DateFormat formatter = new SimpleDateFormat(patron);
		        formatter.setTimeZone(TimeZone.getDefault());
				retorno = formatter.format(fecha);
			}
		}
		return retorno;
	}
	
	/**
	 * 
	 * @param fecha
	 * @param patron
	 * @return
	 */
	public static String formatTime(Date fecha, String patron){
		return formatDate(fecha, patron);
	}
	
	/**
	 * @param fecha
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date fecha, String pattern){
		
		return DateFormatUtils.format(fecha, pattern);
	}
	
	/**
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map diferencia(Date fecha1, Date fecha2) {
		long diferencia = fecha1.getTime() - fecha2.getTime();
		long dias = diferencia / (1000 * 60 * 60 * 24);  
		long segundos = diferencia / 1000;
		Map m = new HashMap();
		m.put("dias", String.valueOf(dias));
		m.put("segundos", String.valueOf(segundos));
		return m;
	}
	
	/**
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public static Map diferencia(int fecha1, int fecha2) throws ParseException {
		return diferencia(_FORMAT_YYYYMMDD.parse(String.valueOf(fecha1)), _FORMAT_YYYYMMDD.parse(String.valueOf(fecha2)));
	}
	
	/**
	 * 
	 * @return
	 */
	public static String actualDateTime() {
		Calendar c = Calendar.getInstance(locale);
		int anio = c.get(Calendar.YEAR);
		String mes = StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dia = StringUtils.leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2, '0');
		int hora = c.get(Calendar.HOUR_OF_DAY);
		String min = StringUtils.leftPad(String.valueOf(c.get(Calendar.MINUTE)), 2, '0');
		String seg = StringUtils.leftPad(String.valueOf(c.get(Calendar.SECOND)), 2, '0');
		return "" + new Integer(anio + mes + dia) + new Integer(hora + min + seg);
	}
	
	/**
	 * 
	 * @param fechaNacimiento
	 */
	public static int calculaEdad(int fechaNacimiento) {
		try {
			Calendar fechaNac = Calendar.getInstance(locale);
			fechaNac.setTime(DateUtil._FORMAT_YYYYMMDD.parse(String.valueOf(fechaNacimiento)));
			
		    Calendar today = Calendar.getInstance(locale);
		   
		    int diff_year = today.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
		    int diff_month = today.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
		    int diff_day = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
	
		    //Si está en ese año pero todavía no los ha cumplido
		    if(diff_month<0 || (diff_month==0 && diff_day<0)){
		        diff_year = diff_year - 1; //no aparecían los dos guiones del postincremento :|
		    }
		    return diff_year;
		} catch (ParseException e) {
			return 0;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(actualDateToInt());
		System.out.println(actualDateToInteger());
		System.out.println(actualDateToString());
		System.out.println(actualDateToString("-"));
		System.out.println();
		System.out.println(actualTimeToInt());
		System.out.println(actualTimeToInteger());
		System.out.println(actualTimeToString());
		System.out.println(actualTimeToString("-"));
		try {
			System.out.println(parseStrDate("012345", DateUtil.DATE_PATRON_HHMMSS));
			System.out.println(parseStrDate("11/02/1981", DateUtil.DATE_PATRON_DD_MM_YYYY));
			System.out.println(parseStrDate("19810211", DateUtil.DATE_PATRON_YYYYMMDD));
			System.out.println(parseStrDate("19810211012345", DateUtil.DATE_PATRON_YYYYMMDDHHMMSS));
			System.out.println(parseIntTime(345));
			System.out.println(parseIntTime(12345));
			System.out.println(parseIntTime(123456));
			System.out.println(parseIntTime(120000));
			System.out.println(parseIntTime(133456));
			System.out.println(parseIntTime(3456));
			System.out.println(parseIntTime(235959));
			System.out.println(parseIntTime(235960));
			System.out.println(parseIntTime(0));
			System.out.println(intToStringTime(120001));
			System.out.println("--FECHAS--");
			System.out.println(parseIntDate(199900204));
			System.out.println(parseIntDate(19690204));
			System.out.println(parseIntDate(19690204));
			System.out.println(parseIntDate(19700101));
			System.out.println(parseIntDate(19600101));
			System.out.println(parseIntDate(120000));
			System.out.println(parseIntDate(0));
			
			System.out.println(dateToString(parseIntDate(19700101), DATE_PATRON_DD_MM_YYYY));
			System.out.println(dateToString(parseIntDate(20100831), DATE_PATRON_YYYYMMDD));

			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static Map getHorasOMinutos(String tipo) {
		List resultado = new ArrayList();
		
		if(tipo.equals("HORA")) {
			for (int i = 0; i < 24; i++) {
				resultado.add(StringUtils.leftPad(String.valueOf(i), 2, '0'));
			}
			
		} else if(tipo.equals("MINUTO")) {
			for (int i = 0; i < 60; i++) {
				resultado.add(StringUtils.leftPad(String.valueOf(i), 2, '0'));
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultado", resultado);		
		
		return map;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject separateDate(String date) throws JSONException {
		JSONObject result = new JSONObject();
		result.put("day", Integer.parseInt(date.split("/")[0]));
		result.put("month", Integer.parseInt(date.split("/")[1]));
		result.put("year", Integer.parseInt(date.split("/")[2]));
		return result;
	}
	
	/**
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	public static GregorianCalendar getCustomDateCalendar(JSONObject jsonObject) throws JSONException {
		return  new GregorianCalendar(
				jsonObject.getInt("year"), 
				jsonObject.getInt("month")-1, 
				jsonObject.getInt("day"), 
				jsonObject.getInt("hour"),
				jsonObject.getInt("minutes")
		);
	}
}