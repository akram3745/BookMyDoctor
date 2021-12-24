package com.te.bookmydoctor.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
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
import com.te.bookmydoctor.dto.AddressDto;
import com.te.bookmydoctor.dto.AppointmentFetchDto;
import com.te.bookmydoctor.dto.AppointmentTakenTimeFetchDto;
import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.dto.LanguageDto;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.response.ResponseMessage;
import com.te.bookmydoctor.service.DoctorService;

@SpringBootTest
class DoctorResourceTest {
	@InjectMocks
	private DoctorResource doctorResource;

	@Mock
	private DoctorService doctorService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(doctorResource).build();
		this.objectMapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Test
	void testSaveDoctor() throws UnsupportedEncodingException, Exception {
		List<AddressDto> addressDtos = new ArrayList<AddressDto>();
		addressDtos.add(
				new AddressDto("2nd Cross", "Kareesandra", "Banashankari 2nd Stage", 560070, "Karnataka", "India"));

		List<LanguageDto> languageDtos = new ArrayList<LanguageDto>();
		languageDtos.add(new LanguageDto("Kannada"));
		DoctorDto doctor = new DoctorDto("Shivaa", "Shivu", "shivu@gmail.com", 8880638598l, "shivu@123", "MALE", 5.0,
				addressDtos, languageDtos, "MBBS", "Kempegowda Insititution Of Medical And Science", 2017, "FRCS",
				"KIMS8861", "EYE and SKIN");
		DoctorDto doctor1 = new DoctorDto(doctor.getEmail(), doctor.getPassword());
		Mockito.when(doctorService.saveDoctor(Mockito.any())).thenReturn(doctor);
		String jsonObject = objectMapper.writeValueAsString(doctor);
		String result = mockMvc
				.perform(post("/api/v1/doctor/register-doctor").contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage readValue = objectMapper.readValue(result, ResponseMessage.class);
		Map<String, String> map = (Map<String, String>) readValue.getData();
		int count = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (count++ < 2) {
				continue;
			}
			assertEquals(doctor1.getEmail(), entry.getValue());
			break;
		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	void testSeeAppointment() throws UnsupportedEncodingException, Exception {
		List<AppointmentTakenTimeFetchDto> appointmentTakenTimeFetchDtos = new ArrayList<>();
		appointmentTakenTimeFetchDtos.add(new AppointmentTakenTimeFetchDto("shivuu", "9-0am", "10-am"));
		AppointmentFetchDto appointmentFetchDto = new AppointmentFetchDto(new Date(2021, 12, 13),
				appointmentTakenTimeFetchDtos);

		Mockito.when(doctorService.seeAppointment(Mockito.any())).thenReturn(appointmentFetchDto);
		String jsonObject = objectMapper.writeValueAsString(appointmentFetchDto);
		String result = mockMvc
				.perform(get("/api/v1/doctor/check-appointment/2021-12-13").contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage readValue = objectMapper.readValue(result, ResponseMessage.class);
		Map<String, String> map = (Map<String, String>) readValue.getData();
		int count = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (count++ < 2) {
				continue;
			}
			assertEquals(appointmentFetchDto.getAppointmentTakenTimeDtos().get(0).getAppointmentTakenTime(),
					entry.getValue());
			break;

		}
	}

	@SuppressWarnings("unchecked")
	@Test
	void testSeeFeedBack() throws UnsupportedEncodingException, Exception {

		List<FeedbackDto> feedbackDtos = new ArrayList<FeedbackDto>();
		feedbackDtos.add(new FeedbackDto(4.0, "Good"));
		feedbackDtos.add(new FeedbackDto(3.0, "Average"));
		Mockito.when(doctorService.seeFeedBack()).thenReturn(feedbackDtos);
		String jsonObject = objectMapper.writeValueAsString(feedbackDtos);
		String result = mockMvc
				.perform(get("/api/v1/doctor/feedback").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage readValue = objectMapper.readValue(result, ResponseMessage.class);
		int count = 0;
		List<Map<String, String>> map = (List<Map<String, String>>) readValue.getData();
		for (Map.Entry<String, String> entry : map.get(0).entrySet()) {
			if (count++ < 1)
				continue;
			assertEquals(feedbackDtos.get(0).getReviews(), entry.getValue());
			break;
		}
	}

	@Test
	void testRemoveDoctor() throws UnsupportedEncodingException, Exception {
		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
		Mockito.when(doctorService.removeDoctor(8880638598l)).thenReturn(1);
		Integer isDeleted = 1;
		String jsonObject = objectMapper.writeValueAsString(doctor);
		String result = mockMvc
				.perform(get("/api/v1/doctor/remove-doctor/8880638598").contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage readValue = objectMapper.readValue(result, ResponseMessage.class);
		assertEquals(isDeleted, readValue.getData());
	}

	@Test
	void testCheckStatus() throws UnsupportedEncodingException, Exception {
		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
		Mockito.when(doctorService.checkStatus()).thenReturn(1);
		Integer isverified = 1;
		String jsonObject = objectMapper.writeValueAsString(doctor);
		String result = mockMvc
				.perform(get("/api/v1/doctor/registration-status").contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		ResponseMessage readValue = objectMapper.readValue(result, ResponseMessage.class);
		assertEquals(isverified, readValue.getData());

	}

}
