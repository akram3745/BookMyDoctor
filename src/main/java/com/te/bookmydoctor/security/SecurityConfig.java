package com.te.bookmydoctor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.te.bookmydoctor.exception.CustomAccessDeniedException;
import com.te.bookmydoctor.exception.CustomAuthenticationEntryPoint;
import com.te.bookmydoctor.filter.CustomAuthenticattionFilter;
import com.te.bookmydoctor.filter.CustomAuthorizationFilter;
import com.te.bookmydoctor.service.DoctorServiceImpl;
import com.te.bookmydoctor.service.PatientServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private PatientServiceImpl patientServiceImpl; 
	@Autowired
	private DoctorServiceImpl doctorServiceImpl; 

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticattionFilter authenticattionFilter = new CustomAuthenticattionFilter(authenticationManagerBean(),
				config(), new CustomAccessDeniedException());
		CustomAuthorizationFilter authorizationFilter = new CustomAuthorizationFilter(
				new CustomAccessDeniedException(),patientServiceImpl,doctorServiceImpl);
		authenticattionFilter.setFilterProcessesUrl("/api/v1/login");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
				.antMatchers("/api/v1/login/**", "/api/v1/token/refresh/**", "/api/v1/doctor/register-doctor/**",
						"/api/v1/patient/register-patient/**", "/api/v1/patient/patient-verify/**","/api/v1/token/refresh/**",
						"/api/v1/patient/resendotp/**", "/swagger-resources/configuration/ui", "/swagger-resources",
						"/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**")
				.permitAll();
		http.authorizeRequests().antMatchers("/api/v1/doctor/**").hasAnyAuthority("DOCTOR");
		http.authorizeRequests().antMatchers("/api/v1/patient/**").hasAnyAuthority("PATIENT");
		http.authorizeRequests().antMatchers("/api/v1/admin/**").hasAnyAuthority("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		http.addFilter(authenticattionFilter);
		http.addFilterBefore(authorizationFilter, CustomAuthenticattionFilter.class);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JWTConfig config() {
		return new JWTConfig();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
				"/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**");
	}
}
