package com.te.bookmydoctor.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDoctorDto implements Serializable {
	@NotBlank(message = "Country Name Can Not Be Empty")
	@NotNull(message = "Country Name Can Not Be null")
	private String country;
	@NotBlank(message = "State Name Can Not Be Empty")
	@NotNull(message = "State Name Can Not Be null")
	private String state;
	@NotBlank(message = "City Name Can Not Be Empty")
	@NotNull(message = "City Name Can Not Be null")
	private String city;
	@NotBlank(message = "Area Name Can Not Be Empty")
	@NotNull(message = "Area Name Can Not Be null")
	private String area;
	@NotBlank(message = "Specialty Name Can Not Be Empty")
	@NotNull(message = "Specialty Name Can Not Be null")
	private String specialty;
	@NotBlank(message = "Language Name Can Not Be Empty")
	@NotNull(message = "Language Name Can Not Be null")
	private String language;
	@NotBlank(message = "gender Name Can Not Be Empty")
	@NotNull(message = "gender Name Can Not Be null")
	private String gender;
}
