package com.te.bookmydoctor.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.dto.AppointmentDto;
import com.te.bookmydoctor.dto.AppointmentTakenTimeDto;
import com.te.bookmydoctor.dto.DoctorFetch;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.dto.PatientAppointmentStatusDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.dto.SearchDoctorDto;
import com.te.bookmydoctor.model.Address;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.Language;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.model.Qualification;
import com.te.bookmydoctor.model.Specialization;
import com.te.bookmydoctor.response.ResponseMessage;
import com.te.bookmydoctor.service.PatientService;

@SpringBootTest
class PatientResourceTest {
	@Mock
	private PatientService patientService;
	@InjectMocks
	PatientResource patientResource;
	
	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(patientResource).build();
		this.mapper = new ObjectMapper();
	}
	@SuppressWarnings("unchecked")
	@Test
	void testSearchDoctor() throws UnsupportedEncodingException, Exception {
		SearchDoctorDto searchDoctorDto = new SearchDoctorDto("India", "Dhubri", "Dhubri", "Dhubri", "ENT", "Hindi", "Male");
		DoctorFetch doctorFetch = new DoctorFetch(100, "Sahid Alom", 4.5);
		List<DoctorFetch> doctorFetchs=new ArrayList<>();
		doctorFetchs.add(doctorFetch);
		Doctor doctor = new Doctor("Sahid", "Alom", "sa@gmail.com", 123457843l, "12345", "MALE", 4.5,0, 1);
		Qualification qualification = new Qualification("ABC", "MNP", 2012, "CFX.png", "bcv1234", doctor);
		doctor.setQualification(qualification);
		List<Address> Addresses=new ArrayList<Address>();
		Address address = new  Address("Dhubri", "Dhubri", "Dhubri", 7412584, "Assam", "India", doctor);
		Addresses.add(address);
		doctor.setAddresses(Addresses);
		List<Language> languages=new ArrayList<Language>();
		Language language = new Language("Hindi", doctor);
		languages.add(language);
		doctor.setLanguage(languages);
		Specialization specialization = new Specialization("ENT", doctor);
		doctor.setSpecialization(specialization);
		Mockito.when(patientService.searchDoctor(Mockito.any())).thenReturn(doctorFetchs);
		String jsonObject = mapper.writeValueAsString(searchDoctorDto);
		String result = mockMvc
				.perform(post("/api/v1/patient/search-doctor").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println(result);
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		System.out.println(responseMessage);
		List<Map<String, String>> map = (List<Map<String, String>>) responseMessage.getData();
		for (Map.Entry<String, String> message : map.get(0).entrySet()) {
			assertEquals(doctorFetch.getDoctorId(), message.getValue());
			break;
		}
	}

	// done
	@Test
	void testAddPatient() throws UnsupportedEncodingException, Exception {
		PatientDto patientDto = new PatientDto("Akram", "ladaf", "dbydby12@gmail.com", "12As@1233", "MALE", 9108074711l);

		Mockito.when(patientService.savePatient(Mockito.any())).thenReturn(patientDto);

		String jsonString = mapper.writeValueAsString(patientDto);
		String resultString = mockMvc
				.perform(post("/api/v1/patient/register-patient").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonString))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

		ResponseMessage message = mapper.readValue(resultString, ResponseMessage.class);

		String email = (String) message.getData();
		assertEquals(patientDto.getEmail(), email);

	}

