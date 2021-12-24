package com.te.bookmydoctor.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.te.bookmydoctor.dto.AddressDto;
import com.te.bookmydoctor.dto.AppointmentFetchDto;
import com.te.bookmydoctor.dto.AppointmentTakenTimeFetchDto;
import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.dto.LanguageDto;
import com.te.bookmydoctor.model.Address;
import com.te.bookmydoctor.model.Appointment;
import com.te.bookmydoctor.model.AppointmentTakenTime;
import com.te.bookmydoctor.model.Doctor;
import com.te.bookmydoctor.model.FeedBack;
import com.te.bookmydoctor.model.Language;
import com.te.bookmydoctor.model.Qualification;
import com.te.bookmydoctor.model.Role;
import com.te.bookmydoctor.model.Specialization;
import com.te.bookmydoctor.repository.AddressRepository;
import com.te.bookmydoctor.repository.DoctorRepository;
import com.te.bookmydoctor.repository.LanguageRepository;
import com.te.bookmydoctor.repository.QualificationRepository;
import com.te.bookmydoctor.repository.RoleRepository;
import com.te.bookmydoctor.repository.SpecializationRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private AddressRepository AddressRepository;
	@Autowired
	private QualificationRepository QualificationRepository;
	@Autowired
	private LanguageRepository LanguageRepository;
	@Autowired
	private SpecializationRepository SpecializationRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	private Integer doctorId;
	private String email;
	private Long mobileNumber;

