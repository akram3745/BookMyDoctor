package com.te.bookmydoctor.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SwaggerProperty {
	@Value("${swagger.title}")
	private String title;
	@Value("${swagger.description}")
	private String doc;
	@Value("${swagger.project.version}")
	private String version;
	@Value("${swagger.termsofservice}")
	private String termsOfService;
	@Value("${swagger.contact.username}")
	private String username;
	@Value("${swagger.contact.website}")
	private String website;
	@Value("${swagger.contact.email}")
	private String email;
	@Value("${swagger.api.license}")
	private String license;
	@Value("${swagger.api.licenseurl}")
	private String licenseurl;
}
