package com.Diabetes.Backend.SpringBoot.Service;

import com.Diabetes.Backend.SpringBoot.Entity.PatientRecord;
import com.Diabetes.Backend.SpringBoot.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class PredictionService {

    private final PatientRepository repo;
    private final RestTemplate restTemplate;

    @Value("${model.api.url}")
    private String modelApiUrl;

    public PredictionService(PatientRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public PatientRecord predictAndSave(Map<String, Object> data) {
        // Convert 0/1 to "yes"/"no" for Flask model
        String hypertensionStr = "no";
        String heartDiseaseStr = "no";

        if ("1".equals(data.get("hypertension").toString())) {
            hypertensionStr = "yes";
        }
        if ("1".equals(data.get("heart_disease").toString())) {
            heartDiseaseStr = "yes";
        }

        // Prepare payload for Flask API
        Map<String, Object> payload = Map.of(
                "gender", data.get("gender"),
                "age", data.get("age"),
                "hypertension", hypertensionStr,
                "heart_disease", heartDiseaseStr,
                "smoking_history", data.get("smoking_history"),
                "bmi", data.get("bmi"),
                "HbA1c_level", data.get("HbA1c_level"),
                "blood_glucose_level", data.get("blood_glucose_level")
        );

        // Call Flask model API
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> req = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(modelApiUrl, req, Map.class);
        Map<?, ?> resBody = response.getBody();

        String result = resBody != null ? (String) resBody.get("result") : "Unknown";

        // Convert 0/1 for database storage
        int hypertensionDb = "1".equals(data.get("hypertension").toString()) ? 1 : 0;
        int heartDiseaseDb = "1".equals(data.get("heart_disease").toString()) ? 1 : 0;

        // Save record in database
        PatientRecord record = new PatientRecord();
        record.setGender((String) data.get("gender"));
        record.setAge(Integer.parseInt(data.get("age").toString()));
        record.setHypertension(hypertensionDb);
        record.setHeartDisease(heartDiseaseDb);
        record.setSmokingHistory((String) data.get("smoking_history"));
        record.setBmi(Double.parseDouble(data.get("bmi").toString()));
        record.setHba1cLevel(Double.parseDouble(data.get("HbA1c_level").toString()));
        record.setBloodGlucoseLevel(Integer.parseInt(data.get("blood_glucose_level").toString()));
        record.setResult(result);

        repo.save(record);
        return record;
    }

    public List<PatientRecord> getAllRecords() {
        return repo.findAll();
    }
}
