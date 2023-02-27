package com.example.test.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.models.EmpleadoModel;
import com.example.test.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empRepo;

    public ArrayList<EmpleadoModel> getEmps(){
        return (ArrayList<EmpleadoModel>) empRepo.findAll();
    }

    public EmpleadoModel getEmpleadoByIdEmpleado(Integer idEmpleado){
        return empRepo.findByIdEmpleado(idEmpleado);
    }
}
