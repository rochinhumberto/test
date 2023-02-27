package com.example.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "db/tests.sql")
@EnabledIfEnvironmentVariable(named = "DB_URL", matches = ".*test*.")
public class TestApplicationControllerTests extends TestApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@BeforeAll
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
    void createPoliza() throws Exception {
        Map<String, Object> postJSON = new HashMap<String, Object>();
        postJSON.put("id_empleado", 1);
        postJSON.put("cantidad", 3);
        postJSON.put("sku", "111111");
        
        mockMvc.perform(post("/api/v1/poliza")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postJSON)))
            .andExpect(status().isCreated())
            .andExpect(content().json("{\"Meta\":{\"Status\":\"OK\"},\"Data\":{\"idPoliza\":3,\"empleado\":{\"idEmpleado\":1,\"nombre\":\"Michael\",\"apellido\":\"Smith\",\"puesto\":\"Tester\"},\"articulo\":{\"sku\":\"111111\",\"nombre\":\"Banco\",\"cantidad\":97},\"cantidad\":3}}"))
            .andDo(print());
    }

    @Test
    void listPolizas() throws Exception {

        mockMvc.perform(get("/api/v1/poliza"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"Meta\":{\"Status\":\"OK\"},\"Data\":{\"Polizas\":{\"content\":[{\"idPoliza\":1,\"empleado\":{\"idEmpleado\":2,\"nombre\":\"Juan\",\"apellido\":\"Lopez\",\"puesto\":\"Manager\"},\"articulo\":{\"sku\":\"444444\",\"nombre\":\"Puerta\",\"cantidad\":98},\"cantidad\":2},{\"idPoliza\":2,\"empleado\":{\"idEmpleado\":3,\"nombre\":\"Jose\",\"apellido\":\"Garza\",\"puesto\":\"Lead\"},\"articulo\":{\"sku\":\"555555\",\"nombre\":\"Abanico\",\"cantidad\":98},\"cantidad\":2}],\"pageable\":{\"sort\":{\"empty\":false,\"unsorted\":false,\"sorted\":true},\"offset\":0,\"pageSize\":5,\"pageNumber\":0,\"paged\":true,\"unpaged\":false},\"last\":true,\"totalElements\":2,\"totalPages\":1,\"size\":5,\"number\":0,\"sort\":{\"empty\":false,\"unsorted\":false,\"sorted\":true},\"first\":true,\"numberOfElements\":2,\"empty\":false}}}"))
            .andDo(print());
    }

    @Test
    void getPolizaById() throws Exception {

        mockMvc.perform(get("/api/v1/poliza/1"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"Meta\":{\"Status\":\"OK\"},\"Data\":{\"Poliza\":{\"idPoliza\":1,\"empleado\":{\"idEmpleado\":2,\"nombre\":\"Juan\",\"apellido\":\"Lopez\",\"puesto\":\"Manager\"},\"articulo\":{\"sku\":\"444444\",\"nombre\":\"Puerta\",\"cantidad\":98},\"cantidad\":2}}}"))
            .andDo(print());
    }

    @Test
    void editPoliza() throws Exception {
        Map<String, Object> postJSON = new HashMap<String, Object>();
        postJSON.put("id_empleado", 1);
        postJSON.put("cantidad", 5);

        mockMvc.perform(put("/api/v1/poliza/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postJSON)))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"Meta\":{\"Status\":\"OK\"},\"Data\":{\"Poliza\":{\"idPoliza\":1,\"empleado\":{\"idEmpleado\":1,\"nombre\":\"Michael\",\"apellido\":\"Smith\",\"puesto\":\"Tester\"},\"articulo\":{\"sku\":\"444444\",\"nombre\":\"Puerta\",\"cantidad\":95},\"cantidad\":5},\"Mensaje\":\"Se actualizó correctamente la póliza: 1\"}}"))
            .andDo(print());
    }

    @Test
    void deletePoliza() throws Exception {

        mockMvc.perform(delete("/api/v1/poliza/2"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"Meta\":{\"Status\":\"OK\"},\"Data\":{\"Message\":\"Se eliminó correctamente la póliza: 2\"}}"))
            .andDo(print());
    }

}