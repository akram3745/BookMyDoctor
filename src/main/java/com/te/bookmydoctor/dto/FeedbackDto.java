package com.te.bookmydoctor.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
	
	@Range(min = 1,max = 10,message =  "Rating must be in given range")
	private Double rating;
	@NotBlank(message = "reviews Name Can Not Be Empty")
	@NotNull(message = "reviews Name Can Not Be null")
	private String reviews;
}
