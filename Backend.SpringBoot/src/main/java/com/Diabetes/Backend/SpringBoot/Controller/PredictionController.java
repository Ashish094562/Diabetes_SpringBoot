package com.Diabetes.Backend.SpringBoot.Controller;

import com.Diabetes.Backend.SpringBoot.Entity.PatientRecord;
import com.Diabetes.Backend.SpringBoot.Service.PredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PredictionController {

    private final PredictionService service;

    public PredictionController(PredictionService service) {
        this.service = service;
    }

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestBody Map<String, Object> data) {
        PatientRecord record = service.predictAndSave(data);
        return ResponseEntity.ok(Map.of(
                "message", "Prediction saved successfully",
                "result", record.getResult(),
                "recordId", record.getId()
        ));
    }

    @GetMapping("/records")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAllRecords());
    }
}

