package com.example.test.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.test.models.PolizaModel;
import com.example.test.repositories.PolizaRepository;

@Service
public class PolizaService {
    @Autowired
    PolizaRepository polRepo;

    public ArrayList<PolizaModel> getPolizas(){
        return (ArrayList<PolizaModel>) polRepo.findAll();
    }

    public Page<PolizaModel> getPolizasPaged(Pageable paged){
        return (Page<PolizaModel>) polRepo.findAll(paged);
    }

    public ArrayList<PolizaModel> searchPolizas(String query){
        return (ArrayList<PolizaModel>) polRepo.findByEmpleadoNombreContainingIgnoreCase(query);
    }

    public Page<PolizaModel> searchPolizasPaged(Pageable paged, String query){
        return (Page<PolizaModel>) polRepo.findByEmpleadoNombreContainingIgnoreCase(paged, query);
    }

    public PolizaModel savePoliza(PolizaModel poliza){
        return polRepo.save(poliza);
    }

    public PolizaModel getPolizaByIdPoliza(Long idPoliza){
        return polRepo.findByIdPoliza(idPoliza);
    }

    public boolean deletePoliza(Long idPoliza){
        try {
            polRepo.deleteById(idPoliza);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
