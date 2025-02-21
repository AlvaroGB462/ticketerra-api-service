package com.ticketerra.backend.ticketerra_api_service.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.servicios.RegistroServicio;

@CrossOrigin(origins = "http://localhost:9094")
@RestController
@RequestMapping("/api/usuarios")
public class RegistroControlador {

    @Autowired
    private RegistroServicio registroServicio;

    // Registro sin activar al usuario en la base de datos
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        return registroServicio.registrarUsuario(usuario);  // Solo se guarda el token, no el usuario
    }

    // Buscar usuario por token
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorToken(@RequestParam("token") String token) {
        return registroServicio.buscarPorToken(token);
    }

    // Activar usuario después de la confirmación
    @PostMapping("/activar")
    public ResponseEntity<?> activarUsuario(@RequestBody Usuario usuario) {
        return registroServicio.activarUsuario(usuario);  // Aquí se guarda el usuario en la base de datos
    }
}
