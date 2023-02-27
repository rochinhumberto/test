package com.example.test.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.models.EmpleadoModel;
import com.example.test.models.InventarioModel;
import com.example.test.models.PolizaModel;
import com.example.test.services.EmpleadoService;
import com.example.test.services.InventarioService;
import com.example.test.services.PolizaService;
import com.example.test.utils.PolizaCreateValidator;
import com.example.test.utils.PolizaUpdateValidator;
import com.example.test.utils.ResponseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/poliza")
public class PolizaController {
    @Autowired
    private PolizaService polService;
    @Autowired
    private EmpleadoService empService;
    @Autowired
    private InventarioService invService;
    private final static Logger LOGGER = LoggerFactory.getLogger(PolizaController.class);

    @PostMapping()
    public ResponseEntity<Object> savePoliza(@Valid @RequestBody PolizaCreateValidator request){
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
            Integer cantidad = request.getCantidad();
            EmpleadoModel empleado = empService.getEmpleadoByIdEmpleado(request.getId_empleado());
            if(empleado == null){
                resData.put("Mensaje", "Id empleado no existe.");
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
            }
            InventarioModel sku = invService.getInventarioBySku(request.getSku());
            if(sku == null){
                resData.put("Mensaje", "SKU no existe.");
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
            }
            if(sku.getCantidad() < cantidad){
                resData.put("Mensaje", "Cantidad mayor a disponible en inventario.");
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
            }

            sku.setCantidad(sku.getCantidad() - cantidad);
            invService.saveInventario(sku);

            PolizaModel newPoliza = new PolizaModel();
            newPoliza.setCantidad(cantidad);
            newPoliza.setEmpleado(empleado);
            newPoliza.setArticulo(sku);

            polService.savePoliza(newPoliza);
            return ResponseHandler.setResponse("OK", newPoliza, HttpStatus.CREATED);
        } catch (Exception e) {
            resData.put("Mensaje", "Ha ocurrido un error en los grabados de póliza.");
            resData.put("Error", e.getMessage());
            LOGGER.error("/poliza - " + e.getMessage());
            return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getPolizas(
        @RequestParam(defaultValue = "true") Boolean paginated,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") Integer page_size
    ) {
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
            if(paginated){
                Pageable paged = PageRequest.of(page, page_size, Sort.by("idPoliza"));
                Page<PolizaModel> pols = polService.getPolizasPaged(paged);

                resData.put("Polizas", pols);
                return ResponseHandler.setResponse("OK", resData, HttpStatus.OK);

            } else {
                ArrayList<PolizaModel> pols = polService.getPolizas();
                resData.put("Polizas", pols);
                return ResponseHandler.setResponse("OK", resData, HttpStatus.OK);
            }
        } catch (Exception e) {
            resData.put("Mensaje", e.getMessage());
            LOGGER.error("/poliza - " + e.getMessage());
            return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Object> searchPolizas(
        @RequestParam(defaultValue = "query") String query,
        @RequestParam(defaultValue = "true") Boolean paginated,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") Integer page_size
    ) {
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
            if(paginated){
                Pageable paged = PageRequest.of(page, page_size, Sort.by("idPoliza"));
                Page<PolizaModel> pols = polService.searchPolizasPaged(paged, query);

                resData.put("Polizas", pols);
                return ResponseHandler.setResponse("OK", resData, HttpStatus.OK);

            } else {
                ArrayList<PolizaModel> pols = polService.searchPolizas(query);
                resData.put("Polizas", pols);
                return ResponseHandler.setResponse("OK", resData, HttpStatus.OK);
            }
        } catch (Exception e) {
            resData.put("Mensaje", e.getMessage());
            LOGGER.error("/poliza - " + e.getMessage());
            return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPolizaById(@PathVariable("id") Long id) {
        PolizaModel foundPoliza = polService.getPolizaByIdPoliza(id);
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
            if (foundPoliza == null) {
                resData.put("Message", "Póliza no encontrada");
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.NOT_FOUND);
            }

            resData.put("Poliza", foundPoliza);
            return ResponseHandler.setResponse("OK", resData, HttpStatus.OK);
        
        } catch (Exception e) {
            resData.put("Mensaje", "Ha ocurrido un error al consultar la póliza: " + id);
            resData.put("Error", e.getMessage());
            LOGGER.error("/poliza/{id} - " + e.getMessage());
            return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePoliza(@PathVariable("id") Long id, @Valid @RequestBody PolizaUpdateValidator request) {
        PolizaModel foundPoliza = polService.getPolizaByIdPoliza(id);
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
            if (foundPoliza == null) {
                resData.put("Message", "Póliza no encontrada");
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.NOT_FOUND);
            }
    
            if(request.getId_empleado() != null){
                EmpleadoModel empleado = empService.getEmpleadoByIdEmpleado(request.getId_empleado());
                if(empleado == null){
                    resData.put("Mensaje", "Id empleado no existe.");
                    return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
                }
                foundPoliza.setEmpleado(empleado);
            }
    
            if(request.getCantidad() != null){
                Integer diff = foundPoliza.getCantidad() - request.getCantidad();
    
                if( (foundPoliza.getArticulo().getCantidad() + foundPoliza.getCantidad()) < request.getCantidad()){
                    resData.put("Mensaje", "Cantidad mayor a disponible en inventario.");
                    return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
                }
                foundPoliza.setCantidad(request.getCantidad());

                InventarioModel sku = invService.getInventarioBySku(foundPoliza.getArticulo().getSku());
                sku.setCantidad(sku.getCantidad() + diff);
                invService.saveInventario(sku);
            }

            polService.savePoliza(foundPoliza);
            resData.put("Mensaje", "Se actualizó correctamente la póliza: " + id);
            resData.put("Poliza", foundPoliza);
            return ResponseHandler.setResponse("OK", resData, HttpStatus.OK);
        
        } catch (Exception e) {
            resData.put("Mensaje", "Ha ocurrido un error al intentar actualizar la póliza: " + id);
            resData.put("Error", e.getMessage());
            LOGGER.error("/poliza - " + e.getMessage());
            return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> updatePoliza(@PathVariable("id") Long id) {
        PolizaModel foundPoliza = polService.getPolizaByIdPoliza(id);
        Map<String, Object> resData = new HashMap<String, Object>();
        try {
            if (foundPoliza == null) {
                resData.put("Message", "Póliza no encontrada");
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.NOT_FOUND);
            }

            String editedSku = foundPoliza.getArticulo().getSku();
            boolean deleted = polService.deletePoliza(id);
            if(deleted){
                InventarioModel sku = invService.getInventarioBySku(editedSku);
                sku.setCantidad(sku.getCantidad() + foundPoliza.getCantidad());
                invService.saveInventario(sku);
                resData.put("Message", "Se eliminó correctamente la póliza: " + id);
                return ResponseHandler.setResponse("OK", resData, HttpStatus.OK);

            }else{
                resData.put("Message", "Ha ocurrido un error al intentar eliminar la póliza: " + id);
                return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.OK);
            }

        } catch (Exception e) {
            resData.put("Mensaje", "Ha ocurrido un error al intentar eliminar la póliza: " + id);
            resData.put("Error", e.getMessage());
            LOGGER.error("/poliza - " + e.getMessage());
            return ResponseHandler.setResponse("FAILURE", resData, HttpStatus.CONFLICT);
        }
        
    }
}
