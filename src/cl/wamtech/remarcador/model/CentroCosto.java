/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table CENTROS_COSTOS
 */
public class CentroCosto implements IPersistente{

	/*@sqlmap_column_primary_key ID_CENTRO_COSTO*/
	private int id = -1;
	
	/*@sqlmap_column NOMBRE_CENTRO_COSTO*/
	private String nombre;
	
	/**
	 * 
	 */
	public CentroCosto() {
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

}
