package com.ticketerra.backend.ticketerra_api_service.controladores;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:9094")
public class AdminSupremoUsuariosControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    public AdminSupremoUsuariosControlador(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Método para obtener todos los usuarios
    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        System.out.println("Usuarios obtenidos en la API: " + usuarios);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build(); // Si la lista está vacía, respondemos con código 204
        }
        return ResponseEntity.ok(usuarios); // Si hay usuarios, respondemos con ellos
    }

    // Método para eliminar un usuario
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUsuario(@RequestParam String correo) {
        usuarioRepositorio.deleteByCorreo(correo); // Eliminar el usuario por su correo
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }
}
