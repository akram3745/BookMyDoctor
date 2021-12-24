package com.te.bookmydoctor.filter;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.bookmydoctor.exception.CustomAccessDeniedException;
import com.te.bookmydoctor.security.JWTConfig;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomAuthenticattionFilter extends UsernamePasswordAuthenticationFilter{
	private final AuthenticationManager authenticationManager;
	private JWTConfig config;
	private CustomAccessDeniedException accessDenied;

	public CustomAuthenticattionFilter(AuthenticationManager authenticationManager,JWTConfig config,
			CustomAccessDeniedException accessDenied) {
		this.authenticationManager = authenticationManager;
		this.config = config;
		this.accessDenied = accessDenied;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		log.info("Username is " + username + " And Password Is " + password);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		Authentication authenticate = null;
		try {
			authenticate = authenticationManager.authenticate(authenticationToken);
			log.info("Authentication Successfully!!!");
		} catch (Exception exception) {
			try {
				log.error(exception.getMessage());
				accessDenied.handle(request, response, new AccessDeniedException(exception.getMessage()));
			} catch (Exception exception2) {
				System.out.println(exception2.getMessage());
			}
		}
		return authenticate;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + config.getAccess_token()))
				.withIssuer(request.getRequestURI().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		String refresh_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + config.getRefresh_token()))
				.withIssuer(request.getRequestURI().toString()).sign(algorithm);
		log.info("Generate access token And refresh token Successfully");
		LinkedHashMap<String, Object> infomation = new LinkedHashMap<>();
		LinkedHashMap<String, Object> tokens = new LinkedHashMap<>();
		infomation.put("error", false); 
		infomation.put("message", "Successfully Login "+user.getUsername());
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		infomation.put("data", tokens);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), infomation);
	}

}
