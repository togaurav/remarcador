/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.DateUtil;
import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table bitacoras
 */
public class Bitacora implements IPersistente {
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*@sqlmap_column_primary_key id*/
	private int id = -1;
	
	/*@sqlmap_column id_usuario*/
	private int idUsuario = -1;
	
	/*@sqlmap_column fecha_lectura_inicial*/
	private int fechaLecturaInicial = -1;
	
	/*@sqlmap_column fecha_lectura_fin*/
	private int fechaLecturaFin = -1;
	
	/*@sqlmap_column hora_lectura_inicial*/
	private String horaLecturaInicial;
	
	/*@sqlmap_column hora_lectura_fin*/
	private String horaLecturaFin;
	
	/*@sqlmap_column fecha_actual*/
	private int fechaActual = DateUtil.actualDateToInt();
	
	/*@sqlmap_column hora_actual*/
	private int horaActual = DateUtil.actualTimeToInt();
	

	/**
	 * 
	 */
	public Bitacora() {
		super();
	}

	/**
	 * @param idUsuario
	 * @param fechaLecturaInicial
	 * @param fechaLecturaFin
	 * @param horaLecturaInicial
	 * @param horaLecturaFin
	 */
	public Bitacora(int idUsuario, int fechaLecturaInicial,
			int fechaLecturaFin, String horaLecturaInicial, String horaLecturaFin) {
		super();
		this.idUsuario = idUsuario;
		this.fechaLecturaInicial = fechaLecturaInicial;
		this.fechaLecturaFin = fechaLecturaFin;
		this.horaLecturaInicial = horaLecturaInicial;
		this.horaLecturaFin = horaLecturaFin;
	}

	/**
	 * @return the idUsuario
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the fechaLecturaInicial
	 */
	public int getFechaLecturaInicial() {
		return fechaLecturaInicial;
	}

	/**
	 * @param fechaLecturaInicial the fechaLecturaInicial to set
	 */
	public void setFechaLecturaInicial(int fechaLecturaInicial) {
		this.fechaLecturaInicial = fechaLecturaInicial;
	}

	/**
	 * @return the fechaLecturaFin
	 */
	public int getFechaLecturaFin() {
		return fechaLecturaFin;
	}

	/**
	 * @param fechaLecturaFin the fechaLecturaFin to set
	 */
	public void setFechaLecturaFin(int fechaLecturaFin) {
		this.fechaLecturaFin = fechaLecturaFin;
	}

	/**
	 * @return the horaLecturaInicial
	 */
	public String getHoraLecturaInicial() {
		return horaLecturaInicial;
	}

	/**
	 * @param horaLecturaInicial the horaLecturaInicial to set
	 */
	public void setHoraLecturaInicial(String horaLecturaInicial) {
		this.horaLecturaInicial = horaLecturaInicial;
	}

	/**
	 * @return the horaLecturaFin
	 */
	public String getHoraLecturaFin() {
		return horaLecturaFin;
	}

	/**
	 * @param horaLecturaFin the horaLecturaFin to set
	 */
	public void setHoraLecturaFin(String horaLecturaFin) {
		this.horaLecturaFin = horaLecturaFin;
	}

	/**
	 * @return the fechaActual
	 */
	public int getFechaActual() {
		return fechaActual;
	}

	/**
	 * @param fechaActual the fechaActual to set
	 */
	public void setFechaActual(int fechaActual) {
		this.fechaActual = fechaActual;
	}

	/**
	 * @return the horaActual
	 */
	public int getHoraActual() {
		return horaActual;
	}

	/**
	 * @param horaActual the horaActual to set
	 */
	public void setHoraActual(int horaActual) {
		this.horaActual = horaActual;
	}
}