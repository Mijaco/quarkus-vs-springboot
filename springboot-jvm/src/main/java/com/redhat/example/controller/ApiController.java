package com.redhat.example.controller;

import com.redhat.example.entity.Calculator;
import com.redhat.example.service.CalculateService;
import com.redhat.example.service.DatabaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private CalculateService calculateService;

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/calculate")
    public ResponseEntity<?> calculate(
            @RequestParam("value1") Long value1,
            @RequestParam("method") String method,
            @RequestParam("value2") Long value2) {
        LOG.debug("Calculating {} {} {}", value1, method, value2);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("result", calculateService.calculate(value1, method, value2));
        }});
    }

    @GetMapping("/results")
    public List<Calculator> results() {
        LOG.debug("Getting result");
        return databaseService.getResult();
    }

    @GetMapping("/prime")
    public Map<String, Object> getNthPrime(@RequestParam int n) {
        long start = System.currentTimeMillis();
        long prime = calculateService.findNthPrime(n);
        long duration = System.currentTimeMillis() - start;

        Map<String, Object> response = new HashMap<>();
        response.put("nthPrime", prime);
        response.put("position", n);
        response.put("durationInMillis", duration);

        return response;
    }
}
