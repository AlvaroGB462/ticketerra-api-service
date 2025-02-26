package com.ticketerra.backend.ticketerra_api_service.controladores;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:9094")
public class LoginControlador {

    private static final Logger logger = LoggerFactory.getLogger(LoginControlador.class);

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        logger.info("Intento de inicio de sesión con correo: {}", correo);

        Optional<Usuario> usuario = usuarioRepositorio.findByCorreo(correo);

        if (usuario.isPresent()) {
            logger.info("Inicio de sesión exitoso para usuario: {}", correo);
            return ResponseEntity.ok(usuario.get());
        } else {
            logger.warn("Intento de inicio de sesión fallido. Usuario no encontrado: {}", correo);
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }

    @PostMapping("/guardarToken")
    public ResponseEntity<?> guardarToken(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        String token = request.get("token");

        logger.info("Intentando guardar token para usuario: {}", correo);

        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setTokenRecuperacion(token);
            usuario.setTokenExpiracion(System.currentTimeMillis() + 3600000);

            usuarioRepositorio.save(usuario);
            logger.info("Token guardado exitosamente para usuario: {}", correo);
            return ResponseEntity.ok(Map.of("mensaje", "Token guardado correctamente."));
        } else {
            logger.warn("Intento de guardar token fallido. Usuario no encontrado: {}", correo);
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }

    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String nuevaContrasena = request.get("nuevaContrasena");

        logger.info("Intento de restablecimiento de contraseña con token: {}", token);

        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenRecuperacion(token);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            if (usuario.getTokenExpiracion() > System.currentTimeMillis()) {
                usuario.setContrasena(nuevaContrasena);
                usuario.setTokenRecuperacion(null);
                usuarioRepositorio.save(usuario);
                logger.info("Contraseña restablecida correctamente para usuario: {}", usuario.getCorreo());
                return ResponseEntity.ok(Map.of("mensaje", "Contraseña restablecida exitosamente."));
            } else {
                logger.warn("Token de restablecimiento inválido o expirado: {}", token);
                return ResponseEntity.status(400).body(Map.of("error", "Token inválido o expirado."));
            }
        } else {
            logger.warn("Intento de restablecimiento fallido. Token no encontrado: {}", token);
            return ResponseEntity.status(404).body(Map.of("error", "Token no encontrado."));
        }
    }

    @PutMapping("/activar/{correo}")
    public ResponseEntity<?> activarUsuario(@PathVariable String correo) {
        logger.info("Intento de activación de usuario: {}", correo);

        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.isActivo()) {
                logger.warn("Intento de activación fallido. Usuario ya activo: {}", correo);
                return ResponseEntity.status(400).body(Map.of("error", "El usuario ya está activo"));
            }

            usuario.setActivo(true);
            usuarioRepositorio.save(usuario);
            logger.info("Usuario activado correctamente: {}", correo);
            return ResponseEntity.ok("Usuario activado correctamente.");
        } else {
            logger.warn("Intento de activación fallido. Usuario no encontrado: {}", correo);
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }

    @PutMapping("/desactivar/{correo}")
    public ResponseEntity<?> desactivarUsuario(@PathVariable String correo) {
        logger.info("Intento de desactivación de usuario: {}", correo);

        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (!usuario.isActivo()) {
                logger.warn("Intento de desactivación fallido. Usuario ya desactivado: {}", correo);
                return ResponseEntity.status(400).body(Map.of("error", "El usuario ya está desactivado"));
            }

            usuario.setActivo(false);
            usuarioRepositorio.save(usuario);
            logger.info("Usuario desactivado correctamente: {}", correo);
            return ResponseEntity.ok("Usuario desactivado correctamente.");
        } else {
            logger.warn("Intento de desactivación fallido. Usuario no encontrado: {}", correo);
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
    }
}
