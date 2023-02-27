package com.example.test.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.test.models.EmpleadoModel;

@Repository
public interface EmpleadoRepository extends CrudRepository<EmpleadoModel, Integer> {
    public abstract EmpleadoModel findByIdEmpleado(Integer idEmpleado);
}
