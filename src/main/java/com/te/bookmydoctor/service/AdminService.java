package com.te.bookmydoctor.service;

import java.util.List;

import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.DoctorFetchAllDto;
import com.te.bookmydoctor.dto.PatientDto;

public interface AdminService {
	List<DoctorFetchAllDto> getAllDoctor();

	PatientDto getPatient(Long mobileNumber);

	List<PatientDto> getAllPatient();

	DoctorDto getDoctor(Long mobileNumber);

	Integer removeDoctor(Long mobileNumber);

	Integer removePatient(Long mobileNumber);

	Integer doctorVerification(Long mobileNumber);

	Integer patientVerification(Long mobileNumber);

}
