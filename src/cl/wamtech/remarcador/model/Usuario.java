/**
 * 
 */
package cl.wamtech.remarcador.model;

import cl.wamtech.remarcador.util.IPersistente;

/**
 * @author jorge
 * @sqlmap_table usuarios
 */
public class Usuario implements IPersistente {
	
	/*@sqlmap_column_primary_key id*/
	private int id = -1;
	
	/*@sqlmap_column usuario*/
	private String usuario;
	
	/*@sqlmap_column clave*/
	private String clave;
	
	/*@sqlmap_column nombre*/
	private String nombre;
	
	/*@sqlmap_column apellido_paterno*/
	private String apellidoPaterno;
	
	/*@sqlmap_column apellido_materno*/
	private String apellidoMaterno;
	
	/*@sqlmap_column email*/
	private String email;
	
	/*@sqlmap_column id_perfil*/
	private Perfil perfil;

	/**
	 * 
	 */
	public Usuario() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the perfil
	 */
	public Perfil getPerfil() {
		return perfil;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
}
