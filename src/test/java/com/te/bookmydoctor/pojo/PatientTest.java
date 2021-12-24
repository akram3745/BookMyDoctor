package com.te.bookmydoctor.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.model.Patient;
/**
 * @author Sahid
 *
 */

class PatientTest {
	
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}
	private String jsonValue="{\"patientId\":null,\"firstName\":\"Akram\",\"lastName\":\"ladaf\",\"email\":\"sahidalom1234@gmail.com\",\"password\":\"123\",\"gender\":\"male\",\"mobileNumber\":9108074711,\"isDeleted\":0,\"isVerified\":0,\"appointmentTakenTime\":null,\"feedBack\":null,\"roles\":null,\"verification\":null}";
	@Test
	void testPatientSerialization() throws JsonProcessingException {
//		Patient patient = new Patient("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l, 0, 0);
//		Address address = mapper.readValue(jsonValue, Address.class);
		Patient patient = mapper.readValue(this.jsonValue, Patient.class);
		String jsonValue = mapper.writeValueAsString(patient);
		assertEquals(this.jsonValue, jsonValue);
	}
	@Test
	void testPatientDeserialization() throws JsonMappingException, JsonProcessingException {
		String expected="Akram";
		Patient patient = mapper.readValue(this.jsonValue, Patient.class);
		assertEquals(expected, patient.getFirstName());
	}

}
