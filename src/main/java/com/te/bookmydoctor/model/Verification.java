package com.te.bookmydoctor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Verification implements Serializable {
	@Id
	@SequenceGenerator(name = "verification_sequence_generator",initialValue = 100,allocationSize = 1)
	@GeneratedValue(generator = "verification_sequence_generator")
	private Integer verificationId;
	@OneToOne
	private Doctor doctor;
	@OneToOne
	private Patient patient;
	@OneToOne
	private Admin admin;
}
