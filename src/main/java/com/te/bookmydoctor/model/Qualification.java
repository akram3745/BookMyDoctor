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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Qualification_Table")
public class Qualification implements Serializable {
	@Id
	@SequenceGenerator(name = "qualification_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "qualification_sequence_generator")
	private Integer qualificationId;
//	@NotNull(message = "Degree name cannot be null")
	private String degreeName;
//	@NotNull(message = "Intituition name cannot be null")
	private String institutionName;
//	@Pattern(regexp = "^[0-9].{4}", message = "Year of passing must of 4 digit")
	private Integer passingYear;
//	@NotNull(message = "Degree name cannot be null")
	private String highestQualificationCertificate;
//	@NotNull(message = "Medical Registration Number cannot be null")
	private String medicalRegistrationNumber;
	@OneToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	public Qualification(String degree_name, String institutionName, Integer passingYear,
			String highestQualificationCertificate, String medicalRegistrationNumber, Doctor doctor) {
		this.degreeName = degree_name;
		this.institutionName = institutionName;
		this.passingYear = passingYear;
		this.highestQualificationCertificate = highestQualificationCertificate;
		this.medicalRegistrationNumber = medicalRegistrationNumber;
		this.doctor = doctor;
	}
	@Override
	public String toString() {
		return "Qualification [qualificationId=" + qualificationId + ", degreeName=" + degreeName + ", institutionName="
				+ institutionName + ", passingYear=" + passingYear + ", highestQualificationCertificate="
				+ highestQualificationCertificate + ", medicalRegistrationNumber=" + medicalRegistrationNumber + "]";
	}
	
	
}
