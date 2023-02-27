package com.example.test.models;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "poliza")
public class PolizaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idPoliza;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado")
    private EmpleadoModel empleado;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sku")
    private InventarioModel articulo;

    private Integer cantidad;
    
    @Column(name = "fecha", nullable = false, updatable = false)
    @CreationTimestamp
    private Date fecha;

    public Long getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(Long idPoliza) {
        this.idPoliza = idPoliza;
    }

    public EmpleadoModel getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoModel empleado) {
        this.empleado = empleado;
    }

    public InventarioModel getArticulo() {
        return articulo;
    }

    public void setArticulo(InventarioModel articulo) {
        this.articulo = articulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
