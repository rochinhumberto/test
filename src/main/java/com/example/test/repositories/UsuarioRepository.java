package com.example.test.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.test.models.UsuarioModel;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, String> {
    public abstract UsuarioModel findByEmailAndContrasena(String email, String contrasena);
}
