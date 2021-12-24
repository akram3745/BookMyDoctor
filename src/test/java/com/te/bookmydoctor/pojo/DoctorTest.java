/**
 * 
 */
package com.te.bookmydoctor.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.model.Doctor;

/**
 * @author Sahid
 *
 */
class DoctorTest {

	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}

	private String jsonString = "{\"doctorId\":null,\"firstName\":\"Shiva\",\"lastName\":\"Radhe\",\"email\":\"Shiva@gmail.com\",\"mobileNumber\":8880638598,\"password\":\"MALE\",\"gender\":\"shivu123\",\"averageRating\":5.0,\"isDeleted\":0,\"isVerified\":1,\"language\":null,\"addresses\":null,\"qualification\":null,\"specialization\":null,\"appointments\":null,\"feedBacks\":null,\"verification\":null,\"roles\":null}";

	@Test
	void testDoctorSerialization() throws JsonProcessingException {
//		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
		Doctor doctor2 = mapper.readValue(jsonString, Doctor.class);
		String string = mapper.writeValueAsString(doctor2);
		assertEquals(jsonString, string);
	}

	@Test
	void testDoctorDeSerialization() throws JsonProcessingException {
		Doctor doctor = mapper.readValue(jsonString, Doctor.class);
		assertEquals("Shiva", doctor.getFirstName());
	}

}
