/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.DateUtil;
import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table logs_accesos
 */
public class LogAcceso implements IPersistente {

	/*@sqlmap_column_primary_key id*/
	private int id = -1;
	
	/*@sqlmap_column id_usuario*/
	private Usuario usuario;
	
	/*@sqlmap_column fecha_acceso*/
	private int fechaAcceso = DateUtil.actualDateToInt();
	
	/*@sqlmap_column hora_acceso*/
	private int horaAcceso = DateUtil.actualTimeToInt();

	/**
	 * 
	 */
	public LogAcceso() {
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
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the fechaAcceso
	 */
	public int getFechaAcceso() {
		return fechaAcceso;
	}

	/**
	 * @param fechaAcceso the fechaAcceso to set
	 */
	public void setFechaAcceso(int fechaAcceso) {
		this.fechaAcceso = fechaAcceso;
	}

	/**
	 * @return the horaAcceso
	 */
	public int getHoraAcceso() {
		return horaAcceso;
	}

	/**
	 * @param horaAcceso the horaAcceso to set
	 */
	public void setHoraAcceso(int horaAcceso) {
		this.horaAcceso = horaAcceso;
	}
}
