package com.te.bookmydoctor.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto implements Serializable{
	@NotBlank(message = "language Name Can Not Be Empty")
	@NotNull(message = "language Name Can Not Be null")
	private String language;
}
