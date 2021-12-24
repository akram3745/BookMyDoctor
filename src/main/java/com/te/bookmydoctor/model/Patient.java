package com.te.bookmydoctor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
//@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Patients_Table",uniqueConstraints = @UniqueConstraint(columnNames = { "email", "mobileNumber" }))
public class Patient implements Serializable {
	@Id
	@SequenceGenerator(name = "patient_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "patient_sequence_generator")
	private Integer patientId;
//	@NotNull(message = "First name cannot be null")
	private String firstName;
//	@NotNull(message = "Last name cannot be null")
	private String lastName;
//	@Email(message = "Enter the Email")
	private String email;
//	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "a digit must occur at least once/a lower case letter must occur at least once/an upper case letter must occur at least once/a special character must occur at least once/no whitespace allowed in the entire string/ at least 8 characters")
	private String password;
//	@NotNull(message = "Gender cannot be null")
	private String gender;
//	@Pattern(regexp = "^[0-9]{10}",message = "10 digit Mobile Number should be enter")
	private Long mobileNumber;
	private Integer isDeleted;
	private Integer isVerified;

	@ManyToMany(mappedBy = "patient",cascade = CascadeType.ALL)
	private List<AppointmentTakenTime> appointmentTakenTime;

	@OneToOne(mappedBy = "patient")
	private FeedBack feedBack;

	@ManyToMany(mappedBy = "patient")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Role> roles;

	@OneToOne(mappedBy = "patient")
	private Verification verification;

	public Patient(String firstName, String lastName, String email, String password, String gender, Long mobileNumber,
			Integer isDeleted, Integer isVerified) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.mobileNumber = mobileNumber;
		this.isDeleted = isDeleted;
		this.isVerified = isVerified;
	}

}
