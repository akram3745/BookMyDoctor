package com.te.bookmydoctor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.te.bookmydoctor.dto.AddressDto;
import com.te.bookmydoctor.dto.AppointmentFetchDto;
import com.te.bookmydoctor.dto.AppointmentTakenTimeFetchDto;
import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.dto.LanguageDto;
import com.te.bookmydoctor.model.Appointment;
import com.te.bookmydoctor.model.AppointmentTakenTime;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.repository.AddressRepository;
import com.te.bookmydoctor.repository.DoctorRepository;
import com.te.bookmydoctor.repository.LanguageRepository;
import com.te.bookmydoctor.repository.QualificationRepository;
import com.te.bookmydoctor.repository.RoleRepository;
import com.te.bookmydoctor.repository.SpecializationRepository;

@SpringBootTest
class DoctorServiceImplTest {
	@InjectMocks
	private DoctorServiceImpl doctorServiceImpl;
	@Mock
	private DoctorRepository doctorRepository;
	@Mock
	private AddressRepository addressRepository;
	@Mock
	private QualificationRepository qualificationRepository;
	@Mock
	private LanguageRepository languageRepository;
	@Mock
	private SpecializationRepository specializationRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void testSaveDoctor() {
		List<AddressDto> addressDtos = new ArrayList<AddressDto>();
		addressDtos.add(
				new AddressDto("2nd Cross", "Kareesandra", "Banashankari 2nd Stage", 560070, "Karnataka", "India"));
		List<LanguageDto> languageDtos = new ArrayList<LanguageDto>();
		languageDtos.add(new LanguageDto("Kannada"));
		DoctorDto doctorDto = new DoctorDto("Shivaa", "Shivu", "shivu@gmail.com", 8880638598l, "shivu@123", "MALE", 5.0,
				addressDtos, languageDtos, "MBBS", "Kempegowda Insititution Of Medical And Science", 2017, "FRCS",
				"KIMS8861", "EYE and SKIN");
		Mockito.when(doctorServiceImpl.saveDoctor(doctorDto)).thenReturn(doctorDto);
		assertEquals("shivu@gmail.com", doctorDto.getEmail());
	}

	@SuppressWarnings("deprecation")
	@Test
	void testSeeAppointment() {
		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
		List<AppointmentTakenTimeFetchDto> appointmentTakenTimeFetchDtos = new ArrayList<>();
		AppointmentTakenTimeFetchDto appointmentTakenTimeFetchDto = new AppointmentTakenTimeFetchDto("shivuu", "9-0am",
				"10-am");
		appointmentTakenTimeFetchDtos.add(appointmentTakenTimeFetchDto);
		AppointmentFetchDto appointmentFetchDto = new AppointmentFetchDto(new Date(2021, 12, 13),
				appointmentTakenTimeFetchDtos);
		Appointment appointment = new Appointment(new Date(2021, 12, 13), "Monday", doctor);
		List<AppointmentTakenTime> appointmentTakenTime = new ArrayList<AppointmentTakenTime>();
		List<Patient> patients = new ArrayList<Patient>();
		patients.add(new Patient("shiva", "shivu", "shivuuu@gmaul.com", "88611", "MALE", 8880638598l, 0, 1));
		appointmentTakenTime.add(new AppointmentTakenTime("9-am", "10-am", "booked", patients));
		List<Appointment> appointments = new ArrayList<Appointment>();
		appointments.add(appointment);
		appointment.setAppointmentTakenTime(appointmentTakenTime);
		appointmentFetchDto.setAppointmentTakenTimeDtos(appointmentTakenTimeFetchDtos);
		doctor.setAppointments(appointments);
		Mockito.when(doctorRepository.getById(Mockito.any())).thenReturn(doctor);
		assertEquals("9-0am", appointmentFetchDto.getAppointmentTakenTimeDtos().get(0).getAppointmentTakenTime());
	}

	@Test
	void testSeeFeedBack() {
		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
		List<FeedbackDto> feedbackDtos = new ArrayList<FeedbackDto>();
		feedbackDtos.add(new FeedbackDto(4.0, "Good"));
		feedbackDtos.add(new FeedbackDto(3.0, "Average"));
		Mockito.when(doctorRepository.getById(Mockito.any())).thenReturn(doctor);
		assertEquals(4.0,feedbackDtos.get(0).getRating());
	}

	@Test
	void testRemoveDoctor() {
		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
		Integer isDeleted = 1;
		Mockito.when(doctorRepository.findByMobileNumber(8880638598l)).thenReturn(doctor);
		Integer removeDoctor = doctorServiceImpl.removeDoctor(8880638598l);
		assertEquals(removeDoctor, isDeleted);
	}

	@Test
	void testCheckStatus() {
		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
		Integer isVerified = 1;
		Mockito.when(doctorRepository.getById(Mockito.any())).thenReturn(doctor);
		Integer checkStatus = doctorServiceImpl.checkStatus();
		assertEquals(isVerified, checkStatus);
	}

}
