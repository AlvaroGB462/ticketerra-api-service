package com.ticketerra.backend.ticketerra_api_service.controladores;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:9094")
public class LoginControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        Optional<Usuario> usuario = usuarioRepositorio.findByCorreo(correo);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }

    @PostMapping("/guardarToken")
    public ResponseEntity<?> guardarToken(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        String token = request.get("token");

        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setTokenRecuperacion(token);
            usuario.setTokenExpiracion(System.currentTimeMillis() + 3600000);

            usuarioRepositorio.save(usuario);
            return ResponseEntity.ok(Map.of("mensaje", "Token guardado correctamente."));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }

    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String nuevaContrasena = request.get("nuevaContrasena");

        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenRecuperacion(token);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            if (usuario.getTokenExpiracion() > System.currentTimeMillis()) {
                usuario.setContrasena(nuevaContrasena);
                usuario.setTokenRecuperacion(null);
                usuarioRepositorio.save(usuario);
                return ResponseEntity.ok(Map.of("mensaje", "Contraseña restablecida exitosamente."));
            } else {
                return ResponseEntity.status(400).body(Map.of("error", "Token inválido o expirado."));
            }
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Token no encontrado."));
        }
    }

    @PutMapping("/activar/{correo}")
    public ResponseEntity<?> activarUsuario(@PathVariable String correo) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.isActivo()) {
                return ResponseEntity.status(400).body(Map.of("error", "El usuario ya está activo"));
            }

            usuario.setActivo(true);
            usuarioRepositorio.save(usuario);
            return ResponseEntity.ok("Usuario activado correctamente.");
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
