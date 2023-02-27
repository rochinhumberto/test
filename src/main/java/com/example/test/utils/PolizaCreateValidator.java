package com.example.test.utils;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class PolizaCreateValidator {
    @NotNull(message = "El campo cantidad es requerido.")
    @Range(min = 1, message = "El campo cantidad debe ser mayor a 0.")
    private Integer cantidad;

    @NotNull(message = "El campo id_empleado es requerido.")
    @Range(min = 1, message = "El campo id_empleado debe ser mayor a 0.")
    private Integer id_empleado;
    
    @NotNull(message = "El campo sku es requerido.")
    @Size(min = 6, max = 6, message = "El campo sku es inválido.")
    private String sku;
}