//done
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test
	void testPatientAppointment() throws UnsupportedEncodingException, Exception {
		AppointmentTakenTimeDto appointment = new AppointmentTakenTimeDto("8.00", "9.00", "appointment is fixed");
		List<AppointmentTakenTimeDto> list = new ArrayList<>();
		list.add(appointment);
		java.sql.Date date = new java.sql.Date(2021, 12, 11);
		AppointmentDto appointmentdto = new AppointmentDto(9108074711l, date, list);
		Mockito.when(patientService.patientAppointment(Mockito.any())).thenReturn(appointmentdto);
		String jsonString = mapper.writeValueAsString(appointmentdto);
		String resultString = mockMvc
				.perform(post("/api/v1/patient/book-appoinment").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonString))
				.andExpectAll(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage message = mapper.readValue(resultString, ResponseMessage.class);
		Map<String, String> map = (Map<String, String>) message.getData();
		for (Map.Entry<String, String> mm : map.entrySet()) {
			assertEquals(appointmentdto.getDoctorMobileNumber(), mm.getValue());
			break;
		}
	}

	// Done
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test
	void testPatientAppointmentStatus() throws UnsupportedEncodingException, Exception {
		AppointmentTakenTimeDto appointment = new AppointmentTakenTimeDto("8.00", "9.00", "appointment is fixed");
		List<AppointmentTakenTimeDto> list = new ArrayList<>();
		list.add(appointment);
		java.sql.Date date = new java.sql.Date(1995, 03, 11);
		AppointmentDto appointmentdto = new AppointmentDto(9108074711l, date, list);
		List<AppointmentDto> list2 = new ArrayList<>();
		list2.add(appointmentdto);

		PatientAppointmentStatusDto patient = new PatientAppointmentStatusDto();
		patient.setDoctorName("Akram ladaf");
		patient.setPatientName("Shahid Alom");
		patient.setAppointmentDtos(list2);

		Mockito.when(patientService.patientAppointmentStatus()).thenReturn(patient);
		String jsonString = mapper.writeValueAsString(patient);
		String resultString = mockMvc
				.perform(get("/api/v1/patient/check-appoinment")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString))
				.andExpectAll(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

		ResponseMessage message = mapper.readValue(resultString, ResponseMessage.class);
		Map<String, String> map = (Map<String, String>) message.getData();
		for (Map.Entry<String, String> mm : map.entrySet()) {
			assertEquals(patient.getDoctorName(), mm.getValue());
			break;
		}

	}

	// done
	@SuppressWarnings("unchecked")
	@Test
	void testAddFeedBack() throws UnsupportedEncodingException, Exception {
		FeedbackDto feedbackDto = new FeedbackDto(6.00, "nice doctor");
		Mockito.when(patientService.saveFeedBack(Mockito.any())).thenReturn(feedbackDto);
		String jsonString = mapper.writeValueAsString(feedbackDto);
		String resultString = mockMvc
				.perform(post("/api/v1/patient/add-feedback").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonString))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage message = mapper.readValue(resultString, ResponseMessage.class);
		Map<String, String> map = (Map<String, String>) message.getData();
		for (Map.Entry<String, String> mm : map.entrySet()) {
			assertEquals(feedbackDto.getRating(), mm.getValue());
			break;
		}
	}

	// done
	@Test
	void testPatientVerify() throws UnsupportedEncodingException, Exception {
		Patient patient = new Patient("Akram", "ladaf", "34akramash@gmail.com", "123", "male", 9108074711l, 0, 0);
		List<Patient> list = new ArrayList<>();
		list.add(patient);
		Mockito.when(patientService.patientVerify(Mockito.anyLong())).thenReturn(patient);
		String jsonString = mapper.writeValueAsString(patient);
		String resultString = mockMvc
				.perform(get("/api/v1/patient/patient-verify/" + 564788).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonString))
				.andExpectAll(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage message = mapper.readValue(resultString, ResponseMessage.class);
		String data = (String) message.getData();
		assertEquals(patient.getFirstName() + " " + patient.getLastName(), data);

	}

	@Test
	void testReSendOTP() throws UnsupportedEncodingException, Exception {
		String messageString = "Your OTP is sent  to your Gmail ";
		Mockito.when(patientService.reSendOTP()).thenReturn(messageString);
		String jsonString = mapper.writeValueAsString(messageString);
		String resultString = mockMvc
				.perform(get("/api/v1/patient/resendotp").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonString))
				.andExpectAll(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage message = mapper.readValue(resultString, ResponseMessage.class);
		String messsage2 = (String)message.getData();
		assertEquals(messageString, messsage2);
		
	}

}
