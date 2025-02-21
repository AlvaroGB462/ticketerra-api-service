package com.ticketerra.backend.ticketerra_api_service.modelos;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String contrasena;

    @Column(unique = true)
    private String tokenConfirmacion;  // Token para activar la cuenta

    @Column(nullable = false)
    private Boolean activo = false;  // Indica si el usuario confirmó su cuenta

    @Column(unique = true)
    private String tokenRecuperacion;  // Token para restablecer contraseña

    @Column
    private Long tokenExpiracion;  // Fecha de expiración del token de recuperación

    @Column(nullable = false)
    private String rol = "user";  // Campo agregado para el rol

    @Column
    private Timestamp fechaRegistro;  // Nuevo campo de fecha de registro

    @Column(nullable = true)
    private String telefono;  // Nuevo campo teléfono

    @Column(nullable = true)
    private String codigoPostal;  // Nuevo campo código postal

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String correo, String nombreCompleto, String contrasena, String tokenConfirmacion, Boolean activo, String tokenRecuperacion, Long tokenExpiracion, String rol, Timestamp fechaRegistro, String telefono, String codigoPostal) {
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
    }

    // Getters y Setters
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", correo='" + correo + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", tokenConfirmacion='" + tokenConfirmacion + '\'' +
                ", activo=" + activo +
                ", tokenRecuperacion='" + tokenRecuperacion + '\'' +
                ", tokenExpiracion=" + tokenExpiracion +
                ", rol='" + rol + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", telefono='" + telefono + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                '}';  // Agregar telefono y codigoPostal en el toString
    }
}
