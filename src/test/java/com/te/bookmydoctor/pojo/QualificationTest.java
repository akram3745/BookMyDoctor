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
import com.te.bookmydoctor.model.Qualification;

/**
 * @author Sahid
 *
 */
class QualificationTest {
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}

	private String jsonValue = "{\"qualificationId\":1,\"degreeName\":\"MBBS\",\"institutionName\":\"Kempegowda Insititution Of Medical And Science\",\"passingYear\":2017,\"highestQualificationCertificate\":\"FRCS\",\"medicalRegistrationNumber\":\"KIMS8861\",\"doctor\":null}";

	@Test
	void testQualificationSerialization() throws JsonProcessingException {
//		Qualification qualification = new Qualification(1, "MBBS", "Kempegowda Insititution Of Medical And Science", 2017, "FRCS",
//				"KIMS8861",null);
		Qualification qualification = mapper.readValue(this.jsonValue, Qualification.class);
		String jsonValue = mapper.writeValueAsString(qualification);
		assertEquals(this.jsonValue, jsonValue);
	}

	@Test
	void testQualificationDeserialization() throws JsonMappingException, JsonProcessingException {
		Qualification qualification = mapper.readValue(this.jsonValue, Qualification.class);
		Integer expected = 1;
		assertEquals(expected, qualification.getQualificationId());
	}

}
