package com.te.bookmydoctor.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto implements Serializable {

	@NotBlank(message = "First Name Can Not Be Empty")
	@NotNull(message = "First Name Can Not Be null")
	private String firstName;
	@NotBlank(message = "last Name Can Not Be Empty")
	@NotNull(message = "last Name Can Not Be null")
	private String lastName;
	@Email(message = "Enter the valid Email")
	private String email;
//	@Range(min = 8, max = 10, message = "Phone number should be more than 8 and less then 10")
	private Long mobileNumber;
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "a digit must occur at least once/a lower case letter must occur at least once/an upper case letter must occur at least once/a special character must occur at least once/no whitespace allowed in the entire string/ at least 8 characters")
	private String password;
	@NotBlank(message = "gender Name Can Not Be Empty")
	@NotNull(message = "gender Name Can Not Be null")
	private String gender;
//	@Range(min = 1, max = 10, message = "Rating must be in given range")
	private Double averageRating;

	// locatttion
	private List<AddressDto> addresses;

	// language
	private List<LanguageDto> languages;

	// qualification
	@NotBlank(message = "degree Name Can Not Be Empty")
	@NotNull(message = "degree Name Can Not Be null")
	private String degreeName;
	@NotBlank(message = "institution Name Can Not Be Empty")
	@NotNull(message = "institution Name Can Not Be null")
	private String institutionName;
	@Positive(message = "passing Year cannot be negative")
	private Integer passingYear;
	@NotBlank(message = "highestQualificationCertificate Name Can Not Be Empty")
	@NotNull(message = "highestQualificationCertificate Name Can Not Be null")
	private String highestQualificationCertificate;
	@NotBlank(message = "medicalRegistrationNumber  Can Not Be Empty")
	@NotNull(message = "medicalRegistrationNumber  Can Not Be null")
	private String medicalRegistrationNumber;

//	specialization
	@NotBlank(message = "speciality Name Can Not Be Empty")
	@NotNull(message = "speciality Name Can Not Be null")
	private String speciality;

	public DoctorDto(@NotNull(message = "First Name Can Not Be null") String firstName,
			@NotNull(message = "last Name Can Not Be null") String lastName, String email, Long mobileNumber,
			@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "a digit must occur at least once/a lower case letter must occur at least once/an upper case letter must occur at least once/a special character must occur at least once/no whitespace allowed in the entire string/ at least 8 characters") String password,
			@NotNull(message = "gender Name Can Not Be null") String gender, Double averageRating,
			@NotNull(message = "degree Name Can Not Be null") String degreeName,
			@NotNull(message = "institution Name Can Not Be null") String institutionName,
			@Positive(message = "passing Year cannot be negative") Integer passingYear,
			@NotNull(message = "highestQualificationCertificate Name Can Not Be null") String highestQualificationCertificate,
			@NotNull(message = "medicalRegistrationNumber  Can Not Be null") String medicalRegistrationNumber,
			@NotNull(message = "speciality Name Can Not Be null") String speciality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.gender = gender;
		this.averageRating = averageRating;
		this.degreeName = degreeName;
		this.institutionName = institutionName;
		this.passingYear = passingYear;
		this.highestQualificationCertificate = highestQualificationCertificate;
		this.medicalRegistrationNumber = medicalRegistrationNumber;
		this.speciality = speciality;
	}

	public DoctorDto(String email,
			 String password) {
		this.email = email;
		this.password = password;
	}

//	public DoctorDto(String firstName, String lastName, String email, Long mobileNumber, String password, String gender,
//			Double averageRating) {
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.mobileNumber = mobileNumber;
//		this.password = password;
//		this.gender = gender;
//		this.averageRating = averageRating;
//	}
//
//	public DoctorDto(ArrayList<AddressDto> addresses) {
//		this.addresses = addresses;
//	}
//
//	public DoctorDto(String degree_name, String institutionName, Integer passingYear,
//			String highestQualificationCertificate, String medicalRegistrationNumber) {
//		this.degreeName = degree_name;
//		this.institutionName = institutionName;
//		this.passingYear = passingYear;
//		this.highestQualificationCertificate = highestQualificationCertificate;
//		this.medicalRegistrationNumber = medicalRegistrationNumber;
//	}
//
//	public DoctorDto(String speciality) {
//		this.speciality = speciality;
//	}
//
//	public DoctorDto(List<LanguageDto> languages) {
//		super();
//		this.languages = languages;
//	}
//	
//	
}
