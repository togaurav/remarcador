/**
 * 
 */
package cl.wamtech.remarcador.model;

/**
 * @author jorge
 * @sqlmap_table t_bigint
 */
public class BigInt {

	/*@sqlmap_column codigo_modem*/
	private int codigoModem = -1;
	
	/*@sqlmap_column id_canal*/
	private int idCanal = -1;
	
	/*@sqlmap_column fecha_ts*/
	private int fechaRegistro = -1;
	
	/*@sqlmap_column dato_bigint*/
	private int dato = -1;
	
	/*@sqlmap_column fecha_ts_serv*/
	private int fechaRegistroServer = -1;
	
	/**
	 * 
	 */
	public BigInt() {
		super();
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
	 * @return the fechaRegistro
	 */
	public int getFechaRegistro() {
		return fechaRegistro;
	}
	
	/**
	 * @return the dato
	 */
	public int getDato() {
		return dato;
	}
	
	/**
	 * @return the fechaRegistroServer
	 */
	public int getFechaRegistroServer() {
		return fechaRegistroServer;
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
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(int fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	/**
	 * @param dato the dato to set
	 */
	public void setDato(int dato) {
		this.dato = dato;
	}
	
	/**
	 * @param fechaRegistroServer the fechaRegistroServer to set
	 */
	public void setFechaRegistroServer(int fechaRegistroServer) {
		this.fechaRegistroServer = fechaRegistroServer;
	}

}
