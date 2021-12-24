package com.te.bookmydoctor.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.model.Language;

/**
 * @author manju
 *
 */
class LanguageTest {
	private ObjectMapper mapper;

	@BeforeEach
	void setUp() throws Exception {
		this.mapper = new ObjectMapper();
	}

	private String json = "{\"language_Id\":null,\"language\":\"Kannada\",\"doctor\":{\"doctorId\":null,\"firstName\":\"Shiva\",\"lastName\":\"Shivam0\",\"email\":\"shivu@gmail.com\",\"mobileNumber\":888063898,\"password\":\"shivu@123\",\"gender\":\"MALE\",\"averageRating\":5.0,\"isDeleted\":0,\"isVerified\":1,\"language\":null,\"addresses\":null,\"qualification\":null,\"specialization\":null,\"appointments\":null,\"feedBacks\":null,\"verification\":null,\"roles\":null}}";
	@Test
	void testLanguageSerialization() throws JsonProcessingException {

//		Language language = new Language("Kannada",
//				new Doctor("Shiva", "Shivam0", "shivu@gmail.com", 888063898l, "shivu@123", "MALE", 5.0, 0, 1));
//		String writeValueAsString = mapper.writeValueAsString(language);
//		System.out.println(writeValueAsString);

		Language readValue = mapper.readValue(json, Language.class);
		String writeValueAsString2 = mapper.writeValueAsString(readValue);
		assertEquals(json, writeValueAsString2);
	}

	@Test
	void testLanguageDeserialization() throws JsonProcessingException {
		Language readValue = mapper.readValue(json, Language.class);
		assertEquals("Kannada", readValue.getLanguage());
	}

}