//	@Override
//	public ResponseMessage saveDoctor(DoctorDto doctorDto) {
//		if (doctorDto != null) {
//			Doctor doctor = new Doctor(doctorDto.getFirstName(), doctorDto.getLastName(), doctorDto.getEmail(),
//					doctorDto.getMobileNumber(), passwordEncoder.encode(doctorDto.getPassword()), doctorDto.getGender(),
//					doctorDto.getAverageRating(), 0, 0);
//			List<AddressDto> addresses = doctorDto.getAddresses();
//
//			Qualification qualification = new Qualification(doctorDto.getDegreeName(), doctorDto.getInstitutionName(),
//					doctorDto.getPassingYear(), doctorDto.getHighestQualificationCertificate(),
//					doctorDto.getMedicalRegistrationNumber(), doctor);
//			List<LanguageDto> languages = doctorDto.getLanguages();
//
//			Specialization specialization = new Specialization(doctorDto.getSpeciality(), doctor);
//			List<Doctor> doctors = new ArrayList<>();
//			List<Role> roles = new ArrayList<>();
//			doctors.add(doctor);
//			Role role = new Role("DOCTOR", doctors);
//			roles.add(role);
//			doctor.setRoles(roles);
//			for (Doctor doctor0 : doctors) {
//				doctorRepository.save(doctor0);
//			}
//			for (AddressDto addressDto : addresses) {
//				Address address = new Address(addressDto.getStreet(), addressDto.getArea(), addressDto.getCity(),
//						addressDto.getZipCode(), addressDto.getState(), addressDto.getCountry(), doctor);
//				AddressRepository.save(address);
//			}
//			QualificationRepository.save(qualification);
//			for (LanguageDto languageDto : languages) {
//				Language language = new Language(languageDto.getLanguage(), doctor);
//				LanguageRepository.save(language);
//			}
//			SpecializationRepository.save(specialization);
//			for (Role role0 : roles) {
//				roleRepository.save(role0);
//			}
//			String pass = "";
//			for (int i = 0; i < doctorDto.getPassword().length(); i++) {
//				pass = pass + "*";
//			}
//			doctorDto.setPassword(pass);
//			log.info(doctorDto.getFirstName() + " Registraion Done Successfully And Need to Verify the Details ");
//			return new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					doctorDto.getFirstName() + " Your Registraion Done Successfully And Wait For The Verification",
//					doctorDto);
//		} else {
//			log.error("Doctor Registration Was Failed");
//			throw new DoctorInvalidDetailsException("Your Registration Was Failed ,Try Again Once");
//		}
//	}
//
//	@Override
//	public ResponseMessage seeAppointment(java.sql.Date date) {
//		Doctor doctor = doctorRepository.getById(doctorId);
//		if (doctor != null) {
//			AppointmentFetchDto appointment0 = new AppointmentFetchDto();
//			List<AppointmentTakenTimeFetchDto> takenTimeDto = new ArrayList<>();
//			List<Appointment> appointments = doctor.getAppointments();
//			for (Appointment appointment : appointments) {
//				if (appointment.getAppointmentDate().equals(date)) {
//					List<AppointmentTakenTime> appointmentTakenTime = appointment.getAppointmentTakenTime();
//					for (AppointmentTakenTime appointmentTaken : appointmentTakenTime) {
//						AppointmentTakenTimeFetchDto timeDto = new AppointmentTakenTimeFetchDto(
//								appointmentTaken.getPatient().get(0).getFirstName() + " "
//										+ appointmentTaken.getPatient().get(0).getLastName(),
//								appointmentTaken.getAppointmentTakenTime(), appointmentTaken.getAppointmentEndTime());
//						takenTimeDto.add(timeDto);
//					}
//					appointment0.setAppointmentDate(date);
//					appointment0.setAppointmentTakenTimeDtos(takenTimeDto);
//				}
//			}
//			log.info("Appointment Details Sucessfully Fetched for the Doctor " + doctor.getFirstName() + " "
//					+ doctor.getLastName());
//			return new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Appointment Details Sucessfully Fetched for the Doctor " + doctor.getFirstName() + " "
//							+ doctor.getLastName(),
//					appointment0);
//		} else {
//			log.error("Doctor Details Not Find In The Database To Check the Appointment");
//			throw new DoctorInvalidDetailsException("Doctor Details Not Find In The Database To Check the Appointment");
//		}
//	}
//
//	@Override
//	public ResponseMessage seeFeedBack() {
//		Doctor doctor = doctorRepository.getById(doctorId);
//		if (doctor != null) {
//			List<FeedBack> feedBacks = doctor.getFeedBacks();
//			List<FeedbackDto> feedbackDtoList = feedBacks.stream()
//					.sorted(Comparator.comparingInt(FeedBack::getFeedbackId).reversed())
//					.map(feedBack -> new FeedbackDto(feedBack.getRating(), feedBack.getReviews()))
//					.collect(Collectors.toList());
//			log.info("Doctor Sucessfully Fetching The FeedBack Details");
//			return new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					doctor.getFirstName() + " " + doctor.getLastName() + " Sucessfully Fetching The FeedBack Details",
//					feedbackDtoList);
//		} else {
//			log.error("Doctor Deatils Not Find The Database to Fetch The Feedback");
//			throw new DoctorInvalidDetailsException("Doctor Deatils Not Find The Database to Fetch The Feedback");
//		}
//	}
//
//	@Override
//	public ResponseMessage removeDoctor(Long mobileNumber) {
//		Doctor doctor = doctorRepository.findByMobileNumber(mobileNumber);
//		if (doctor != null) {
//			doctor.setIsDeleted(1);
//			doctorRepository.save(doctor);
//			log.info(" Doctor " + doctor.getFirstName() + " " + doctor.getLastName() + " Deleted Successfully");
//			return new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					" Doctor " + doctor.getFirstName() + " " + doctor.getLastName() + " Deleted Successfully",
//					doctor.getIsDeleted());
//		} else {
//			log.error("Doctor Deatils Not Find The DataBase To Delete");
//			throw new DoctorInvalidDetailsException("Doctor Deatils Not Find The DataBase To Delete");
//		}
//	}
//
//	@Override
//	public ResponseMessage checkStatus() {
//		Doctor doctor = doctorRepository.getById(doctorId);
//		if (doctor != null) {
//			log.info(" Doctor " + doctor.getFirstName() + " " + doctor.getLastName()+" Checked is Status Sucessfully");
//			return new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					" Doctor " + doctor.getFirstName() + " " + doctor.getLastName()+" Checked is Status Sucessfully",
//					doctor.getIsVerified());
//		}else {
//			log.error("Doctor Not Present In The Databse To Check the Status");
//			throw new DoctorInvalidDetailsException("Doctor Not Present In The Databse To Check the Status");
//		}
//	}
	/**
	 * Doctor Registration
	 */
	@Override
	public DoctorDto saveDoctor(DoctorDto doctorDto) {
		Doctor doctor = new Doctor(doctorDto.getFirstName(), doctorDto.getLastName(), doctorDto.getEmail(),
				doctorDto.getMobileNumber(), passwordEncoder.encode(doctorDto.getPassword()), doctorDto.getGender(),
				doctorDto.getAverageRating(), 0, 0);
		List<AddressDto> addresses = doctorDto.getAddresses();

		Qualification qualification = new Qualification(doctorDto.getDegreeName(), doctorDto.getInstitutionName(),
				doctorDto.getPassingYear(), doctorDto.getHighestQualificationCertificate(),
				doctorDto.getMedicalRegistrationNumber(), doctor);
		List<LanguageDto> languages = doctorDto.getLanguages();

		Specialization specialization = new Specialization(doctorDto.getSpeciality(), doctor);
		List<Doctor> doctors = new ArrayList<>();
		List<Role> roles = new ArrayList<>();
		doctors.add(doctor);
		Role role = new Role("DOCTOR", doctors);
		roles.add(role);
		doctor.setRoles(roles);
		for (Doctor doctor0 : doctors) {
			doctorRepository.save(doctor0);
		}
		for (AddressDto addressDto : addresses) {
			Address address = new Address(addressDto.getStreet(), addressDto.getArea(), addressDto.getCity(),
					addressDto.getZipCode(), addressDto.getState(), addressDto.getCountry(), doctor);
			AddressRepository.save(address);
		}
		QualificationRepository.save(qualification);
		for (LanguageDto languageDto : languages) {
			Language language = new Language(languageDto.getLanguage(), doctor);
			LanguageRepository.save(language);
		}
		SpecializationRepository.save(specialization);
		for (Role role0 : roles) {
			roleRepository.save(role0);
		}
		String password = "";
		for (int i = 0; i < doctorDto.getPassword().length(); i++) {
			password = password + "*";
		}
		doctorDto.setPassword(password);
		return doctorDto;
	}

	/**
	 * For viewing all appointments booked on a particular date
	 */
	@Override
	public AppointmentFetchDto seeAppointment(java.sql.Date date) {
		Doctor doctor = doctorRepository.getById(doctorId);
		AppointmentFetchDto appointment0 = new AppointmentFetchDto();
		List<AppointmentTakenTimeFetchDto> takenTimeDto = new ArrayList<>();
		List<Appointment> appointments = doctor.getAppointments();
		for (Appointment appointment : appointments) {
			if (appointment.getAppointmentDate().equals(date)) {
				List<AppointmentTakenTime> appointmentTakenTime = appointment.getAppointmentTakenTime();
				for (AppointmentTakenTime appointmentTaken : appointmentTakenTime) {
					AppointmentTakenTimeFetchDto timeDto = new AppointmentTakenTimeFetchDto(
							appointmentTaken.getPatient().get(0).getFirstName() + " "
									+ appointmentTaken.getPatient().get(0).getLastName(),
							appointmentTaken.getAppointmentTakenTime(), appointmentTaken.getAppointmentEndTime());
					takenTimeDto.add(timeDto);
				}
				appointment0.setAppointmentDate(date);
				appointment0.setAppointmentTakenTimeDtos(takenTimeDto);
			}
		}
		return appointment0;
	}

	/**
	 * For viewing the feedback list
	 */
	@Override
	public List<FeedbackDto> seeFeedBack() {
		Doctor doctor = doctorRepository.getById(doctorId);
		List<FeedBack> feedBacks = doctor.getFeedBacks();
		return feedBacks.stream().sorted(Comparator.comparingInt(FeedBack::getFeedbackId).reversed())
				.map(feedBack -> new FeedbackDto(feedBack.getRating(), feedBack.getReviews()))
				.collect(Collectors.toList());

	}

	/**
	 * For removing a particular doctor by mobile number
	 */
	@Override
	public Integer removeDoctor(Long mobileNumber) {
		Doctor doctor = doctorRepository.findByMobileNumber(mobileNumber);
		if (doctor != null) {
			doctor.setIsDeleted(1);
			doctorRepository.save(doctor);
			return doctor.getIsDeleted();
		}
		return 0;
	}

	/**
	 * Check verification status
	 */
	@Override
	public Integer checkStatus() {
		Doctor doctor = doctorRepository.getById(doctorId);
		return doctor.getIsVerified();
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public Long getMobileNumber() {
		return mobileNumber;
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

}
