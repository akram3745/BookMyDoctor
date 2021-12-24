package com.te.bookmydoctor.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAppointmentStatusDto {
	
	private String doctorName;
	private String patientName;
	private List<AppointmentDto> appointmentDtos;
}
