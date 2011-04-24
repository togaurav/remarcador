/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table remarcadores
 */
public class Remarcador implements IPersistente {

	/*@sqlmap_column_primary_key id*/
	private int id = -1;
	
	/*@sqlmap_column nombre*/
	private String nombre;
	
	/*@sqlmap_column local_remarcador*/
	private String localRemarcador;
	
	/*@sqlmap_column marca_remarcador*/
	private String marcaRemarcador;
	
	/*@sqlmap_column modelo_remarcador*/
	private String modeloRemarcador;
	
	/*@sqlmap_column multiplicador*/
	private double multiplicador = -1;
	
	/*@sqlmap_column tablero*/
	private String tablero;
	
	/*@sqlmap_column numero_medidor*/
	private String numeroMedidor;
	
	/*@sqlmap_column nodo*/
	private String nodo;
	
	/*@sqlmap_column observacion*/
	private String observacion;
	
	/*@sqlmap_column id_centro_costo*/
	private CentroCosto centroCosto;
	
	/*@sqlmap_column id_cuenta*/
	private Cuenta cuenta;
	
	/**
	 * 
	 */
	public Remarcador() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the marcaRemarcador
	 */
	public String getMarcaRemarcador() {
		return marcaRemarcador;
	}

	/**
	 * @return the modeloRemarcador
	 */
	public String getModeloRemarcador() {
		return modeloRemarcador;
	}

	/**
	 * @return the multiplicador
	 */
	public double getMultiplicador() {
		return multiplicador;
	}

	/**
	 * @return the tablero
	 */
	public String getTablero() {
		return tablero;
	}

	/**
	 * @return the numeroMedidor
	 */
	public String getNumeroMedidor() {
		return numeroMedidor;
	}

	/**
	 * @return the nodo
	 */
	public String getNodo() {
		return nodo;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param marcaRemarcador the marcaRemarcador to set
	 */
	public void setMarcaRemarcador(String marcaRemarcador) {
		this.marcaRemarcador = marcaRemarcador;
	}

	/**
	 * @param modeloRemarcador the modeloRemarcador to set
	 */
	public void setModeloRemarcador(String modeloRemarcador) {
		this.modeloRemarcador = modeloRemarcador;
	}

	/**
	 * @param multiplicador the multiplicador to set
	 */
	public void setMultiplicador(double multiplicador) {
		this.multiplicador = multiplicador;
	}

	/**
	 * @param tablero the tablero to set
	 */
	public void setTablero(String tablero) {
		this.tablero = tablero;
	}

	/**
	 * @param numeroMedidor the numeroMedidor to set
	 */
	public void setNumeroMedidor(String numeroMedidor) {
		this.numeroMedidor = numeroMedidor;
	}

	/**
	 * @param nodo the nodo to set
	 */
	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the centroCosto
	 */
	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	/**
	 * @return the cuenta
	 */
	public Cuenta getCuenta() {
		return cuenta;
	}

	/**
	 * @param centroCosto the centroCosto to set
	 */
	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the localRemarcador
	 */
	public String getLocalRemarcador() {
		return localRemarcador;
	}

	/**
	 * @param localRemarcador the localRemarcador to set
	 */
	public void setLocalRemarcador(String localRemarcador) {
		this.localRemarcador = localRemarcador;
	}
}
