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
import com.te.bookmydoctor.model.Appointment;

/**
 * @author Sahid
 *
 */
class AppointmentTest {

	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}

	private String jsonValue = "{\"appointmentId\":100,\"appointmentDate\":4521452,\"day\":\"SUNDAY\",\"appointmentTakenTime\":[{\"appointmentTimeId\":null,\"appointmentTakenTime\":\"5.00\",\"appointmentEndTime\":\"5.30\",\"appointmentStatus\":\"0\",\"appointment\":null,\"patient\":[{\"patientId\":null,\"firstName\":\"Akram\",\"lastName\":\"ladaf\",\"email\":\"sahidalom1234@gmail.com\",\"password\":\"123\",\"gender\":\"male\",\"mobileNumber\":9108074711,\"isDeleted\":0,\"isVerified\":0,\"appointmentTakenTime\":null,\"feedBack\":null,\"roles\":null,\"verification\":null}]}],\"doctor\":{\"doctorId\":null,\"firstName\":\"Shiva\",\"lastName\":\"Radhe\",\"email\":\"Shiva@gmail.com\",\"mobileNumber\":8880638598,\"password\":\"MALE\",\"gender\":\"shivu123\",\"averageRating\":5.0,\"isDeleted\":0,\"isVerified\":1,\"language\":null,\"addresses\":null,\"qualification\":null,\"specialization\":null,\"appointments\":null,\"feedBacks\":null,\"verification\":null,\"roles\":null}}";

	@Test
	void testAppointmentSerialization() throws JsonProcessingException {
//		List<Patient> patients = new ArrayList<>();
//		Patient patient = new Patient("Akram", "ladaf", "sahidalom1234@gmail.com", "123", "male", 9108074711l, 0, 0);
//		patients.add(patient);
//		AppointmentTakenTime appointmentTakenTime = new AppointmentTakenTime("5.00", "5.30", "0", patients);
//		List<AppointmentTakenTime> appointmentTakenTimes = new ArrayList<>();
//		appointmentTakenTimes.add(appointmentTakenTime);
//		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
//		Appointment appointment = new Appointment(100, new Date(4521452), "SUNDAY", appointmentTakenTimes, doctor);

		Appointment appointment = mapper.readValue(this.jsonValue, Appointment.class);
		String jsonValue = mapper.writeValueAsString(appointment);
		assertEquals(this.jsonValue, jsonValue);

	}

	@Test
	void testAppointmentDeserialization() throws JsonMappingException, JsonProcessingException {
		Appointment appointment = mapper.readValue(this.jsonValue, Appointment.class);
		Integer expected = 100;
		assertEquals(expected, appointment.getAppointmentId());
	}

}
