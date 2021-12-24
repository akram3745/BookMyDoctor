package com.te.bookmydoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.bookmydoctor.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{
	Patient findByMobileNumber(Long mobileNumber);
	Patient  findByEmail(String email);
	Patient findByPatientId(Integer patientId);
}
