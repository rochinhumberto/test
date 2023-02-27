package com.example.test.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.models.InventarioModel;
import com.example.test.repositories.InventarioRepository;

@Service
public class InventarioService {
    @Autowired
    InventarioRepository invRepo;

    public ArrayList<InventarioModel> getInventario(){
        return (ArrayList<InventarioModel>) invRepo.findAll();
    }

    public InventarioModel saveInventario(InventarioModel inventario){
        return invRepo.save(inventario);
    }

    public InventarioModel getInventarioBySku(String sku){
        return invRepo.findBySku(sku);
    }
}
