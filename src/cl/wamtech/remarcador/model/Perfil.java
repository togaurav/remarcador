/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table perfiles
 */
public class Perfil implements IPersistente {

	/*@sqlmap_column_primary_key id*/
	private int id = -1;
	
	/*@sqlmap_column nombre*/
	private String nombre;
	
	/*@sqlmap_column descripcion*/
	private String descripcion;
	
	/*@sqlmap_column codigo*/
	private String codigo;
	
	/**
	 * 
	 */
	public Perfil() {
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
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
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
