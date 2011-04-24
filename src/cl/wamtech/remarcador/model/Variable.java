/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table variables
 */
public class Variable implements IPersistente {

	/*@sqlmap_column_primary_key id*/
	private int id = -1;
	
	/*@sqlmap_column codigo_modem*/
	private int codigoModem = -1;
	
	/*@sqlmap_column id_canal*/
	private int idCanal = -1;
	
	/*@sqlmap_column id_remarcador*/
	private Remarcador remarcador;
	
	/**
	 * 
	 */
	public Variable() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the codigoModem
	 */
	public int getCodigoModem() {
		return codigoModem;
	}

	/**
	 * @return the idCanal
	 */
	public int getIdCanal() {
		return idCanal;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param codigoModem the codigoModem to set
	 */
	public void setCodigoModem(int codigoModem) {
		this.codigoModem = codigoModem;
	}

	/**
	 * @param idCanal the idCanal to set
	 */
	public void setIdCanal(int idCanal) {
		this.idCanal = idCanal;
	}

	/**
	 * @return the remarcador
	 */
	public Remarcador getRemarcador() {
		return remarcador;
	}

	/**
	 * @param remarcador the remarcador to set
	 */
	public void setRemarcador(Remarcador remarcador) {
		this.remarcador = remarcador;
	}
	
}
