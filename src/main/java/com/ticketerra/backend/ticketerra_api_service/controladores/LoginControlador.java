package com.ticketerra.backend.ticketerra_api_service.controladores;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.servicios.LoginServicio;

@RestController
@RequestMapping("/api/usuarios")
public class LoginControlador {
    
    @Autowired
    private LoginServicio loginServicio;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");

        Optional<Usuario> usuario = loginServicio.obtenerPorCorreo(correo);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }
    
    // Recuperar contraseña (generar el token y almacenarlo)
    @PostMapping("/recuperar")
    public ResponseEntity<?> recuperarContrasena(@RequestBody Usuario usuario) {
        boolean tokenGenerado = loginServicio.generarTokenRecuperacion(usuario.getCorreo());
        if (tokenGenerado) {
            return ResponseEntity.ok(Map.of("mensaje", "Token de recuperación generado."));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Correo no encontrado."));
        }
    }

    // Restablecer contraseña con token
    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerContrasena(@RequestParam String token, @RequestParam String nuevaContrasena) {
        boolean contrasenaRestablecida = loginServicio.restablecerContrasena(token, nuevaContrasena);
        if (contrasenaRestablecida) {
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña restablecida exitosamente."));
        } else {
            return ResponseEntity.status(400).body(Map.of("error", "Token inválido o expirado."));
        }
    }
}
