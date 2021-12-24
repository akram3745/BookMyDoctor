package com.te.bookmydoctor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.te.bookmydoctor.dto.AppointmentDto;
import com.te.bookmydoctor.dto.AppointmentTakenTimeDto;
import com.te.bookmydoctor.dto.DoctorFetch;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.dto.PatientAppointmentStatusDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.dto.SearchDoctorDto;
import com.te.bookmydoctor.model.Address;
import com.te.bookmydoctor.model.Appointment;
import com.te.bookmydoctor.model.AppointmentTakenTime;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.FeedBack;
import com.te.bookmydoctor.model.Language;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.model.Qualification;
import com.te.bookmydoctor.model.Role;
import com.te.bookmydoctor.model.Specialization;
import com.te.bookmydoctor.repository.AppoinmenTakenTimeReposiory;
import com.te.bookmydoctor.repository.AppoinmentRepository;
import com.te.bookmydoctor.repository.DoctorRepository;
import com.te.bookmydoctor.repository.FeedBackRepository;
import com.te.bookmydoctor.repository.PatientRepository;
import com.te.bookmydoctor.repository.RoleRepository;

@SpringBootTest
class PatientServiceImplTest {

	@Mock
	private PatientRepository patientRepository;
	@Mock
	private DoctorRepository doctorRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private AppoinmenTakenTimeReposiory appoinmenTakenTimeReposiory;
	@Mock
	private AppoinmentRepository appoinmentReposiory;
	@Mock
	private FeedBackRepository feedBackRepository;
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	@Mock
	private JavaMailService javaMailService;
	@InjectMocks
	private PatientServiceImpl patientServiceImpl;

	@Test
	void testSearchDoctor() {
		SearchDoctorDto searchDoctorDto = new SearchDoctorDto("India", "Assam", "Dhubri", "Dhubri", "ENT", "Hindi",
				"Male");
		DoctorFetch doctorFetch = new DoctorFetch(100, "Sahid Alom", 4.5);
		List<DoctorFetch> doctorFetchs = new ArrayList<>();
		doctorFetchs.add(doctorFetch);
		List<Doctor> doctors = new ArrayList<>();
		Doctor doctor = new Doctor("Sahid", "Alom", "sa@gmail.com", 123457843l, "12345", "MALE", 4.5, 0, 1);
		doctor.setDoctorId(100);
		Qualification qualification = new Qualification("ABC", "MNP", 2012, "CFX.png", "bcv1234", doctor);
		doctor.setQualification(qualification);
		List<Address> Addresses = new ArrayList<Address>();
		Address address = new Address("Dhubri", "Dhubri", "Dhubri", 7412584, "Assam", "India", doctor);
		Addresses.add(address);
		doctor.setAddresses(Addresses);
		List<Language> languages = new ArrayList<Language>();
		Language language = new Language("Hindi", doctor);
		languages.add(language);
		doctor.setLanguage(languages);
		Specialization specialization = new Specialization("ENT", doctor);
		doctor.setSpecialization(specialization);
		doctors.add(doctor);

		Mockito.when(doctorRepository.saveAll(doctors)).thenReturn(doctors);
		Mockito.when(doctorRepository.findAll()).thenReturn(doctors);
		Mockito.when(doctorRepository.findByDoctorId(Mockito.any())).thenReturn(doctor);
		List<DoctorFetch> searchDoctor = patientServiceImpl.searchDoctor(searchDoctorDto);
		assertEquals(doctor.getDoctorId(), searchDoctor.get(0).getDoctorId());
	}

