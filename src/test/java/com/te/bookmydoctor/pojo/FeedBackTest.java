/**
 * 
 */
package com.te.bookmydoctor.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.model.FeedBack;

/**
 * @author Sahid
 *
 */
class FeedBackTest {
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}

	private String jsonValue = "{\"feedbackId\":null,\"rating\":4.5,\"reviews\":\"Good\",\"patient\":{\"patientId\":null,\"firstName\":\"Akram\",\"lastName\":\"ladaf\",\"email\":\"sahidalom1234@gmail.com\",\"password\":\"123\",\"gender\":\"male\",\"mobileNumber\":9108074711,\"isDeleted\":0,\"isVerified\":0,\"appointmentTakenTime\":null,\"feedBack\":null,\"roles\":null,\"verification\":null},\"doctor\":{\"doctorId\":null,\"firstName\":\"Shiva\",\"lastName\":\"Radhe\",\"email\":\"Shiva@gmail.com\",\"mobileNumber\":8880638598,\"password\":\"MALE\",\"gender\":\"shivu123\",\"averageRating\":5.0,\"isDeleted\":0,\"isVerified\":1,\"language\":null,\"addresses\":null,\"qualification\":null,\"specialization\":null,\"appointments\":null,\"feedBacks\":null,\"verification\":null,\"roles\":null}}";

	@Test
	void testFeedBackSerialization() throws JsonProcessingException {
//		Patient patient = new Patient("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l, 0, 0);
//		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
//		FeedBack feedBack = new FeedBack(4.5, "Good", patient, doctor);
		FeedBack feedBack = mapper.readValue(this.jsonValue, FeedBack.class);
		String jsonValue = mapper.writeValueAsString(feedBack);
		assertEquals(this.jsonValue, jsonValue);

	}

	@Test
	void testFeedBackDeserialization() throws JsonMappingException, JsonProcessingException {
		FeedBack feedBack = mapper.readValue(this.jsonValue, FeedBack.class);
		String expected = "Good";
		assertEquals(expected, feedBack.getReviews());
	}

}
