/**
 * 
 */
package com.te.bookmydoctor.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.model.Specialization;

/**
 * @author Sahid
 *
 */
class SpecializationTest {
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}
	private String jsonValue="{\"specialization_Id\":null,\"speciality\":\"EYE\",\"doctor\":{\"doctorId\":null,\"firstName\":\"Shiva\",\"lastName\":\"Radhe\",\"email\":\"Shiva@gmail.com\",\"mobileNumber\":8880638598,\"password\":\"MALE\",\"gender\":\"shivu123\",\"averageRating\":5.0,\"isDeleted\":0,\"isVerified\":1,\"language\":null,\"addresses\":null,\"qualification\":null,\"specialization\":null,\"appointments\":null,\"feedBacks\":null,\"verification\":null,\"roles\":null}}";
	@Test
	void testSpecializationSerialization() throws JsonProcessingException {
//		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
//		Specialization specialization = new Specialization("EYE", doctor);
		Specialization specialization = mapper.readValue(this.jsonValue, Specialization.class);
		String jsonValue = mapper.writeValueAsString(specialization);
		assertEquals(this.jsonValue, jsonValue);
	}
	@Test
	void testSpecializationDeserialization() throws JsonProcessingException {
		Specialization specialization = mapper.readValue(this.jsonValue, Specialization.class);
		String expected="EYE";
		assertEquals(expected, specialization.getSpeciality());
	}

}
