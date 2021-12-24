package com.te.bookmydoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.bookmydoctor.model.Doctor;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
	Doctor findByMobileNumber(Long mobileNumber);
	Doctor  findByEmail(String email);
	Doctor  findByDoctorId(Integer doctorId);
}
