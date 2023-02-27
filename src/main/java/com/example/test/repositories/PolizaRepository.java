package com.example.test.repositories;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.test.models.PolizaModel;

@Repository
public interface PolizaRepository extends JpaRepository<PolizaModel, Long> {
    public abstract PolizaModel findByIdPoliza(Long idPoliza);
    public abstract ArrayList<PolizaModel> findByEmpleadoNombreContainingIgnoreCase(String query);
    public abstract Page<PolizaModel> findByEmpleadoNombreContainingIgnoreCase(Pageable paged, String query);
}
