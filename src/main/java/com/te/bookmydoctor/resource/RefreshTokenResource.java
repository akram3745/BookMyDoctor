package com.te.bookmydoctor.resource;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.repository.DoctorRepository;
import com.te.bookmydoctor.repository.PatientRepository;
import com.te.bookmydoctor.security.JWTConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(value = "/api/v1", tags = "Refresh Token Resource")
public class RefreshTokenResource {
	
	@Autowired
	private JWTConfig config;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	
	@GetMapping("/token/refresh/patient")
	@ApiOperation(value = "Generate new Access token ", notes = "Generate new Access token ", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Generate new Access token Successfully"),
			@ApiResponse(code = 404, message = "Refresh token is missing"),
			@ApiResponse(code = 403, message = "Access Denied") })
	public void refreashTokenPatient(HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		String header = request.getHeader(AUTHORIZATION);
		if (header != null && header.startsWith("Bearer ")) {
			try {
				String refresh_token = header.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				Patient patient = patientRepository.findByEmail(username);
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(patient.getRoles().get(0).getRole()));
				String access_token = JWT.create().withSubject(patient.getEmail())
						.withExpiresAt(new Date(System.currentTimeMillis() + config.getAccess_token()))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles",
								authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
						.sign(algorithm);
				LinkedHashMap<String, Object> infomation = new LinkedHashMap<>();
				LinkedHashMap<String, Object> tokens = new LinkedHashMap<>();
				infomation.put("error", false);
				infomation.put("message", "Successfully Generate Access token");
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				infomation.put("data", tokens);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), infomation);
			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				response.setStatus(FORBIDDEN.value());
				HashMap<String, String> error = new HashMap<>();
				error.put("error", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else
			throw new RuntimeException("Refresh token is missing");

	}
	
	@GetMapping("/token/refresh/doctor")
	@ApiOperation(value = "Generate new Access token ", notes = "Generate new Access token ", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Generate new Access token Successfully"),
			@ApiResponse(code = 404, message = "Refresh token is missing"),
			@ApiResponse(code = 403, message = "Access Denied") })
	public void refreashTokenDoctor(HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		String header = request.getHeader(AUTHORIZATION);
		if (header != null && header.startsWith("Bearer ")) {
			try {
				String refresh_token = header.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				Doctor doctor = doctorRepository.findByEmail(username);
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(doctor.getRoles().get(0).getRole()));
				String access_token = JWT.create().withSubject(doctor.getEmail())
						.withExpiresAt(new Date(System.currentTimeMillis() + config.getAccess_token()))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles",
								authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
						.sign(algorithm);
				LinkedHashMap<String, Object> infomation = new LinkedHashMap<>();
				LinkedHashMap<String, Object> tokens = new LinkedHashMap<>();
				infomation.put("error", false);
				infomation.put("message", "Successfully Generate Access token");
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				infomation.put("data", tokens);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), infomation);
			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				response.setStatus(FORBIDDEN.value());
				HashMap<String, String> error = new HashMap<>();
				error.put("error", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else
			throw new RuntimeException("Refresh token is missing");
		
	}
}
