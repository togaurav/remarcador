/**
 * 
 */
package cl.wamtech.remarcador.test;

import java.text.ParseException;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * @author jorge
 *
 */
public class MainTest {

	/**
	 * 
	 */
	public MainTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		
		long multiplicador = 1000; 
		long fechaMl = 1285891200;
		Long total = (fechaMl * multiplicador) + (14400000);
		Long totalIncial = (total - 14400000) / 1000;
		System.out.println("total inicial: " + totalIncial);
		System.out.println("total calculado: " + total);
		System.out.println("fecha: " + DateFormatUtils.format(total, "yyyy-MM-dd HH:mm:ss"));
		
		
		
//		GregorianCalendar calendar = new GregorianCalendar(2010, 7, 8, 00, 00, 00);
//		Long totalCalendar = (calendar.getTimeInMillis());
//		System.out.println("totalCalendar: " + totalCalendar);
//		System.out.println("calendar ts: " + ((totalCalendar / 1000)));

		

//		System.out.println("minuto menos :" + (totalCalendar - 60000));
//		System.out.println("minuto mas :" + (totalCalendar + 60000));
//		
//		System.out.println("minuto menos calendar: " + ((totalCalendar - 60000) - (144000000)) / 1000);
//		System.out.println("minuto mas calendar: " + ((totalCalendar + 60000) - (144000000)) / 1000);

//		System.out.println((1281621300 * multiplicador) + (144000000));
//		Long fnal = (1281621300 * multiplicador) + (144000000);

		
//		System.out.println(DateFormatUtils.format(totalCalendar, "yyyy-MM-dd HH:mm:ss"));
	}

}