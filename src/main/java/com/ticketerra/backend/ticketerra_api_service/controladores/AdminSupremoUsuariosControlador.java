package com.ticketerra.backend.ticketerra_api_service.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:9094")
public class AdminSupremoUsuariosControlador {

    private static final Logger logger = LoggerFactory.getLogger(AdminSupremoUsuariosControlador.class);
    private final UsuarioRepositorio usuarioRepositorio;

    // Constructor para inyectar el repositorio de usuarios
    public AdminSupremoUsuariosControlador(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Endpoint para obtener la lista de todos los usuarios
    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll(); // Obtiene todos los usuarios de la base de datos
        logger.info("Usuarios obtenidos desde la base de datos: {}", usuarios);

        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios en la base de datos.");
            return ResponseEntity.noContent().build(); // Devuelve una respuesta vacía si no hay usuarios
        }

        return ResponseEntity.ok(usuarios); // Devuelve la lista de usuarios
    }

    // Endpoint para eliminar un usuario por su correo
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUsuario(@RequestParam String correo) {
        usuarioRepositorio.deleteByCorreo(correo); // Elimina el usuario por correo
        logger.info("Usuario eliminado con correo: {}", correo);
        return ResponseEntity.ok("Usuario eliminado correctamente."); // Devuelve un mensaje de éxito
    }
}