package com.te.bookmydoctor.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Admin_Table",uniqueConstraints = @UniqueConstraint(columnNames = { "email", "mobileNumber" }))
public class Admin {
	@Id
	@SequenceGenerator(name = "admin_sequence_generator",initialValue = 100,allocationSize = 1)
	@GeneratedValue(generator = "admin_sequence_generator")
	private Integer adminId;
	@NotNull(message = "First name cannot be null")
	private String firstName;
	@NotNull(message = "Last name cannot be null")
	private String lastName;
	@Email(message = "Enter the email")
	private String email;
	@Pattern(regexp = "(?=.*[0-9])(?=\\S+$).{10}",message = "10 digit Mobile Number should be enter")
	private Long mobileNumber;
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "a digit must occur at least once/a lower case letter must occur at least once/an upper case letter must occur at least once/a special character must occur at least once/no whitespace allowed in the entire string/ at least 8 characters")
	private String password;
	
	@OneToOne(mappedBy = "admin")
	private Verification Verification;
	@ManyToMany(mappedBy = "admin")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Role> roles;
	@OneToMany(mappedBy = "admin")
	private List<LogTableAdmin> admiTableAdmins;
	@OneToMany(mappedBy = "admin")
	private List<LogTableDoctor> logTableDoctors;
	@OneToMany(mappedBy = "admin")
	private List<LogTablePatient> logTablePatients;
}
