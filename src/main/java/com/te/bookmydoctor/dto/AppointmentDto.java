package com.te.bookmydoctor.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
//	@Range(min = 8,max = 10,message = "Phone number should be more than 8 and less then 10")
	private Long doctorMobileNumber;
	private Date appointmentDate;
	private List<AppointmentTakenTimeDto> appointmentTakenTimeDtos;
}
