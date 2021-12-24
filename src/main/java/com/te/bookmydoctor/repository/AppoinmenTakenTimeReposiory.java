package com.te.bookmydoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.bookmydoctor.model.AppointmentTakenTime;

@Repository
public interface AppoinmenTakenTimeReposiory extends JpaRepository<AppointmentTakenTime, Integer> {

}
