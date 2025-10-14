package com.Diabetes.Backend.SpringBoot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gender;
    private int age;
    private int hypertension;
    private int heartDisease;
    private String smokingHistory;
    private double bmi;
    private double hba1cLevel;
    private int bloodGlucoseLevel;
    private String result;
}