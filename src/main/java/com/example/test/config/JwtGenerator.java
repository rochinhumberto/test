package com.example.test.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.test.models.UsuarioModel;

@Service
public class JwtGenerator implements JwtGeneratorInterface{
    @Value("${jwt.secret}")
    private String secret;

    @Value("Login OK")
    private String message;

    @Override
    public Map<String, String> generateToken(UsuarioModel usr) {
        String jwtToken="";
        jwtToken = Jwts.builder().setSubject(usr.getEmail()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secret").compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("Token", jwtToken);
        jwtTokenGen.put("Mensaje", message);
        return jwtTokenGen;
    }
}
