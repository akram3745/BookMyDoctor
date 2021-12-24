package com.te.bookmydoctor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "Specialization_Table")
public class Specialization implements Serializable {

	@Id
	@SequenceGenerator(name = "specialization_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "specialization_sequence_generator")
	private Integer specialization_Id;
//	@NotNull(message = "Speciality cannot be null")
	private String speciality;
	@OneToOne
	@JoinColumn(name = "doctorId")
	private Doctor doctor;

	public Specialization(String speciality, Doctor doctor) {
		this.speciality = speciality;
		this.doctor = doctor;
	}

}
