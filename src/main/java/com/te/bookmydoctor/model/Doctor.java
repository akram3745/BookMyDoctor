package com.te.bookmydoctor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Doctor_details",uniqueConstraints = @UniqueConstraint(columnNames = { "email", "mobileNumber" }))
public class Doctor implements Serializable {
	@Id
	@SequenceGenerator(name = "doctor_sequence_generator",initialValue = 100,allocationSize = 1)
	@GeneratedValue(generator = "doctor_sequence_generator")
	private Integer doctorId;
//	@NotNull(message = "First name cannot be null")
	private String firstName;
//	@NotNull(message = "Last name cannot be null")
	private String lastName;
//	@Email(message = "Enter the Email")
	private String email;
//	@Pattern(regexp = "^[0-9]{10}",message = "10 digit Mobile Number should be enter")
	private Long mobileNumber;
//	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "a digit must occur at least once/a lower case letter must occur at least once/an upper case letter must occur at least once/a special character must occur at least once/no whitespace allowed in the entire string/ at least 8 characters")
	private String password;
//	@NotNull(message = "Gender cannot be null")
	private String gender;
	private Double averageRating;
	private Integer isDeleted;
	private Integer isVerified;
	
	@OneToMany(mappedBy = "doctor")
	private List<Language> language;
	
	@OneToMany(mappedBy = "doctor")
	private List<Address> addresses;
	
	@OneToOne(mappedBy = "doctor")
	private Qualification qualification;
	
	@OneToOne(mappedBy = "doctor")
	private Specialization specialization;
	
	@OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
	private List<Appointment> appointments;
	
	@OneToMany(mappedBy = "doctor")
	private List<FeedBack> feedBacks;
	
	@OneToOne(mappedBy = "doctor")
	private Verification verification;
	
	@ManyToMany(mappedBy = "doctor")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Role> roles;

	public Doctor(String firstName, String lastName, String email, Long mobileNumber, String password, String gender,
			Double averageRating,Integer isDeleted,Integer isVerified) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.gender = gender;
		this.averageRating = averageRating;
		this.isDeleted = isDeleted;
		this.isVerified = isVerified;
	}

	@Override
	public String toString() {
		return "Doctor [doctorId=" + doctorId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", mobileNumber=" + mobileNumber + ", password=" + password + ", gender=" + gender
				+ ", averageRating=" + averageRating + ", isDeleted=" + isDeleted + ", isVerified=" + isVerified + "]";
	}

	
	
	
}
