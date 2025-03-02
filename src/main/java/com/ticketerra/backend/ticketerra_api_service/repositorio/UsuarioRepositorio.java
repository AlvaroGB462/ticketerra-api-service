package com.ticketerra.backend.ticketerra_api_service.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // Método para buscar un usuario por su correo electrónico
    Optional<Usuario> findByCorreo(String correo);

    // Método para buscar un usuario por su token de recuperación
    Optional<Usuario> findByTokenRecuperacion(String token);

    // Método para buscar un usuario por su token de confirmación
    Optional<Usuario> findByTokenConfirmacion(String tokenConfirmacion);

    // Método para eliminar un usuario por su correo electrónico
    @Transactional // Asegura que la operación se ejecute dentro de una transacción
    void deleteByCorreo(String correo);
}