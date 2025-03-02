package com.ticketerra.backend.ticketerra_api_service.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@Service
public class AdminSupremoUsuariosServicios {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // Método para obtener la lista de todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll(); // Obtiene todos los usuarios de la base de datos
        return usuarios;
    }

    // Método para eliminar un usuario por su correo
    public void eliminarUsuario(String correo) {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo).orElse(null); // Busca el usuario por correo
        if (usuario != null) {
            usuarioRepositorio.delete(usuario); // Elimina el usuario si existe
        } else {
            throw new RuntimeException("Usuario no encontrado"); // Lanza una excepción si el usuario no existe
        }
    }
}