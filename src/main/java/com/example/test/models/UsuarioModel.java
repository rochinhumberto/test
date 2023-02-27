package com.example.test.models;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class UsuarioModel {
    
    @Id
    @Column(unique = true, nullable = false)
    private String email;

    private String contrasena;
    private String nombre;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
