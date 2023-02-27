package com.example.test.models;

import javax.persistence.*;

@Entity
@Table(name = "inventario")
public class InventarioModel {
    
    @Id
    @Column(unique = true, nullable = false, length = 6)
    private String sku;

    private String nombre;
    private Integer cantidad;

    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
