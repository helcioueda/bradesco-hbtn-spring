package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository {
    
    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);
}