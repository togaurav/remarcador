package cl.wamtech.remarcador.util;

import java.util.List;

public class MathUtil {
	
	private static MathUtil instance;
	
	/**
	 * implementa singleton
	 * @return
	 */
	public static MathUtil getInstance() {
		if (instance == null) {
			instance = new MathUtil();
		}
		return instance;
	}
	
	/**
	 * es privado para implementar singleton
	 */
	private MathUtil(){
	}
	
	/**
	 * Redondea un double nD a la cantidad de decimales nDec
	 * @param nD
	 * @param nDec
	 * @return
	 */
	public static double redondear(double nD, int nDec) 	{  
	  return Math.round(nD*Math.pow(10,nDec))/Math.pow(10,nDec);  
	}  
	
	/**
	 * Calcula el logaritmo en base 10 del valor double de lnD
	 * @param lnD
	 * @return
	 */
	public static double logBase10(double lnD){
		return (Math.log(lnD)/Math.log(10));
	} 

	/**
	 * Calcula el valor de NPS
	 * @param frecuencias
	 * @return
	 */
	public static double calculoNPS(List frecuencias){
		
		int it = frecuencias.size();
		int ini = 0;
		double logParam = 0;
		while (ini < it) {
			if(0 != ((Double)frecuencias.get(ini)).doubleValue())logParam = logParam + Math.pow(10, (0.1*((Double)frecuencias.get(ini)).doubleValue()));
			ini++;
		}
		
		double NPS = 10*logBase10(logParam);
		return NPS; 
	}
	
	/**
	 * Calcular que porcentaje es A en B.
	 * @param a
	 * @param b
	 * @return
	 */
	public static double porcentaje_A_en_B(double a, double b) {
		double c = (double)((double)a / (double)b);
		return c * 100;
	}
	
	/**
	 * Calcular que porcentaje es A en B.
	 * @param a
	 * @param b
	 * @return
	 */
	public static double porcentaje_A_en_B(Number a, Number b) {
		double c = (double)(a.doubleValue() / b.doubleValue());
		return c * 100;
	}
}
