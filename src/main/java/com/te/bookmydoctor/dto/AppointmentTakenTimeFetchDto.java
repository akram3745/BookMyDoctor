package com.te.bookmydoctor.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentTakenTimeFetchDto {
	@NotBlank(message = "patientName Name Can Not Be Empty")
	@NotNull(message = "patientName Name Can Not Be null")
	private String patientName;
	@NotBlank(message = "appointmentTakenTime  Can Not Be Empty")
	@NotNull(message = "appointmentTakenTime  Can Not Be null")
	private String appointmentTakenTime;
	@NotBlank(message = "appointmentEndTime  Can Not Be Empty")
	@NotNull(message = "appointmentEndTime  Can Not Be null")
	private String appointmentEndTime;
}