	@Test
	void testSavePatient() {
		PatientDto patientDto = new PatientDto("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l);
		Patient patient = new Patient(patientDto.getFirstName(), patientDto.getLastName(), patientDto.getEmail(),
				passwordEncoder.encode(patientDto.getPassword()), patientDto.getGender(), patientDto.getMobileNumber(),
				0, 0);
		List<Patient> patients = new ArrayList<>();
		patients.add(patient);
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		role.setRole("PATIENT");
		roles.add(role);
		PatientDto savePatient = patientServiceImpl.savePatient(patientDto);
		System.out.println(savePatient);
		assertEquals(patientDto.getEmail(), savePatient.getEmail());
	}

	@SuppressWarnings("deprecation")
	@Test
	void testPatientAppointment() {
		AppointmentTakenTimeDto appointmentTakenTimeDto = new AppointmentTakenTimeDto("1.00", "2.30", "0");
		List<AppointmentTakenTime> appointmentTakenTimes = new ArrayList<>();
		List<AppointmentTakenTimeDto> appointmentTakenTimesDtos = new ArrayList<>();
		appointmentTakenTimesDtos.add(appointmentTakenTimeDto);
		AppointmentDto appointmentDto = new AppointmentDto(8399842584l, new Date(2021, 12, 10),
				appointmentTakenTimesDtos);
		Patient patient = new Patient("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l, 0, 0);
		patient.setPatientId(100);
		List<Patient> patients = new ArrayList<>();
		patients.add(patient);
		AppointmentTakenTime appointmentTakenTime = new AppointmentTakenTime("5.00", "5.30", "0", patients);
		appointmentTakenTimes.add(appointmentTakenTime);

		Doctor doctor = new Doctor("sahid", "alom", "sahidalom1234@gmail.com", 8399842584l, "12345", "MALE", 4.5, 0, 1);
		List<Appointment> appointments0 = new ArrayList<Appointment>();
		Appointment appointment = new Appointment(100, new Date(4521452), "SUNDAY", appointmentTakenTimes, doctor);
		appointments0.add(appointment);
		patientServiceImpl.setPatientId(100);
		Mockito.when(doctorRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(doctor);
		Mockito.when(patientRepository.findByPatientId(Mockito.anyInt())).thenReturn(patient);
		Mockito.when(appoinmentReposiory.findByAppointmentDate(Mockito.any())).thenReturn(appointments0);
		Mockito.when(appoinmenTakenTimeReposiory.save(Mockito.any())).thenReturn(appointmentTakenTime);
		Mockito.when(appoinmentReposiory.save(Mockito.any())).thenReturn(appointment);

		AppointmentDto patientAppointment = patientServiceImpl.patientAppointment(appointmentDto);

		assertEquals(appointmentDto.getDoctorMobileNumber(), patientAppointment.getDoctorMobileNumber());
	}

	@Test
	void testPatientAppointmentStatus() {
		AppointmentTakenTimeDto appointmentTakenTimeDto = new AppointmentTakenTimeDto("1.00", "2.30", "0");
		List<AppointmentTakenTime> appointmentTakenTimes = new ArrayList<>();
		List<AppointmentTakenTimeDto> appointmentTakenTimesDtos = new ArrayList<>();
		appointmentTakenTimesDtos.add(appointmentTakenTimeDto);
		Doctor doctor = new Doctor("sahid", "alom", "sahidalom1234@gmail.com", 8399842584l, "12345", "MALE", 4.5, 0, 1);
		Patient patient = new Patient("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l, 0, 0);
		patient.setPatientId(100);
		patient.setAppointmentTakenTime(appointmentTakenTimes);
		List<Patient> patients = new ArrayList<>();
		patients.add(patient);
		AppointmentTakenTime appointmentTakenTime = new AppointmentTakenTime("5.00", "5.30", "0", patients);
		Appointment appointment = new Appointment(100, new Date(4521452), "SUNDAY", appointmentTakenTimes, doctor);
		patient.setAppointmentTakenTime(appointmentTakenTimes);
		appointmentTakenTime.setAppointment(appointment);
		appointmentTakenTimes.add(appointmentTakenTime);
		patient.setAppointmentTakenTime(appointmentTakenTimes);
		patientServiceImpl.setMobileNumber(9108074711l);
		Mockito.when(patientRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(patient);
		PatientAppointmentStatusDto patientAppointmentStatus = patientServiceImpl.patientAppointmentStatus();
		assertEquals(patientAppointmentStatus.getDoctorName().split(" ")[0], doctor.getFirstName());
	}

	@Test
	void testSaveFeedBack() {
		List<AppointmentTakenTime> appointmentTakenTimes = new ArrayList<>();
		Patient patient = new Patient("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l, 0, 0);
		patient.setPatientId(100);
		Doctor doctor = new Doctor("sahid", "alom", "sahidalom1234@gmail.com", 8399842584l, "12345", "MALE", 4.5, 0, 1);
		patient.setAppointmentTakenTime(appointmentTakenTimes);
		List<Patient> patients = new ArrayList<>();
		patients.add(patient);
		AppointmentTakenTime appointmentTakenTime = new AppointmentTakenTime("5.00", "5.30", "0", patients);
		Appointment appointment = new Appointment(100, new Date(4521452), "SUNDAY", appointmentTakenTimes, doctor);
		patient.setAppointmentTakenTime(appointmentTakenTimes);
		appointmentTakenTime.setAppointment(appointment);
		appointmentTakenTimes.add(appointmentTakenTime);
		patient.setAppointmentTakenTime(appointmentTakenTimes);
		FeedBack feedBack = new FeedBack(4.5, "Good", patient, doctor);
		FeedbackDto feedbackDto = new FeedbackDto(4.5, "Good");
		patientServiceImpl.setMobileNumber(9108074711l);
		Mockito.when(patientRepository.findByMobileNumber(Mockito.anyLong())).thenReturn(patient);
		Mockito.when(appoinmenTakenTimeReposiory.findAll()).thenReturn(appointmentTakenTimes);
		Mockito.when(feedBackRepository.save(Mockito.any())).thenReturn(feedBack);
		FeedbackDto saveFeedBack = patientServiceImpl.saveFeedBack(feedbackDto);
		assertEquals(saveFeedBack.getReviews(), feedbackDto.getReviews());
	}

	@Test
	void testPatientVerify() {
		Patient patient = new Patient("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l, 0, 0);
		Role role = new Role();
		role.setRole("PATIENT");
		List<Patient> patients = new ArrayList<>();
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		patients.add(patient);
		patientServiceImpl.setPatients(patients);
		patientServiceImpl.setOTP(123l);
		Mockito.when(patientRepository.save(patient)).thenReturn(patient);
		Mockito.when(roleRepository.save(Mockito.any())).thenReturn(patient);
		Patient patientVerify = patientServiceImpl.patientVerify(123l);
		assertEquals(patientVerify.getFirstName(), patient.getFirstName());
	}

	@Test
	void testReSendOTP() {
		String reSendOTP = patientServiceImpl.reSendOTP();
		assertNotNull(reSendOTP);
	}

}
