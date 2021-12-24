package com.te.bookmydoctor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
	@Id
	@SequenceGenerator(name = "role_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "role_sequence_generator")
	private Integer roleId;
	private String role;
	@ManyToMany
	@JoinTable(name = "patient_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "patientId"))
	private List<Patient> patient;
	@ManyToMany
	@JoinTable(name = "doctor_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"), inverseJoinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "doctorId"))
	private List<Doctor> doctor;
	@ManyToMany
	@JoinTable(name = "admin_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"), inverseJoinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "adminId"))
	private List<Admin> admin;

	public Role(String role, List<Doctor> doctor) {
		this.role = role;
		this.doctor = doctor;
	}

//	public Role(String role, ArrayList<Patient> patient) {
//		this.role = role;
//		this.patient = patient;
//	}

}
