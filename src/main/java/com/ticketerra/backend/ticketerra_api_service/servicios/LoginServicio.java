package com.ticketerra.backend.ticketerra_api_service.servicios;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@Service
public class LoginServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    // Método para generar el token de recuperación y almacenarlo
    public boolean generarTokenRecuperacion(String correo) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Generamos el token de recuperación y lo asignamos
            String token = UUID.randomUUID().toString();
            usuario.setTokenRecuperacion(token);
            usuario.setTokenExpiracion(System.currentTimeMillis() + 3600000); // Expira en 1 hora

            // Guardamos los cambios en la base de datos
            usuarioRepositorio.save(usuario);

            return true;
        } else {
            return false;
        }
    }

    // Método para restablecer la contraseña usando el token
    public boolean restablecerContrasena(String token, String nuevaContrasena) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenRecuperacion(token);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Verificar si el token ha expirado
            if (usuario.getTokenExpiracion() > System.currentTimeMillis()) {
                usuario.setContrasena(nuevaContrasena);
                usuario.setTokenRecuperacion(null);  // Limpiar el token de recuperación después de usarlo

                // Guardamos los cambios en la base de datos
                usuarioRepositorio.save(usuario);
                return true;
            }
        }

        return false;
    }
}
