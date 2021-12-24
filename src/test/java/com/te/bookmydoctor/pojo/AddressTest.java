package com.te.bookmydoctor.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.model.Address;

/**
 * @author Sahid
 *
 */

class AddressTest {

	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}	
	private String jsonValue="{\"addressId\":1,\"street\":\"Dhubri\",\"area\":\"Dhubri\",\"city\":\"Dhubri\",\"zipCode\":783334,\"state\":\"Assam\",\"country\":\"India\",\"doctor\":{\"doctorId\":null,\"firstName\":\"Shiva\",\"lastName\":\"Radhe\",\"email\":\"Shiva@gmail.com\",\"mobileNumber\":8880638598,\"password\":\"MALE\",\"gender\":\"shivu123\",\"averageRating\":5.0,\"isDeleted\":0,\"isVerified\":1,\"language\":null,\"addresses\":null,\"qualification\":null,\"specialization\":null,\"appointments\":null,\"feedBacks\":null,\"verification\":null,\"roles\":null}}";
    @Test
	void testAddressSerialization() throws JsonProcessingException {
//		Doctor doctor = new Doctor("Shiva", "Radhe", "Shiva@gmail.com", 8880638598l, "MALE", "shivu123", 5.0, 0, 1);
//		Address address = new Address(1, "Dhubri", "Dhubri", "Dhubri", 783334, "Assam", "India", doctor);
		
		Address address = mapper.readValue(jsonValue, Address.class);
		String string = mapper.writeValueAsString(address);
		assertEquals(jsonValue, string);
	}
	@Test
	void testAddressDeserialization() throws JsonMappingException, JsonProcessingException {
		Address address = mapper.readValue(jsonValue, Address.class);
		assertEquals(1, address.getAddressId());
	}

}
