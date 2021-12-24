package com.te.bookmydoctor.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable{
	@NotBlank(message = "street Name Can Not Be Empty")
	@NotNull(message = "street Name Can Not Be null")
	private String street;
	@NotBlank(message = "area Name Can Not Be Empty")
	@NotNull(message = "area Name Can Not Be null")
	private String area;
	@NotBlank(message = "city Name Can Not Be Empty")
	@NotNull(message = "city Name Can Not Be null")
	private String city;
	private Integer zipCode;
	@NotBlank(message = "state Name Can Not Be Empty")
	@NotNull(message = "state Name Can Not Be null")
	private String state;
	@NotBlank(message = "country Name Can Not Be Empty")
	@NotNull(message = "country Name Can Not Be null")
	private String country;
}
