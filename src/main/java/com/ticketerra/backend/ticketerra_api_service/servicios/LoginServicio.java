package com.ticketerra.backend.ticketerra_api_service.servicios;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@Service
public class LoginServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // Método para obtener el usuario por correo
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    // Activar usuario en la base de datos
    public ResponseEntity<?> activarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(usuario.getCorreo());

        if (usuarioOptional.isPresent()) {
            Usuario usuarioBD = usuarioOptional.get();
            usuarioBD.setActivo(true);

            usuarioRepositorio.save(usuarioBD);
            return ResponseEntity.ok("Usuario activado correctamente.");
        }

        return ResponseEntity.badRequest().body("Usuario no encontrado.");
    }

    // Método para restablecer la contraseña usando el token
    public boolean restablecerContrasena(String token, String nuevaContrasena) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenRecuperacion(token);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Verificamos si el token ha expirado
            if (usuario.getTokenExpiracion() > System.currentTimeMillis()) {
                usuario.setContrasena(nuevaContrasena);  // Establecer nueva contraseña
                usuario.setTokenRecuperacion(null);  // Limpiar el token
                usuarioRepositorio.save(usuario);  // Guardar los cambios en la base de datos
                return true;
            }
        }

        return false;
    }

    // API para activar un usuario
    @PutMapping("/activar/{correo}")
    public ResponseEntity<?> activarUsuario(@PathVariable String correo) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.isActivo()) {
                return ResponseEntity.status(400).body(Map.of("error", "El usuario ya está activo"));
            }

            // Usar el servicio para activar al usuario
            return activarUsuario(usuario);
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }

    @PutMapping("/desactivar/{correo}")
    public ResponseEntity<?> desactivarUsuario(@PathVariable String correo) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (!usuario.isActivo()) {
                return ResponseEntity.status(400).body(Map.of("error", "El usuario ya está desactivado"));
            }

            usuario.setActivo(false);
            usuarioRepositorio.save(usuario);
            return ResponseEntity.ok("Usuario desactivado correctamente.");
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }
}
