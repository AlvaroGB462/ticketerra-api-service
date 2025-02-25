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

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        System.out.println("Usuarios desde la BD: " + usuarios);  // Verifica en la consola
        return usuarios;
    }

    // Método para eliminar un usuario por correo
    public void eliminarUsuario(String correo) {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo).orElse(null); // Buscar el usuario por correo
        if (usuario != null) {
            usuarioRepositorio.delete(usuario); // Eliminar el usuario
        } else {
            throw new RuntimeException("Usuario no encontrado"); // Si no se encuentra, lanzar excepción
        }
    }
}
