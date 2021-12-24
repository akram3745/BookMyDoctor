package com.te.bookmydoctor.service;

import java.sql.Date;
import java.util.List;

import com.te.bookmydoctor.dto.AppointmentFetchDto;
import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.FeedbackDto;

public interface DoctorService {
	DoctorDto saveDoctor(DoctorDto doctorDto);

	AppointmentFetchDto seeAppointment(Date date);

	List<FeedbackDto> seeFeedBack();

	Integer checkStatus();

	Integer removeDoctor(Long mobileNumber);
}
