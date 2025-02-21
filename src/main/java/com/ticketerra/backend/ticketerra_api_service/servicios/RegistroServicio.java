package com.ticketerra.backend.ticketerra_api_service.servicios;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ticketerra.backend.ticketerra_api_service.modelos.Usuario;
import com.ticketerra.backend.ticketerra_api_service.repositorio.UsuarioRepositorio;

@Service
public class RegistroServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // Guardar usuario en la base de datos después de la confirmación
    public ResponseEntity<?> registrarUsuario(Usuario usuario) {
        // Solo se guarda el token, no el usuario aún
        usuario.setTokenConfirmacion(UUID.randomUUID().toString());
        
        // Convertir el valor 'fechaRegistro' (milisegundos) a Timestamp
        Timestamp fechaRegistro = new Timestamp(System.currentTimeMillis());  // Convertimos el 'bigint' a Timestamp
        usuario.setFechaRegistro(fechaRegistro);  // Añadimos la fecha de registro como Timestamp

        // Verificamos si los campos teléfono y código postal están presentes y los asignamos
        if (usuario.getTelefono() == null || usuario.getTelefono().isEmpty()) {
            usuario.setTelefono("Número no proporcionado");
        }
        if (usuario.getCodigoPostal() == null || usuario.getCodigoPostal().isEmpty()) {
            usuario.setCodigoPostal("Código no proporcionado");
        }

        // Guardamos el usuario con el token, pero no lo activamos
        System.out.println(usuario);
        usuarioRepositorio.save(usuario);

        return ResponseEntity.ok("Usuario registrado, revisa tu correo para activar la cuenta.");
    }

    // Buscar usuario por token
    public ResponseEntity<Usuario> buscarPorToken(String token) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenConfirmacion(token);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Activar usuario en la base de datos (después de la confirmación)
    public ResponseEntity<?> activarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByTokenConfirmacion(usuario.getTokenConfirmacion());

        if (usuarioOptional.isPresent()) {
            Usuario usuarioBD = usuarioOptional.get();

            // Verificamos si el token ha expirado (1 hora de expiración)
            long tiempoExpiracion = 60 * 60 * 1000;  // 1 hora en milisegundos

            // Aquí usamos la fecha de creación del token directamente (esto debe ser manejado en frontend)
            long tiempoTokenGenerado = Long.parseLong(usuario.getTokenConfirmacion().split("-")[0]);  // Usamos una parte del token como timestamp

            // Comparamos el tiempo transcurrido con el tiempo de expiración
            if ((System.currentTimeMillis() - tiempoTokenGenerado) > tiempoExpiracion) {
                return ResponseEntity.badRequest().body("El enlace ha expirado.");
            }

            // Activamos al usuario
            usuarioBD.setActivo(true);
            usuarioBD.setTokenConfirmacion(null);  // Eliminamos el token después de la confirmación
            usuarioRepositorio.save(usuarioBD);  // Guardamos el usuario en la base de datos

            return ResponseEntity.ok("Usuario activado correctamente.");
        }

        return ResponseEntity.badRequest().body("Token inválido.");
    }
}
