package com.ticketerra.backend.ticketerra_api_service.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.servicios.RegistroServicio;

@CrossOrigin(origins = "http://localhost:9094")
@RestController
@RequestMapping("/api/usuarios")
public class RegistroControlador {

    private static final Logger logger = LoggerFactory.getLogger(RegistroControlador.class);

    @Autowired
    private RegistroServicio registroServicio;

    // Registro sin activar al usuario en la base de datos
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        logger.info("Intentando registrar usuario con correo: {}", usuario.getCorreo());
        ResponseEntity<?> response = registroServicio.registrarUsuario(usuario);
        logger.info("Registro de usuario procesado para: {}", usuario.getCorreo());
        return response;
    }

    // Buscar usuario por token
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorToken(@RequestParam("token") String token) {
        logger.info("Buscando usuario con token: {}", token);
        ResponseEntity<Usuario> response = registroServicio.buscarPorToken(token);
        
        if (response.getBody() != null) {
            logger.info("Usuario encontrado para token: {}", token);
        } else {
            logger.warn("No se encontró usuario para token: {}", token);
        }

        return response;
    }

    // Activar usuario después de la confirmación
    @PostMapping("/activar")
    public ResponseEntity<?> activarUsuario(@RequestBody Usuario usuario) {
        logger.info("Activando usuario con correo: {}", usuario.getCorreo());
        ResponseEntity<?> response = registroServicio.activarUsuario(usuario);
        logger.info("Usuario activado correctamente: {}", usuario.getCorreo());
        return response;
    }
}
