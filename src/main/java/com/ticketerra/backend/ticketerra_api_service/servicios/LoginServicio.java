package com.ticketerra.backend.ticketerra_api_service.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@Service
public class LoginServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }
}
