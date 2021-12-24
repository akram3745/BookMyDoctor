package com.te.bookmydoctor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.bookmydoctor.dto.AddressDto;
import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.DoctorFetchAllDto;
import com.te.bookmydoctor.dto.LanguageDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.model.Qualification;
import com.te.bookmydoctor.repository.DoctorRepository;
import com.te.bookmydoctor.repository.PatientRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private PatientRepository patientRepository;

	/**
	 * For fetching details of all doctors
	 */
	@Override
	public List<DoctorFetchAllDto> getAllDoctor() {
		List<Doctor> doctors = doctorRepository.findAll();
		List<DoctorFetchAllDto> doctorFetchAllDtos = doctors.stream()
				.map(doctor -> new DoctorFetchAllDto(doctor.getFirstName() + " " + doctor.getLastName(),
						doctor.getAverageRating(), doctor.getIsDeleted(), doctor.getIsVerified(),
						doctor.getMobileNumber()))
				.collect(Collectors.toList());
		return doctorFetchAllDtos;
	}

	/**
	 * For fetching details of a particular doctor by mobile number
	 */
	@Override
	public DoctorDto getDoctor(Long mobileNumber) {
		Doctor doctor = doctorRepository.findByMobileNumber(mobileNumber);
		Qualification qualification = doctor.getQualification();
		List<AddressDto> addresses = new ArrayList<>();
		List<LanguageDto> languages = new ArrayList<>();
		doctor.getAddresses().stream().forEach(address -> addresses.add(new AddressDto(address.getStreet(),
				address.getArea(), address.getCity(), address.getZipCode(), address.getState(), address.getCountry())));
		doctor.getLanguage().stream().forEach(language -> languages.add(new LanguageDto(language.getLanguage())));
		DoctorDto doctorDto = new DoctorDto(doctor.getFirstName(), doctor.getLastName(), doctor.getEmail(),
				doctor.getMobileNumber(), "***********", doctor.getGender(), doctor.getAverageRating(), addresses,
				languages, qualification.getDegreeName(), qualification.getInstitutionName(),
				qualification.getPassingYear(), qualification.getHighestQualificationCertificate(),
				qualification.getMedicalRegistrationNumber(), doctor.getSpecialization().getSpeciality());
		return doctorDto;
	}

	/**
	 * For removing a particular doctor by mobile number
	 */
	@Override
	public Integer removeDoctor(Long mobileNumber) {
		Doctor doctor = doctorRepository.findByMobileNumber(mobileNumber);
		doctor.setIsDeleted(1);
		doctorRepository.save(doctor);
		return doctor.getIsDeleted();
	}

	/**
	 * For removing a particular patient by mobile number
	 */

	@Override
	public Integer removePatient(Long mobileNumber) {
		Patient patient = patientRepository.findByMobileNumber(mobileNumber);
		patient.setIsDeleted(1);
		patientRepository.save(patient);
		return patient.getIsDeleted();
	}

	/**
	 * Doctor verification by mobile number
	 */
	@Override
	public Integer doctorVerification(Long mobileNumber) {
		Doctor doctor = doctorRepository.findByMobileNumber(mobileNumber);
		doctor.setIsVerified(1);
		doctorRepository.save(doctor);
		return doctor.getIsVerified();
	}

	/**
	 * Patient verification by mobile number
	 */
	@Override
	public Integer patientVerification(Long mobileNumber) {
		Patient patient = patientRepository.findByMobileNumber(mobileNumber);
		patient.setIsVerified(1);
		patientRepository.save(patient);
		return patient.getIsVerified();
	}

	/**
	 * Fetching details of a particular patient by mobile number
	 */
	@Override
	public PatientDto getPatient(Long mobileNumber) {
		Patient patient = patientRepository.findByMobileNumber(mobileNumber);
		PatientDto patientDto = new PatientDto(patient.getFirstName(), patient.getLastName(), patient.getEmail(),
				"**********", patient.getGender(), mobileNumber);
		return patientDto;
	}

	/**
	 * Fetching details of all patients
	 */
	@Override
	public List<PatientDto> getAllPatient() {
		List<Patient> patients = patientRepository.findAll();
		List<PatientDto> patientDtos = patients
				.stream().map(patient -> new PatientDto(patient.getFirstName(), patient.getLastName(),
						patient.getEmail(), "**********", patient.getGender(), patient.getMobileNumber()))
				.collect(Collectors.toList());
		return patientDtos;
	}
}
