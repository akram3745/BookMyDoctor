package com.te.bookmydoctor.exception;

@SuppressWarnings("serial")
public class PatientInvalidDetailsException extends RuntimeException {
	public PatientInvalidDetailsException(String message){
		super(message);
	}

}
