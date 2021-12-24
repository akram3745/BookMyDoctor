package com.te.bookmydoctor.exception;

@SuppressWarnings("serial")
public class DoctorInvalidDetailsException extends RuntimeException {
	
	public DoctorInvalidDetailsException(String message) {
		super(message);
	}

}
