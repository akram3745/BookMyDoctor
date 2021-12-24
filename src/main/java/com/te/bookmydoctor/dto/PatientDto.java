package com.te.bookmydoctor.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
	@NotBlank(message = "First Name Can Not Be Empty")
	@NotNull(message = "First Name Can Not Be null")
	private String firstName;
	@NotBlank(message = "Last Name Can Not Be Empty")
	@NotNull(message = "Last Name Can Not Be null")
	private String lastName;
	@Email(message = "Enter the valid Email")
	private String email;
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "a digit must occur at least once/a lower case letter must occur at least once/an upper case letter must occur at least once/a special character must occur at least once/no whitespace allowed in the entire string/ at least 8 characters")
	private String password;
	@NotBlank(message = "Last Name Can Not Be Empty")
	@NotNull(message = "Last Name Can Not Be null")
	private String gender;
	//@Size(min = 6,max = 10,message = "Phone number should be more than 6 and less then 10")
//	@Range(min = 10,max = 10,message = "Phone number should be more than 8 and less then 10")
	private Long mobileNumber;
}
