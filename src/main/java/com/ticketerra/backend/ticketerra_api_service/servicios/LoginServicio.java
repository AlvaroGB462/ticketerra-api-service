package com.ticketerra.backend.ticketerra_api_service.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
