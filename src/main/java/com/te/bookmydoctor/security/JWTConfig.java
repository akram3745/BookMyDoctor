package com.te.bookmydoctor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@SuppressWarnings("unused")
public class JWTConfig {
	@Autowired
	private Environment environment;
	private long access_token;
	private long refresh_token;

	public long getAccess_token() {
		return Long.parseLong(environment.getProperty("access.token"));
	}

	public long getRefresh_token() {
		return Long.parseLong(environment.getProperty("refresh.token"));
	}
}