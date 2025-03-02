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

    // Endpoint para registrar un nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        logger.info("Intentando registrar usuario con correo: {}", usuario.getCorreo());
        ResponseEntity<?> response = registroServicio.registrarUsuario(usuario); // Llama al servicio para registrar al usuario
        logger.info("Registro de usuario procesado para: {}", usuario.getCorreo());
        return response; // Devuelve la respuesta del servicio
    }

    // Endpoint para buscar un usuario por su token de confirmación
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorToken(@RequestParam("token") String token) {
        logger.info("Buscando usuario con token: {}", token);
        return registroServicio.buscarPorToken(token); // Llama al servicio para buscar el usuario por token
    }

    // Endpoint para activar un usuario
    @PostMapping("/activar")
    public ResponseEntity<?> activarUsuario(@RequestBody Usuario usuario) {
        logger.info("Activando usuario con correo: {}", usuario.getCorreo());
        return registroServicio.activarUsuario(usuario); // Llama al servicio para activar al usuario
    }
}