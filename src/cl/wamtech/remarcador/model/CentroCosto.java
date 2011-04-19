/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table centros_costos
 */
public class CentroCosto implements IPersistente{

	/*@sqlmap_column_primary_key id_centro_costo*/
	private int id = -1;
	
	/*@sqlmap_column nombre_centro_costo*/
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
