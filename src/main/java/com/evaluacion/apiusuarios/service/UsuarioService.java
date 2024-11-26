package com.evaluacion.apiusuarios.service;

import com.evaluacion.apiusuarios.model.Usuario;
import com.evaluacion.apiusuarios.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Genera una clave segura y suficiente para HS512 al iniciar la aplicación
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Crea un nuevo usuario, valida si ya existe por email.
     * @param usuario Datos del usuario.
     * @return Usuario creado con datos adicionales.
     */
    public Usuario crearUsuario(Usuario usuario) {
        // Valida si el correo ya está registrado
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        // Completa los datos del usuario antes de guardarlo
        usuario.setId(UUID.randomUUID());
        usuario.setCreated(LocalDateTime.now());
        usuario.setModified(LocalDateTime.now());
        usuario.setLastLogin(LocalDateTime.now());
        usuario.setToken(generarToken(usuario.getEmail()));
        usuario.setIsActive(true);

        return usuarioRepository.save(usuario);
    }

    /**
     * Genera un token JWT para un usuario.
     * @param email Email del usuario (se usa como subject en el token).
     * @return Token JWT generado.
     */
    private String generarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expira en 1 día
                .signWith(secretKey) // Firma con la clave segura generada
                .compact();
    }
}

