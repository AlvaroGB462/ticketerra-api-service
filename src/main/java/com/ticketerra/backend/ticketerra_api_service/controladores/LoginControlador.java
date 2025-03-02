package com.ticketerra.backend.ticketerra_api_service.controladores;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.servicios.LoginServicio;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:9094")
public class LoginControlador {

    private static final Logger logger = LoggerFactory.getLogger(LoginControlador.class);

    @Autowired
    private LoginServicio loginServicio;

    // Endpoint para obtener un usuario por su correo (solo CRUD)
    @PostMapping("/obtenerPorCorreo")
    public ResponseEntity<?> obtenerPorCorreo(@RequestBody Map<String, String> request) {
        String correo = request.get("correo"); // Obtiene el correo del cuerpo de la solicitud
        Optional<Usuario> usuario = loginServicio.obtenerPorCorreo(correo); // Busca el usuario en la base de datos

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get()); // Devuelve el usuario si existe
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado")); // Devuelve error 404 si no existe
        }
    }

    // Endpoint para guardar un token de recuperación (solo CRUD)
    @PostMapping("/guardarToken")
    public ResponseEntity<?> guardarToken(@RequestBody Map<String, String> request) {
        String correo = request.get("correo"); // Obtiene el correo del cuerpo de la solicitud
        String token = request.get("token"); // Obtiene el token del cuerpo de la solicitud

        loginServicio.guardarToken(correo, token); // Guarda el token en la base de datos
        return ResponseEntity.ok(Map.of("mensaje", "Token guardado correctamente.")); // Devuelve un mensaje de éxito
    }

    // Endpoint para restablecer la contraseña (solo CRUD)
    @PostMapping("/restablecerContrasena")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        String token = request.get("token"); // Obtiene el token del cuerpo de la solicitud
        String nuevaContrasena = request.get("nuevaContrasena"); // Obtiene la nueva contraseña del cuerpo de la solicitud

        loginServicio.restablecerContrasena(token, nuevaContrasena); // Restablece la contraseña en la base de datos
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña restablecida exitosamente.")); // Devuelve un mensaje de éxito
    }

    // Endpoint para activar o desactivar un usuario (solo CRUD)
    @PutMapping("/actualizarEstado/{correo}")
    public ResponseEntity<?> actualizarEstado(@PathVariable String correo, @RequestParam boolean activo) {
        loginServicio.actualizarEstado(correo, activo); // Actualiza el estado del usuario en la base de datos
        return ResponseEntity.ok("Estado actualizado correctamente."); // Devuelve un mensaje de éxito
    }
}