package com.example.test.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> setResponse(String metaStatus, Object dataObject, HttpStatus status) {
        Map<String, Object> meta = new HashMap<String, Object>();
        meta.put("Status", metaStatus);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("Meta", meta);
        res.put("Data", dataObject);

        return new ResponseEntity<Object>(res, status);
    }
}
