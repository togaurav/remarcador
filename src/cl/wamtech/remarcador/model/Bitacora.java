/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table bitacoras
 */
public class Bitacora implements IPersistente {
	
	/*@sqlmap_column_primary_key id*/
	private int id = -1;
	
	/*@sqlmap_column id_remarcador*/
	private int idRemarcador = -1;
	
	/*@sqlmap_column id_usuario*/
	private int idUsuario = -1;
	
	/*@sqlmap_column fecha_inicial*/
	private int fechaInicial = -1;
	
	/*@sqlmap_column fecha_final*/
	private int fechaFinal = -1;

	/**
	 * 
	 */
	public Bitacora() {
		// TODO Auto-generated constructor stub
	}

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

	/**
	 * @return the idRemarcador
	 */
	public int getIdRemarcador() {
		return idRemarcador;
	}

	/**
	 * @param idRemarcador the idRemarcador to set
	 */
	public void setIdRemarcador(int idRemarcador) {
		this.idRemarcador = idRemarcador;
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
	 * @return the fechaInicial
	 */
	public int getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * @param fechaInicial the fechaInicial to set
	 */
	public void setFechaInicial(int fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * @return the fechaFinal
	 */
	public int getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(int fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
}
