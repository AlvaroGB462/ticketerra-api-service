package com.ticketerra.backend.ticketerra_api_service.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByTokenRecuperacion(String token);
    Optional<Usuario> findByTokenConfirmacion(String tokenConfirmacion);
    
    @Transactional
    void deleteByCorreo(String correo);

}