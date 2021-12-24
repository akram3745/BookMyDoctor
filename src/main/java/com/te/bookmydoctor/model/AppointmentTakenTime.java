package com.te.bookmydoctor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AppointmentTakenTime_Table")
public class AppointmentTakenTime {
	
	@Id
	@SequenceGenerator(name = "appointmentTakenTime_sequence_generator",initialValue = 100,allocationSize = 1)
	@GeneratedValue(generator = "appointmentTakenTime_sequence_generator")
	private Integer appointmentTimeId;
//	@FutureOrPresent
	private String appointmentTakenTime;
//	@Future
	private String appointmentEndTime;
//	@NotNull(message = "Enter the Status")
	private String appointmentStatus;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointmentId")
    private Appointment appointment;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "patientId")
	private List<Patient> patient;
	public AppointmentTakenTime(String appointmentTakenTime, String appointmentEndTime, String appointmentStatus,
			List<Patient> patient) {
		super();
		this.appointmentTakenTime = appointmentTakenTime;
		this.appointmentEndTime = appointmentEndTime;
		this.appointmentStatus = appointmentStatus;
		this.patient = patient;
	}
}
