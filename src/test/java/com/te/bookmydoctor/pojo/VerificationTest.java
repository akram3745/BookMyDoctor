package com.te.bookmydoctor.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.model.Verification;

/**
 * @author manju
 *
 */
class VerificationTest {
	private ObjectMapper mapper;

	@BeforeEach
	void setUp() throws Exception {
		this.mapper = new ObjectMapper();
	}

	private String json = "{\"verificationId\":10,\"doctor\":{\"doctorId\":null,\"firstName\":\"Shiva\",\"lastName\":\"Shivam0\",\"email\":\"shivu@gmail.com\",\"mobileNumber\":888063898,\"password\":\"shivu@123\",\"gender\":\"MALE\",\"averageRating\":5.0,\"isDeleted\":0,\"isVerified\":1,\"language\":null,\"addresses\":null,\"qualification\":null,\"specialization\":null,\"appointments\":null,\"feedBacks\":null,\"verification\":null,\"roles\":null},\"patient\":{\"patientId\":null,\"firstName\":\"Radhe\",\"lastName\":\"Charan\",\"email\":\"radhe@gmail.com\",\"password\":\"radhe@123\",\"gender\":\"Female\",\"mobileNumber\":8861227977,\"isDeleted\":0,\"isVerified\":1,\"appointmentTakenTime\":null,\"feedBack\":null,\"roles\":null,\"verification\":null},\"admin\":null}";
	@Test
	void testVerificationSerialization() throws JsonProcessingException {

//		Verification verification = new Verification(10,
//				new Doctor("Shiva", "Shivam0", "shivu@gmail.com", 888063898l, "shivu@123", "MALE", 5.0, 0, 1),
//				new Patient("Radhe", "Charan", "radhe@gmail.com", "radhe@123", "Female", 8861227977l, 0, 1), null);
//		String writeValueAsString = mapper.writeValueAsString(verification);
//		System.out.println(writeValueAsString);

		Verification readValue = mapper.readValue(json, Verification.class);
		String writeValueAsString = mapper.writeValueAsString(readValue);
		assertEquals(json, writeValueAsString);
	}

	@Test
	void testVerificationDeserialization() throws JsonProcessingException {
		Verification readValue = mapper.readValue(json, Verification.class);
		assertEquals("radhe@gmail.com", readValue.getPatient().getEmail());
	}

}
