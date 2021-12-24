package com.te.bookmydoctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorFetchAllDto {
	private String doctorName;
	private Double AverageRating;
	private Integer isDeleted;
	private Integer isVerified;
	private Long mobileNumber;
}
