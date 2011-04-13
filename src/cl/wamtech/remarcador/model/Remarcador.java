/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table REMARCADORES
 */
public class Remarcador implements IPersistente {

	/*@sqlmap_column_primary_key ID*/
	private int id = -1;
	
	/*@sqlmap_column NOMBRE*/
	private String nombre;
	
	/*@sqlmap_column LOCAL*/
	private String local;
	
	/*@sqlmap_column MARCA_REMARCADOR*/
	private String marcaRemarcador;
	
	/*@sqlmap_column MODELO_REMARCADOR*/
	private String modeloRemarcador;
	
	/*@sqlmap_column MULTIPLICADOR*/
	private double multiplicador = -1;
	
	/*@sqlmap_column TABLERO*/
	private String tablero;
	
	/*@sqlmap_column NUMERO_MEDIDOR*/
	private String numeroMedidor;
	
	/*@sqlmap_column NODO*/
	private String nodo;
	
	/*@sqlmap_column OBSERVACION*/
	private String observacion;
	
	/*@sqlmap_column ID_CENTRO_COSTO*/
	private CentroCosto centroCosto;
	
	/*@sqlmap_column ID_CUENTA*/
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
	 * @return the local
	 */
	public String getLocal() {
		return local;
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
	 * @param local the local to set
	 */
	public void setLocal(String local) {
		this.local = local;
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

}
