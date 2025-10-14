package com.Diabetes.Backend.SpringBoot.Repository;

import com.Diabetes.Backend.SpringBoot.Entity.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientRecord, Long> {
}