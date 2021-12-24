package com.te.bookmydoctor.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.bookmydoctor.model.Appointment;

@Repository
public interface AppoinmentRepository extends JpaRepository<Appointment, Integer> {
	List<Appointment> findByAppointmentDate(Date appointmentDate);
}
