package com.te.bookmydoctor.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
//@Data
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Appointment_Table")
public class Appointment implements Serializable {
	@Id
	@SequenceGenerator(name = "appointment_sequence_generator",initialValue = 100,allocationSize = 1)
	@GeneratedValue(generator = "appointment_sequence_generator")
	private Integer appointmentId;
//	@PastOrPresent
	private Date appointmentDate;
//   @NotNull(message = "Day cannot be null")
	private String day;
	
	@OneToMany(mappedBy = "appointment",cascade = CascadeType.ALL)
	private List<AppointmentTakenTime> appointmentTakenTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "doctorId")
	private Doctor doctor;

	public Appointment(Date appointmentDate, String day, Doctor doctor) {
		this.appointmentDate = appointmentDate;
		this.day = day;
		this.doctor = doctor;
	}
	
}
