package com.ticketerra.backend.ticketerra_api_service.controladores;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@CrossOrigin(origins = "http://localhost:9094")  // Permite la comunicación con la capa web
@RestController
@RequestMapping("/api/usuarios")
public class LoginControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // API para recuperar usuario por correo
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        Optional<Usuario> usuario = usuarioRepositorio.findByCorreo(correo);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get()); // Retorna el usuario si se encuentra en la base de datos
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }
    
    

    // API para guardar el token de recuperación en la base de datos
    @PostMapping("/guardarToken")
    public ResponseEntity<?> guardarToken(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        String token = request.get("token");

        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setTokenRecuperacion(token);  // Guardar el token en el usuario
            usuario.setTokenExpiracion(System.currentTimeMillis() + 3600000);  // Token expira en 1 hora

            usuarioRepositorio.save(usuario);  // Guardar el usuario con el token actualizado
            return ResponseEntity.ok(Map.of("mensaje", "Token guardado correctamente."));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }

    // API para restablecer la contraseña usando un token
    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String nuevaContrasena = request.get("nuevaContrasena");

        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenRecuperacion(token);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Verificamos si el token ha expirado
            if (usuario.getTokenExpiracion() > System.currentTimeMillis()) {
                usuario.setContrasena(nuevaContrasena);  // Establecer nueva contraseña
                usuario.setTokenRecuperacion(null);  // Limpiar el token
                usuarioRepositorio.save(usuario);  // Guardar los cambios en la base de datos
                return ResponseEntity.ok(Map.of("mensaje", "Contraseña restablecida exitosamente."));
            } else {
                return ResponseEntity.status(400).body(Map.of("error", "Token inválido o expirado."));
            }
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Token no encontrado."));
        }
    }
}
