package com.te.bookmydoctor.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.DoctorFetchAllDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.response.ResponseMessage;
import com.te.bookmydoctor.service.AdminService;

@SpringBootTest
class AdminResourceTest {

	@Mock
	private AdminService adminService;

	@InjectMocks
	private AdminResource adminResource;

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(adminResource).build();
		this.mapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetAllDoctor() throws UnsupportedEncodingException, Exception {
		List<DoctorFetchAllDto> fetchAllDtos = new ArrayList<>();
		DoctorFetchAllDto fetchAllDto1 = new DoctorFetchAllDto("Sahid Alom", 4.5, 1, 0, 8745124534l);
		DoctorFetchAllDto fetchAllDto2 = new DoctorFetchAllDto("Dundappa ", 4.7, 1, 0, 9835124534l);
		fetchAllDtos.add(fetchAllDto1);
		fetchAllDtos.add(fetchAllDto2);
		Mockito.when(adminService.getAllDoctor()).thenReturn(fetchAllDtos);
		String jsonObject = mapper.writeValueAsString(fetchAllDtos);
		String result = mockMvc
				.perform(get("/api/v1/admin/doctors").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		List<Map<String, String>> map = (List<Map<String, String>>) responseMessage.getData();
		for (Map.Entry<String, String> m : map.get(0).entrySet()) {
			assertEquals(fetchAllDto1.getDoctorName(), m.getValue());
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetDoctor() throws UnsupportedEncodingException, Exception {
		DoctorDto doctorDto = new DoctorDto("sahid", "alom", "sahidalom1234@gmail.com", 8399842584l, "12As@345", "MALE",
				4.5, "MBBS", "ABC", 2014, "xyz.png", "x452145", "ENT");
		Mockito.when(adminService.getDoctor(Mockito.anyLong())).thenReturn(doctorDto);
		String jsonObject = mapper.writeValueAsString(doctorDto);
		String result = mockMvc
				.perform(get("/api/v1/admin/doctor/" + doctorDto.getMobileNumber())
						.contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		Map<String, String> map = (Map<String, String>) responseMessage.getData();
		for (Map.Entry<String, String> m : map.entrySet()) {
			assertEquals(doctorDto.getFirstName(), m.getValue());
			break;
		}
	}

	@Test
	void testRemoveDoctor() throws UnsupportedEncodingException, Exception {
		Doctor doctor = new Doctor("sahid", "alom", "sahidalom1234@gmail.com", 8399842584l, "12345", "MALE", 4.5, 0, 1);
		Mockito.when(adminService.removeDoctor(Mockito.anyLong())).thenReturn(1);
		String jsonObject = mapper.writeValueAsString(doctor);
		String result = mockMvc
				.perform(get("/api/v1/admin/remove-doctor/" + doctor.getMobileNumber())
						.contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		assertEquals(doctor.getIsVerified(), responseMessage.getData());
	}

	@Test
	void testRemovePatient()  throws UnsupportedEncodingException, Exception{
		Patient patient = new Patient("sahid", "alom", "sahidalom1234@gmail.com", "12345", "MALE", 8399842584l, 0, 1);
		Mockito.when(adminService.removePatient(Mockito.anyLong())).thenReturn(1);
		String jsonObject = mapper.writeValueAsString(patient);
		String result = mockMvc
				.perform(get("/api/v1/admin/remove-patient/" + patient.getMobileNumber())
						.contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		assertEquals(patient.getIsVerified(), responseMessage.getData());
	}

	@Test
	void testDoctorVerified() throws UnsupportedEncodingException, Exception {
		Doctor doctor = new Doctor("sahid", "alom", "sahidalom1234@gmail.com", 8399842584l, "12345", "MALE", 4.5, 0, 0);
		Integer expected=1;
		Mockito.when(adminService.doctorVerification(Mockito.anyLong())).thenReturn(1);
		String jsonObject = mapper.writeValueAsString(doctor);
		String result = mockMvc
				.perform(get("/api/v1/admin/verify-doctor/" + doctor.getMobileNumber())
						.contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		assertEquals(expected, responseMessage.getData());
	}

	@Test
	void testPatientVerified() throws UnsupportedEncodingException, Exception{
		Patient patient = new Patient("sahid", "alom", "sahidalom1234@gmail.com", "12345", "MALE", 8399842584l, 0, 0);
		Integer expected=1;
		Mockito.when(adminService.patientVerification(Mockito.anyLong())).thenReturn(1);
		String jsonObject = mapper.writeValueAsString(patient);
		String result = mockMvc
				.perform(get("/api/v1/admin/verify-patient/" + patient.getMobileNumber())
						.contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		assertEquals(expected, responseMessage.getData());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetPatient() throws UnsupportedEncodingException, Exception {
		PatientDto patientDto = new PatientDto("Sahid", "Alom", "sahid@123@gmail.com", "12345", "MALE", 123457898l);
		Mockito.when(adminService.getPatient(Mockito.anyLong())).thenReturn(patientDto);
		String jsonObject = mapper.writeValueAsString(patientDto);
		String result = mockMvc
				.perform(get("/api/v1/admin/patient/" + patientDto.getMobileNumber())
						.contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		Map<String, String> map = (Map<String, String>) responseMessage.getData();
		for (Map.Entry<String, String> m : map.entrySet()) {
			assertEquals(patientDto.getFirstName(), m.getValue());
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetAllPatient() throws UnsupportedEncodingException, Exception {
		List<PatientDto> patientDtos = new ArrayList<>();
		PatientDto patientDto1 = new PatientDto("Sahid", "Alom", "sahid@123@gmail.com", "12345", "MALE", 123457898l);
		PatientDto patientDto2 = new PatientDto("Sahi2", "Alom", "sahin@123@gmail.com", "12345", "MALE", 789812345l);
		patientDtos.add(patientDto1);
		patientDtos.add(patientDto2);
		Mockito.when(adminService.getAllPatient()).thenReturn(patientDtos);
		String jsonObject = mapper.writeValueAsString(patientDtos);
		String result = mockMvc
				.perform(get("/api/v1/admin/patients").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage responseMessage = mapper.readValue(result, ResponseMessage.class);
		List<Map<String, String>> map = (List<Map<String, String>>) responseMessage.getData();
		for (Map.Entry<String, String> m : map.get(0).entrySet()) {
			assertEquals(patientDto1.getFirstName(), m.getValue());
			break;
		}
	}

}
