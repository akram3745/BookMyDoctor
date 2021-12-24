package com.te.bookmydoctor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.DoctorFetchAllDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.model.Address;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.Language;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.model.Qualification;
import com.te.bookmydoctor.model.Specialization;
import com.te.bookmydoctor.repository.DoctorRepository;
import com.te.bookmydoctor.repository.PatientRepository;

@SpringBootTest
class AdminServiceImplTest {
	@InjectMocks
	private AdminServiceImpl adminServiceImpl;
	@Mock
	private DoctorRepository doctorRepository;
	@Mock
	private PatientRepository patientRepository;

	@Test
	void testGetAllDoctor() {
		List<Doctor> doctors = new ArrayList<>();
		Integer isVerified = 1;
		doctors.add(new Doctor("Sahid", "alom", "sahid@123@gmail,com", 425784245l, "12345", "MALE", 4.5, 0, 1));
		doctors.add(new Doctor("Sahin", "alom", "sahin@123@gmail,com", 842442575l, "12345", "MALE", 4.5, 0, 1));
		Mockito.when(doctorRepository.findAll()).thenReturn(doctors);
		List<DoctorFetchAllDto> allDoctor = adminServiceImpl.getAllDoctor();
		System.out.println(allDoctor);
		for (DoctorFetchAllDto doctorFetchAllDto : allDoctor) {
			assertEquals(doctorFetchAllDto.getIsVerified(), isVerified);
		}
	}

	@Test
	void testGetDoctor() {
		List<Doctor> doctors = new ArrayList<>();
		Doctor doctor = new Doctor("Sahid", "alom", "sahid@123@gmail,com", 425784245l, "12345", "MALE", 4.5, 0, 1);
		Doctor doctor2 = new Doctor("Sahin", "alom", "sahin@123@gmail,com", 842442575l, "12345", "MALE", 4.5, 0, 1);
		List<Address> addresses = new ArrayList<>();
		Address address = new Address("Dhubri", "Dhubri", "Dhubri", 783334, "Assam", "india", doctor);
		addresses.add(address);
		doctor.setAddresses(addresses);
		List<Language> languages = new ArrayList<Language>();
		Language language = new Language("Hindi", doctor);
		languages.add(language);
		Qualification qualification = new Qualification("MBBS", "XYZ", 2014, "ABC.png", "XFZ1452", doctor);
		doctor.setQualification(qualification);
		Specialization specialization = new Specialization("ENT", doctor);
		doctor.setLanguage(languages);
		;
		doctor.setSpecialization(specialization);
		doctors.add(doctor);
		doctors.add(doctor2);
		Mockito.when(doctorRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(doctor);
		DoctorDto doctor3 = adminServiceImpl.getDoctor(doctor.getMobileNumber());
		assertEquals(doctor3.getEmail(), doctor.getEmail());

	}

	@Test
	void testRemoveDoctor() {
		Doctor doctor = new Doctor("Sahid", "alom", "sahid@123@gmail,com", 425784245l, "12345", "MALE", 4.5, 0, 1);
		Integer expected = 1;
		Mockito.when(doctorRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(doctor);
		Integer removeDoctor = adminServiceImpl.removeDoctor(doctor.getMobileNumber());
		assertEquals(expected, removeDoctor);
	}

	@Test
	void testRemovePatient() {
		Patient patient = new Patient("sahid", "alom", "sahidalom1234@gmail.com", "12345", "MALE", 8399842584l, 0, 1);
		Integer expected = 1;
		Mockito.when(patientRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(patient);
		Integer removePatient = adminServiceImpl.removePatient(patient.getMobileNumber());
		assertEquals(expected, removePatient);
	}

	@Test
	void testDoctorVerification() {
		Doctor doctor = new Doctor("Sahid", "alom", "sahid@123@gmail,com", 425784245l, "12345", "MALE", 4.5, 0, 1);
		Integer expected = 1;
		Mockito.when(doctorRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(doctor);
		Integer veriInteger = adminServiceImpl.doctorVerification(doctor.getMobileNumber());
		assertEquals(expected, veriInteger);
	}

	@Test
	void testPatientVerification() {
		Patient patient = new Patient("sahid", "alom", "sahidalom1234@gmail.com", "12345", "MALE", 8399842584l, 0, 1);
		Integer expected = 1;
		Mockito.when(patientRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(patient);
		Integer verifInteger = adminServiceImpl.patientVerification(patient.getMobileNumber());
		assertEquals(expected, verifInteger);
	}

	@Test
	void testGetPatient() {
		PatientDto patientDto = new PatientDto("Sahid", "Alom", "sahidalom1234@gmail.com", "12345", "MALE",
				8399842584l);
		Patient patient = new Patient("sahid", "alom", "sahidalom1234@gmail.com", "12345", "MALE", 8399842584l, 0, 1);
		Mockito.when(patientRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(patient);
		PatientDto patient2 = adminServiceImpl.getPatient(patient.getMobileNumber());
		assertEquals(patientDto.getEmail(), patient2.getEmail());
	}

	@Test
	void testGetAllPatient() {
		List<PatientDto> patientDtos = new ArrayList<>();
		List<Patient> patients = new ArrayList<>();
		PatientDto patientDto1 = new PatientDto("Sahid", "Alom", "sahidalom1234@gmail.com", "12345", "MALE",
				123457898l);
		PatientDto patientDto2 = new PatientDto("Sahi2", "Alom", "sahinalom1234@gmail.com", "12345", "MALE",
				789812345l);
		patientDtos.add(patientDto1);
		patientDtos.add(patientDto2);
		Patient patient1 = new Patient("sahid", "alom", "sahidalom1234@gmail.com", "12345", "MALE", 8399842584l, 0, 1);
		Patient patient2 = new Patient("sahin", "alom", "sahinalom1234@gmail.com", "12345", "MALE", 6900313356l, 0, 1);
		patients.add(patient1);
		patients.add(patient2);
		Mockito.when(patientRepository.findAll()).thenReturn(patients);
		List<PatientDto> allPatient = adminServiceImpl.getAllPatient();
		assertEquals(patientDtos.get(0).getEmail(), allPatient.get(0).getEmail());
		assertEquals(patientDtos.get(1).getEmail(), allPatient.get(1).getEmail());
	}

}
