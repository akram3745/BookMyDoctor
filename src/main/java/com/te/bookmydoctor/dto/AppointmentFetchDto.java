package com.te.bookmydoctor.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFetchDto {
	private Date appointmentDate;
	private List<AppointmentTakenTimeFetchDto> appointmentTakenTimeDtos;
}
