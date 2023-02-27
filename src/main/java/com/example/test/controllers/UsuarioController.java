package com.example.test.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.config.JwtGeneratorInterface;
import com.example.test.models.UsuarioModel;
import com.example.test.services.UsuarioService;
import com.example.test.utils.ResponseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService userService;
    @Autowired
    private JwtGeneratorInterface jwtGenerator;
    private final static Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody UsuarioModel usr) {
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
            if(usr.getEmail() == null || usr.getContrasena() == null) {
                resData.put("Mensaje", "Usuario o Contraseña invalido");
                LOGGER.error("/usuario/login - Usuario o Contraseña invalido" + "USR: " + usr.getEmail() + "PWD: " + usr.getContrasena() );
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.UNAUTHORIZED);
            }

            UsuarioModel usrData = userService.getUserByEmailAndContrasena(usr.getEmail(), usr.getContrasena());
            if(usrData == null){
                resData.put("Mensaje", "Usuario o Contraseña invalido");
                LOGGER.error("/usuario/login - Usuario o Contraseña invalido" + "USR: " + usr.getEmail() + "PWD: " + usr.getContrasena() );
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.UNAUTHORIZED);
            }

            return ResponseHandler.setResponse("OK", jwtGenerator.generateToken(usr), HttpStatus.OK);
        } catch (Exception e) {
            resData.put("Mensaje", e.getMessage());
            LOGGER.error("/usuario/login - " + e.getMessage());
            return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
        }
    }

}
