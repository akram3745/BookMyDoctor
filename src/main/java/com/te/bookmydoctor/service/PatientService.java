package com.te.bookmydoctor.service;

import java.util.List;

import com.te.bookmydoctor.dto.AppointmentDto;
import com.te.bookmydoctor.dto.DoctorFetch;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.dto.PatientAppointmentStatusDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.dto.SearchDoctorDto;
import com.te.bookmydoctor.model.Patient;

public interface PatientService {
	List<DoctorFetch> searchDoctor(SearchDoctorDto searchDoctorDto);

	PatientDto savePatient(PatientDto patientDto);

	AppointmentDto patientAppointment(AppointmentDto appointmentDto);

	PatientAppointmentStatusDto patientAppointmentStatus();

	FeedbackDto saveFeedBack(FeedbackDto feedbackDto);
	
	String reSendOTP();

	Patient patientVerify(Long OTP);
}
