package com.ticketerra.backend.ticketerra_api_service.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@Service
public class LoginServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // Método para obtener un usuario por su correo (solo CRUD)
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo); // Busca el usuario en la base de datos
    }

    // Método para guardar un token de recuperación (solo CRUD)
    public void guardarToken(String correo, String token) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo); // Busca el usuario en la base de datos
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setTokenRecuperacion(token); // Guarda el token en el usuario
            usuario.setTokenExpiracion(System.currentTimeMillis() + 3600000); // Establece el tiempo de expiración (1 hora)
            usuarioRepositorio.save(usuario); // Guarda los cambios en la base de datos
        }
    }

    // Método para restablecer la contraseña (solo CRUD)
    public void restablecerContrasena(String token, String nuevaContrasena) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenRecuperacion(token); // Busca el usuario por el token
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setContrasena(nuevaContrasena); // Actualiza la contraseña
            usuario.setTokenRecuperacion(null); // Elimina el token de recuperación
            usuarioRepositorio.save(usuario); // Guarda los cambios en la base de datos
        }
    }

    // Método para actualizar el estado de un usuario (solo CRUD)
    public void actualizarEstado(String correo, boolean activo) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo); // Busca el usuario en la base de datos
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setActivo(activo); // Actualiza el estado del usuario
            usuarioRepositorio.save(usuario); // Guarda los cambios en la base de datos
        }
    }
}