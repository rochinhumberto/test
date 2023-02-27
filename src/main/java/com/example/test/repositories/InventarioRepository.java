package com.example.test.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.test.models.InventarioModel;

@Repository
public interface InventarioRepository extends CrudRepository<InventarioModel, String> {
    public abstract InventarioModel findBySku(String sku);
}
