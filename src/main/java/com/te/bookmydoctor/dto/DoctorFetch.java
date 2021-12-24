package com.te.bookmydoctor.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Data
@NoArgsConstructor
public class DoctorFetch implements Serializable{
	private Integer doctorId;
	private String name;
	private Double averageRating;
	public DoctorFetch(Integer doctor_id, String name, Double average_rating) {
		this.doctorId = doctor_id;
		this.name = name;
		this.averageRating = average_rating;
	}
	public DoctorFetch(Integer doctor_id) {
		this.doctorId = doctor_id;
	}
}
