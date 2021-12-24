package com.te.bookmydoctor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.te.bookmydoctor.model.Admin;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.model.Role;
import com.te.bookmydoctor.repository.AdminRepository;
import com.te.bookmydoctor.repository.DoctorRepository;
import com.te.bookmydoctor.repository.PatientRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserdetailsServiceImpl implements UserDetailsService {

	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private DoctorServiceImpl doctorService;
	@Autowired
	private PatientServiceImpl serviceImpl;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Doctor doctor = doctorRepository.findByEmail(username);
		Patient patient = patientRepository.findByEmail(username);
		Admin admin = adminRepository.findByEmail(username);
		if ((doctor == null && patient == null && admin == null)) {
			log.error("Please Enter your Correct User Name");
			throw new UsernameNotFoundException("User Not Found !!!");
		} else if (admin != null) {
			log.info("Successfully Logged in " + username);
			List<Role> roles = admin.getRoles();
			List<SimpleGrantedAuthority> authorities = roles.stream()
					.map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
			return new User(admin.getEmail(), admin.getPassword(), authorities);
		}else if (doctor != null) {
			if (doctor.getIsDeleted()==1 || doctor.getIsVerified()==0) {
				log.error("Doctor Not Found !!!");
				throw new UsernameNotFoundException("Doctor Not Found !!!");
			}
			log.info("Successfully Logged in " + username);
			doctorService.setDoctorId(doctor.getDoctorId());
			doctorService.setEmail(username);
			doctorService.setMobileNumber(doctor.getMobileNumber());
			List<Role> roles = doctor.getRoles();
			List<SimpleGrantedAuthority> authorities = roles.stream()
					.map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
			return new User(doctor.getEmail(), doctor.getPassword(), authorities);
		} else if (patient != null) {
			if (patient.getIsDeleted()==1 || patient.getIsVerified()==0) {
				log.error("Patient Not Found !!!");
				throw new UsernameNotFoundException("Patient Not Found !!!");
			}
			log.info("Successfully Logged in " + username);
			serviceImpl.setPatientId(patient.getPatientId());
			serviceImpl.setEmail(username);
			serviceImpl.setMobileNumber(patient.getMobileNumber());
			List<Role> roles = patient.getRoles();
			List<SimpleGrantedAuthority> authorities = roles.stream()
					.map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
			return new User(patient.getEmail(), patient.getPassword(), authorities);
		}else
			throw new UsernameNotFoundException("Not Found !!!");
	}

}
