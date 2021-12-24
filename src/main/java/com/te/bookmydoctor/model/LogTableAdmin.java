package com.te.bookmydoctor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("serial")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LogTableAdmin implements Serializable {
	@Id
	@SequenceGenerator(name = "log_admin_sequence_generator",initialValue = 100,allocationSize = 1)
	@GeneratedValue(generator = "log_admin_sequence_generator")
	private Integer logId;
	private String doctorName;
	private String action;
	private String message;
	@ManyToOne
	@JoinColumn(name = "adminId")
	private Admin admin;
}
