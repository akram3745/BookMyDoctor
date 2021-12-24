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
import com.te.bookmydoctor.model.Role;

/**
 * @author Sahid
 *
 */
class RoleTest {
	
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
	}
	private String jsonValue="{\"roleId\":null,\"role\":\"ADMIN\",\"patient\":null,\"doctor\":null,\"admin\":null}";

	@Test
	void testRoleSerialization() throws JsonProcessingException {
//		Role role = new Role("ADMIN", null);
		Role role = mapper.readValue(this.jsonValue, Role.class);
		String jsonValue = mapper.writeValueAsString(role);
		assertEquals(this.jsonValue, jsonValue);
	}
	@Test
	void testRoleDeserialization() throws JsonMappingException, JsonProcessingException {
		Role role = mapper.readValue(this.jsonValue, Role.class);
		String expected="ADMIN";
		assertEquals(expected, role.getRole());
	}

}
