package com.example.test.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.models.InventarioModel;
import com.example.test.services.InventarioService;
import com.example.test.utils.ResponseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired
    private InventarioService invService;
    private final static Logger LOGGER = LoggerFactory.getLogger(InventarioController.class);

    @GetMapping()
    public ResponseEntity<Object> getInventario() {
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
            ArrayList<InventarioModel> inventario = invService.getInventario();

            resData.put("Inventario", inventario);
            return ResponseHandler.setResponse("OK", resData, HttpStatus.OK);
        } catch (Exception e) {
            resData.put("Mensaje", e.getMessage());
            LOGGER.error("/inventario - " + e.getMessage());
            return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
        }
    }
}
