package com.ticketerra.backend.ticketerra_api_service.servicios;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@Service
public class RegistroServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // Método para registrar un nuevo usuario
    public ResponseEntity<?> registrarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario); // Guarda el usuario en la base de datos
        return ResponseEntity.ok("Usuario registrado, revisa tu correo para activar la cuenta."); // Devuelve un mensaje de éxito
    }

    // Método para buscar un usuario por su token de confirmación
    public ResponseEntity<Usuario> buscarPorToken(String token) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenConfirmacion(token); // Busca el usuario por token
        return usuarioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null)); // Devuelve el usuario si existe, o un error si no
    }

    // Método para activar un usuario
    public ResponseEntity<?> activarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenConfirmacion(usuario.getTokenConfirmacion()); // Busca el usuario por token

        if (usuarioOptional.isPresent()) {
            Usuario usuarioBD = usuarioOptional.get();
            usuarioBD.setActivo(true); // Activa al usuario
            usuarioBD.setTokenConfirmacion(null); // Elimina el token de confirmación
            usuarioRepositorio.save(usuarioBD); // Guarda los cambios en la base de datos
            return ResponseEntity.ok("Usuario activado correctamente."); // Devuelve un mensaje de éxito
        }
        return ResponseEntity.badRequest().body("Token inválido."); // Devuelve un mensaje de error si el token no es válido
    }
}