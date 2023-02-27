package com.example.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.models.UsuarioModel;
import com.example.test.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usrRepo;

    public UsuarioModel getUserByEmailAndContrasena(String email, String contrasena){
        return usrRepo.findByEmailAndContrasena(email, contrasena);
    }
}
