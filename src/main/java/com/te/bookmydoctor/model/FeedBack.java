package com.te.bookmydoctor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FeedBack_Table")
public class FeedBack implements Serializable{
	@Id
	@SequenceGenerator(name = "feedback_sequence_generator",initialValue = 100,allocationSize = 1)
	@GeneratedValue(generator = "feedback_sequence_generator")
	private Integer feedbackId;
	private Double rating;
	private String reviews;
	@OneToOne
	@JoinColumn(name = "patientId")
	private Patient patient;
	@ManyToOne
	@JoinColumn(name = "doctorId")
	private Doctor doctor;
	public FeedBack(Double rating, String reviews, Patient patient, Doctor doctor) {
		this.rating = rating;
		this.reviews = reviews;
		this.patient = patient;
		this.doctor = doctor;
	}
	
	
}
