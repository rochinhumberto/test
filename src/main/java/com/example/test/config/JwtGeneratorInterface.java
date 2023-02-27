package com.example.test.config;

import java.util.Map;

import com.example.test.models.UsuarioModel;

public interface JwtGeneratorInterface {

    Map<String, String> generateToken(UsuarioModel usr);
}
