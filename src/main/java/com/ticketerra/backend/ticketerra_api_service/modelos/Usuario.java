package com.ticketerra.backend.ticketerra_api_service.modelos;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Column(unique = true, nullable = false)
	private String correo;

	@Column(nullable = false)
	private String nombreCompleto;

	@Column(nullable = false)
	private String contrasena;

	@Column(unique = true)
	private String tokenConfirmacion;

	@Column(nullable = false)
	private Boolean activo = false;

	@Column(unique = true)
	private String tokenRecuperacion;

	@Column
	private Long tokenExpiracion;

	@Column(nullable = false)
	private String rol = "user";

	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Timestamp fechaRegistro;

	@Column(nullable = true)
	private String telefono;

	@Column(nullable = true)
	private String codigoPostal;

	@Column(name = "foto")
	private byte[] foto;

	public Usuario() {
	}

	public Usuario(String correo, String nombreCompleto, String contrasena, String tokenConfirmacion, Boolean activo,
			String tokenRecuperacion, Long tokenExpiracion, String rol, Timestamp fechaRegistro, String telefono,
			String codigoPostal, byte[] foto) {
		this.correo = correo;
		this.nombreCompleto = nombreCompleto;
		this.contrasena = contrasena;
		this.tokenConfirmacion = tokenConfirmacion;
		this.activo = activo;
		this.tokenRecuperacion = tokenRecuperacion;
		this.tokenExpiracion = tokenExpiracion;
		this.rol = rol;
		this.fechaRegistro = fechaRegistro;
		this.telefono = telefono;
		this.codigoPostal = codigoPostal;
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getTokenConfirmacion() {
		return tokenConfirmacion;
	}

	public void setTokenConfirmacion(String tokenConfirmacion) {
		this.tokenConfirmacion = tokenConfirmacion;
	}

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getTokenRecuperacion() {
		return tokenRecuperacion;
	}

	public void setTokenRecuperacion(String tokenRecuperacion) {
		this.tokenRecuperacion = tokenRecuperacion;
	}

	public Long getTokenExpiracion() {
		return tokenExpiracion;
	}

	public void setTokenExpiracion(Long tokenExpiracion) {
		this.tokenExpiracion = tokenExpiracion;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] fotoPerfil) {
		this.foto = fotoPerfil;
	}

	@Override
	public String toString() {
		return "Usuario{" + "id=" + id + ", correo='" + correo + '\'' + ", nombreCompleto='" + nombreCompleto + '\''
				+ ", contrasena='" + contrasena + '\'' + ", tokenConfirmacion='" + tokenConfirmacion + '\''
				+ ", activo=" + activo + ", tokenRecuperacion='" + tokenRecuperacion + '\'' + ", tokenExpiracion="
				+ tokenExpiracion + ", rol='" + rol + '\'' + ", fechaRegistro=" + fechaRegistro + ", telefono='"
				+ telefono + '\'' + ", codigoPostal='" + codigoPostal + '\'' + ", fotoPerfil="
				+ (foto != null ? "Sí" : "No") + '}';
	}
}
