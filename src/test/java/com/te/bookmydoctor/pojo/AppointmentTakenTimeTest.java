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
import com.te.bookmydoctor.model.AppointmentTakenTime;

/**
 * @author Sahid
 *
 */
class AppointmentTakenTimeTest {

	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}

	private String jsonValue = "{\"appointmentTimeId\":null,\"appointmentTakenTime\":\"5.00\",\"appointmentEndTime\":\"5.30\",\"appointmentStatus\":\"0\",\"appointment\":null,\"patient\":[{\"patientId\":null,\"firstName\":\"Akram\",\"lastName\":\"ladaf\",\"email\":\"sahidalom1234@gmail.com\",\"password\":\"123\",\"gender\":\"male\",\"mobileNumber\":9108074711,\"isDeleted\":0,\"isVerified\":0,\"appointmentTakenTime\":null,\"feedBack\":null,\"roles\":null,\"verification\":null}]}";

	@Test
	void testAppointmentTakenTimeSerialization() throws JsonMappingException, JsonProcessingException {
//		List<Patient> patients = new ArrayList<>();
//		Patient patient = new Patient("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l, 0, 0);
//		patients.add(patient);
//		AppointmentTakenTime appointmentTakenTime = new AppointmentTakenTime("5.00", "5.30", "0", patients);

		AppointmentTakenTime appointmentTakenTime = mapper.readValue(this.jsonValue, AppointmentTakenTime.class);
		String jsonValue = mapper.writeValueAsString(appointmentTakenTime);
		 assertEquals(this.jsonValue, jsonValue);
	}

	@Test
	void testAppointmentTakenTimeDeserialization() throws JsonMappingException, JsonProcessingException {
		AppointmentTakenTime appointmentTakenTime = mapper.readValue(this.jsonValue, AppointmentTakenTime.class);
		String  expected="5.00";
		assertEquals(expected, appointmentTakenTime.getAppointmentTakenTime());
	}

}
