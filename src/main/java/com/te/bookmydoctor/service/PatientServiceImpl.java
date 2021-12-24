package com.te.bookmydoctor.service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.te.bookmydoctor.dto.AppointmentDto;
import com.te.bookmydoctor.dto.AppointmentTakenTimeDto;
import com.te.bookmydoctor.dto.DoctorFetch;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.dto.PatientAppointmentStatusDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.dto.SearchDoctorDto;
import com.te.bookmydoctor.exception.PatientInvalidDetailsException;
import com.te.bookmydoctor.model.Appointment;
import com.te.bookmydoctor.model.AppointmentTakenTime;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.FeedBack;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.model.Role;
import com.te.bookmydoctor.repository.AppoinmenTakenTimeReposiory;
import com.te.bookmydoctor.repository.AppoinmentRepository;
import com.te.bookmydoctor.repository.DoctorRepository;
import com.te.bookmydoctor.repository.FeedBackRepository;
import com.te.bookmydoctor.repository.PatientRepository;
import com.te.bookmydoctor.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AppoinmenTakenTimeReposiory appoinmenTakenTimeReposiory;
	@Autowired
	private AppoinmentRepository appoinmentReposiory;
	@Autowired
	private FeedBackRepository feedBackRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailService javaMailService;
	private List<Patient> patients = new ArrayList<>();
	private List<Role> roles = new ArrayList<>();
	private Integer patientId;
	private Long OTP;
	private Long mobileNumber;
	private String email;

	/**
	 * For searching all doctors
	 */
	@Override
	public List<DoctorFetch> searchDoctor(SearchDoctorDto searchDoctorDto) {
		List<Doctor> doctors = doctorRepository.findAll();
		List<DoctorFetch> doctorList = new ArrayList<>();
		List<DoctorFetch> doctorList1 = new ArrayList<>();
		List<DoctorFetch> doctorList0 = new ArrayList<>();
		List<Doctor> allDoctors = doctors.stream()
				.filter(doctor -> doctor.getIsDeleted() == 0 && doctor.getIsVerified() == 1)
				.collect(Collectors.toList());

		for (Doctor doctor : allDoctors) {
			List<DoctorFetch> collect = doctor.getAddresses().stream()
					.filter(address -> address.getCountry().equalsIgnoreCase(searchDoctorDto.getCountry())
							&& address.getState().equalsIgnoreCase(searchDoctorDto.getState())
							&& address.getCity().equalsIgnoreCase(searchDoctorDto.getCity())
							&& address.getArea().equalsIgnoreCase(searchDoctorDto.getArea()))
					.map(address -> new DoctorFetch(address.getDoctor().getDoctorId())).collect(Collectors.toList());
			doctorList.addAll(collect);
		}
		for (DoctorFetch doctorDto : doctorList) {
			List<DoctorFetch> collect = doctorRepository.findByDoctorId(doctorDto.getDoctorId()).getLanguage().stream()
					.filter(language -> language.getLanguage().equalsIgnoreCase(searchDoctorDto.getLanguage()))
					.map(address -> new DoctorFetch(address.getDoctor().getDoctorId())).collect(Collectors.toList());
			doctorList1.addAll(collect);
		}
		for (DoctorFetch doctorDto : doctorList1) {
			Doctor doctor = doctorRepository.findByDoctorId(doctorDto.getDoctorId());
			if (doctor.getGender().equalsIgnoreCase(searchDoctorDto.getGender())
					&& doctor.getSpecialization().getSpeciality().equalsIgnoreCase(searchDoctorDto.getSpecialty())) {
				doctorList0.add(new DoctorFetch(doctor.getDoctorId(),
						doctor.getFirstName() + " " + doctor.getLastName(), doctor.getAverageRating()));
			}
		}
		return doctorList0;
	}

	/**
	 * Patient Registration
	 */
	@SuppressWarnings("unused")
	@Override
	public PatientDto savePatient(PatientDto patientDto) {
		Patient patient = new Patient(patientDto.getFirstName(), patientDto.getLastName(), patientDto.getEmail(),
				passwordEncoder.encode(patientDto.getPassword()), patientDto.getGender(), patientDto.getMobileNumber(),
				0, 0);
		this.mobileNumber = patient.getMobileNumber();
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		role.setRole("PATIENT");
		roles.add(role);
		List<Patient> patients = new ArrayList<>();
		patients.add(patient);
		role.setPatient(patients);
		patient.setRoles(roles);
		this.patients.clear();
		this.patients.addAll(patients);
		this.roles.clear();
		this.roles.addAll(roles);
		String pass = "";
		for (int i = 0; i < patientDto.getPassword().length(); i++) {
			pass = pass + "*";
		}
		patientDto.setPassword(pass);
		this.email = patientDto.getEmail();
		this.OTP = ThreadLocalRandom.current().nextLong(100000l, 1000000l);
		String message = "Thanks for Registring with Book My Doctor\nVerify Your Account and Your OTP Is : " + this.OTP;
		try {
			javaMailService.sendMail(patient.getEmail(), "Verify Your Email", message);
		} catch (MessagingException e) {
			throw new PatientInvalidDetailsException("Something Went wrong try Again!!!");
		}
		return patientDto;
	}

	/**
	 * Book Appointment
	 */
	@Override
	public AppointmentDto patientAppointment(AppointmentDto appointmentDto) {
		Doctor doctor = doctorRepository.findByMobileNumber(appointmentDto.getDoctorMobileNumber());
		Patient patient = patientRepository.findByPatientId(patientId);
		List<Appointment> appointments0 = appoinmentReposiory
				.findByAppointmentDate(appointmentDto.getAppointmentDate());
		for (Appointment appointment : appointments0) {
			for (AppointmentTakenTime takenTime : appointment.getAppointmentTakenTime()) {
				if (takenTime.getAppointmentTakenTime()
						.contains(appointmentDto.getAppointmentTakenTimeDtos().get(0).getAppointmentTakenTime())) {
					log.error("Appointment is already booked by some other Person at that Time");
					throw new PatientInvalidDetailsException(
							"Appointment is already booked by some other Person at that Time");
				}
			}
		}

		List<AppointmentTakenTime> appointmentTakenTimes = new ArrayList<>();
		List<Appointment> appointments = new ArrayList<>();
		List<Patient> patients = new ArrayList<>();
		java.sql.Date date = appointmentDto.getAppointmentDate();
		DayOfWeek dayOfWeek = date.toLocalDate().getDayOfWeek();
		patients.add(patient);
		Appointment appointment = new Appointment(date, dayOfWeek.name(), doctor);
		List<AppointmentTakenTimeDto> appointmentTakenTimeDtos = appointmentDto.getAppointmentTakenTimeDtos();
		for (AppointmentTakenTimeDto appointmentTakenTimeDto : appointmentTakenTimeDtos) {
			AppointmentTakenTime appointmentTakenTime = new AppointmentTakenTime(
					appointmentTakenTimeDto.getAppointmentTakenTime(), appointmentTakenTimeDto.getAppointmentEndTime(),
					"0", patients);
			appointmentTakenTimes.add(appointmentTakenTime);
			appointment.setAppointmentTakenTime(appointmentTakenTimes);
			appointmentTakenTime.setAppointment(appointment);
			patient.setAppointmentTakenTime(appointmentTakenTimes);
			appoinmenTakenTimeReposiory.save(appointmentTakenTime);
		}
		appointments.add(appointment);
		doctor.setAppointments(appointments);
		appoinmentReposiory.save(appointment);
		log.info("Appointment Is Fixed");
		return appointmentDto;
	}

	/**
	 * For checking appointment status by mobile number
	 */
	@Override
	public PatientAppointmentStatusDto patientAppointmentStatus() {
		Patient patient = patientRepository.findByMobileNumber(this.mobileNumber);
		List<AppointmentDto> appointmentDtos = new ArrayList<>();
		List<AppointmentTakenTime> appointmentTakenTime = patient.getAppointmentTakenTime();
		for (AppointmentTakenTime appointmentTakenTime2 : appointmentTakenTime) {
			List<AppointmentTakenTimeDto> appointmentDtos2 = new ArrayList<>();
			java.sql.Date appointmentDate = appointmentTakenTime2.getAppointment().getAppointmentDate();
			AppointmentTakenTimeDto takenTimeDto = new AppointmentTakenTimeDto(
					appointmentTakenTime2.getAppointmentTakenTime(), appointmentTakenTime2.getAppointmentEndTime(),
					appointmentTakenTime2.getAppointmentStatus());
			Long mobileNumber2 = appointmentTakenTime2.getAppointment().getDoctor().getMobileNumber();
			appointmentDtos2.add(takenTimeDto);
			AppointmentDto dto = new AppointmentDto(mobileNumber2, appointmentDate, appointmentDtos2);
			appointmentDtos.add(dto);
		}
		String patientName = patient.getFirstName() + " " + patient.getLastName();
		Doctor doctor = appointmentTakenTime.get(0).getAppointment().getDoctor();
		String doctorName = doctor.getFirstName() + " " + doctor.getLastName();
		PatientAppointmentStatusDto statusDto = new PatientAppointmentStatusDto(doctorName, patientName,
				appointmentDtos);
		return statusDto;
	}

	/**
	 * Adding Feedback
	 */
	@Override
	public FeedbackDto saveFeedBack(FeedbackDto feedbackDto) {
		Patient patient = patientRepository.findByMobileNumber(this.mobileNumber);
		List<AppointmentTakenTime> takenTimes = appoinmenTakenTimeReposiory.findAll();
		AppointmentTakenTime takenTime = takenTimes.stream()
				.sorted(Comparator.comparingInt(AppointmentTakenTime::getAppointmentTimeId).reversed()).findFirst()
				.get();
		Doctor doctor = takenTime.getAppointment().getDoctor();
		System.out.println(takenTime.getAppointmentTimeId());
		List<FeedBack> feedBacks = new ArrayList<>();
		FeedBack feedBack = new FeedBack(feedbackDto.getRating(), feedbackDto.getReviews(), patient, doctor);
		feedBacks.add(feedBack);
		patient.setFeedBack(feedBack);
		feedBack.setPatient(patient);
		doctor.setFeedBacks(feedBacks);
		feedBack.setDoctor(doctor);
		feedBackRepository.save(feedBack);
		return feedbackDto;
	}

	/**
	 * Patient verification by OTP
	 */
	@Override
	public Patient patientVerify(Long OTP) {
		if (OTP.equals(this.OTP)) {
			for (Patient patient : patients) {
				patient.setIsVerified(1);
				patientRepository.save(patient);
			}
			for (Role role1 : roles) {
				roleRepository.save(role1);
			}
			this.OTP = 1l;
			return this.patients.get(0);
		}
		return null;
	}

	/**
	 * Request for regeneration of OTP
	 */
	@Override
	public String reSendOTP() {
		this.OTP = ThreadLocalRandom.current().nextLong(100000l, 1000000l);
		String message = "Thanks for Registring with Book My Doctor\nVerify Your Account and Your OTP Is : " + this.OTP;
		try {
			javaMailService.sendMail(this.email, "Verify Your Email", message);
		} catch (MessagingException e) {
			throw new PatientInvalidDetailsException("Patient Not Found");
		}
		return "Patient Mobile Number " + mobileNumber;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getOTP() {
		return OTP;
	}

	public void setOTP(Long oTP) {
		OTP = oTP;
	}

}
