package com.te.bookmydoctor.resource;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.bookmydoctor.dto.AppointmentDto;
import com.te.bookmydoctor.dto.DoctorFetch;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.dto.PatientAppointmentStatusDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.dto.SearchDoctorDto;
import com.te.bookmydoctor.exception.DoctorInvalidDetailsException;
import com.te.bookmydoctor.exception.PatientInvalidDetailsException;
import com.te.bookmydoctor.model.Patient;
import com.te.bookmydoctor.response.Message;
import com.te.bookmydoctor.response.ResponseMessage;
import com.te.bookmydoctor.service.PatientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/patient")
@Api(value = "/api/v1/patient", tags = "Patient Resource")
public class PatientResource {
	@Autowired
	private PatientService patientService;
	
	/**
	 * For searching all doctors
	 */
	@PostMapping("/search-doctor")
	@ApiOperation(value = "Search Doctor", notes = "Search Doctor", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.ALL_DOCTORS_FETCHED),
			@ApiResponse(code = 404, message = Message.NO_DOCTOR_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> searchDoctor(@Valid @RequestBody SearchDoctorDto searchDoctorDto) {
		System.out.println(searchDoctorDto);
		List<DoctorFetch> doctor = patientService.searchDoctor(searchDoctorDto);
		if (!doctor.isEmpty()) {
			log.info(Message.VERIFY_ACCOUNT);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.DOCTOR_FETCHED, doctor);
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.NO_DOCTOR_FOUND);
		throw new DoctorInvalidDetailsException(Message.NO_DOCTOR_FOUND);

	}

	/**
	 * Patient Registration
	 */
	@PostMapping("/register-patient")
	@ApiOperation(value = "Register Patient", notes = "Register Patient", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.PATIENT_REGISTRATION_SUCCESSFUL),
			@ApiResponse(code = 404, message = Message.PATIENT_REGISTRATION_UNSUCCESSFUL),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> addPatient(@Valid @RequestBody PatientDto patientDto) {
		PatientDto patient = patientService.savePatient(patientDto);
		if (patient != null) {
			log.info(Message.PATIENT_REGISTRATION_SUCCESSFUL);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.VERIFY_ACCOUNT, patient.getEmail());
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.PATIENT_REGISTRATION_UNSUCCESSFUL);
		throw new PatientInvalidDetailsException(Message.PATIENT_REGISTRATION_UNSUCCESSFUL);

	}

	/**
	 * Appointment Booking
	 */
	@PostMapping("/book-appoinment")
	@ApiOperation(value = "Book Appointment", notes = "Book Appointment", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.APPOINTMENT_BOOKED),
			@ApiResponse(code = 404, message = Message.APPOINTMENT_NOT_BOOKED),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> patientAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
		AppointmentDto appointment = patientService.patientAppointment(appointmentDto);
		if (appointment != null) {
			log.info(Message.APPOINTMENT_BOOKED);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.APPOINTMENT_BOOKED, appointment);

			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.SOMETHING_WENT_WRONG);
		throw new PatientInvalidDetailsException(Message.SOMETHING_WENT_WRONG);

	}

	/**
	 * For checking appointment status
	 */
	@GetMapping("/check-appoinment")
	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.APPOINTMENT_DETAILS),
			@ApiResponse(code = 404, message = Message.APPOINTMENT_NOT_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> patientAppointmentStatus() {
		PatientAppointmentStatusDto statusDto = patientService.patientAppointmentStatus();
		if (statusDto != null) {
			log.info(Message.APPOINTMENT_STATUS);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.APPOINTMENT_STATUS, statusDto);
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.NO_PATIENT_FOUND);
		throw new PatientInvalidDetailsException(Message.NO_PATIENT_FOUND);
	}

	/**
	 * Adding feedback
	 */
	@PostMapping("/add-feedback")
	@ApiOperation(value = "Add FeedBack", notes = "Add FeedBack", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.FEEDBACK_LIST),
			@ApiResponse(code = 404, message = Message.FEEDBACK_NOT_ADDED),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> addFeedBack(@Valid @RequestBody FeedbackDto feedbackDto) {
		FeedbackDto feedBack = patientService.saveFeedBack(feedbackDto);
		if (feedBack != null) {
			log.info(Message.ADD_FEEDBACK);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.ADD_FEEDBACK, feedBack);

			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);

		}
		log.error(Message.NO_PATIENT_FOUND);
		throw new PatientInvalidDetailsException(Message.NO_PATIENT_FOUND);

	}

	/**
	 * Patient verification by OTP
	 */
	@GetMapping("/patient-verify/{OTP}")
	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.VERIFY_PATIENT),
			@ApiResponse(code = 404, message = Message.PATIENT_NOT_VERIFIED),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> patientVerify(@PathVariable("OTP") Long OTP) {
		Patient patients = patientService.patientVerify(OTP);
		if (patients != null) {
			log.info(Message.PATIENT_REGISTRATION_SUCCESSFUL);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.PATIENT_REGISTRATION_SUCCESSFUL + " " + "Patient Name is :    ",
					patients.getFirstName() + " " + patients.getLastName());

			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);

		}
		log.error(Message.SOMETHING_WENT_WRONG);
		throw new PatientInvalidDetailsException(Message.SOMETHING_WENT_WRONG);

	}

	/**
	 * Request for regeneration of OTP
	 */
	@GetMapping("/resendotp")
	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.OTP_SENT),
			@ApiResponse(code = 404, message = Message.OTP_NOT_SENT),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> reSendOTP() {
		String reSendOTP = patientService.reSendOTP();
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
				Message.OTP_DETAILS, reSendOTP);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

//	@PostMapping("/search-doctor")
//	@ApiOperation(value = "Search Doctor", notes = "Search Doctor", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Doctor Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Doctor Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> searchDoctor(@RequestBody SearchDoctorDto searchDoctorDto) {
//		List<DoctorFetch> doctorList = patientService.searchDoctor(searchDoctorDto);
//		log.info("Doctors Fetched Sucessfully As Per Requirement");
//		return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//				" Doctors Fetched Sucessfully As Per Requirement", doctorList), HttpStatus.OK);
//	}

//	@PostMapping("/register-patient")
//	@ApiOperation(value = "Register Patient", notes = "Register Patient", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Patient Details was Registered Successfully.."),
//			@ApiResponse(code = 404, message = "Patient was Not Registered!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> addPatient(@RequestBody PatientDto patientDto) {
//		PatientDto patientDto1 = patientService.savePatient(patientDto);
//		if (patientDto1!=null) {
//			log.info("Verify Your Account For Successfull Registration");
//			 ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Verify Your Account For Successfull Registration", patientDto1);
//			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Patient Registration was Failed");
//		throw new PatientInvalidDetailsException("Your Registration was Failed ,Try Again Once");
//	}
//
//	@PostMapping("/book-appoinment")
//	@ApiOperation(value = "Book Appointment", notes = "Book Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Booked Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment Not Booked!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> patientAppointment(@RequestBody AppointmentDto appointmentDto) {
//		ResponseMessage responseMessage = patientService.patientAppointment(appointmentDto);
//		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//
//	}
//
//	@GetMapping("/check-appoinment")
//	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment was Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> patientAppointmentStatus() {
//		ResponseMessage responseMessage = patientService.patientAppointmentStatus();
//		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//
//	}
//
//	@PostMapping("/add-feedback")
//	@ApiOperation(value = "Add FeedBack", notes = "Add FeedBack", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "FeedBack Details Added Successfully.."),
//			@ApiResponse(code = 404, message = "FeedBack Not Added!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> addFeedBack(@RequestBody FeedbackDto feedbackDto) {
//		ResponseMessage responseMessage = patientService.saveFeedBack(feedbackDto);
//		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//
//	}
//
//	@GetMapping("/patient-verify/{OTP}")
//	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment was Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> patientVerify(@PathVariable("OTP") Long OTP) {
//		ResponseMessage responseMessage = patientService.patientVerify(OTP);
//		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//	}
//
//	@GetMapping("/resendotp")
//	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment was Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> reSendOTP() {
//		ResponseMessage responseMessage = patientService.reSendOTP();
//		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//	}
//
//	/**
//	 * For searching all doctors
//	 */
//	@PostMapping("/search-doctor")
//	@ApiOperation(value = "Search Doctor", notes = "Search Doctor", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Doctor Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Doctor Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> searchDoctor(@Valid @RequestBody SearchDoctorDto searchDoctorDto) {
//		System.out.println(searchDoctorDto);
//		List<DoctorFetch> doctor = patientService.searchDoctor(searchDoctorDto);
//		if (!doctor.isEmpty()) {
//			log.info("Verify Your Account For Successfull Registration");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					" Doctors Fetched Sucessfully As Per Requirement", doctor);
//			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Doctor doesnot exist");
//		throw new DoctorInvalidDetailsException("Doctor Doesnot Exist");
//
//	}
//
//	/**
//	 * Patient Registration
//	 */
//	@PostMapping("/register-patient")
//	@ApiOperation(value = "Register Patient", notes = "Register Patient", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Patient Details was Registered Successfully.."),
//			@ApiResponse(code = 404, message = "Patient was Not Registered!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> addPatient(@Valid @RequestBody PatientDto patientDto) {
//		PatientDto patient = patientService.savePatient(patientDto);
//		if (patient != null) {
//			log.info("Patient Registration Successful");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Verify Your Account For Successfull Registration and OTP Is Send To The Below Gmail   ",
//					patient.getEmail());
//			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Patient Registration was Failed");
//		throw new PatientInvalidDetailsException("Your Registration was Failed ,Try Again Once");
//
//	}
//
//	/**
//	 * Appointment Booking
//	 */
//	@PostMapping("/book-appoinment")
//	@ApiOperation(value = "Book Appointment", notes = "Book Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Booked Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment Not Booked!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> patientAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
//		AppointmentDto appointment = patientService.patientAppointment(appointmentDto);
//		if (appointment != null) {
//			log.info("Appointment Is Fixed");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Appointment Is Fixed", appointment);
//
//			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Something  Went Wrong");
//		throw new PatientInvalidDetailsException("Something  Went Wrong");
//
//	}
//
//	/**
//	 * For checking appointment status by mobile number
//	 */
//	@GetMapping("/check-appoinment")
//	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment was Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> patientAppointmentStatus() {
//		PatientAppointmentStatusDto statusDto = patientService.patientAppointmentStatus();
//		if (statusDto != null) {
//			log.info("Checking the Appointment Status");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					" Checking the Appointment Status", statusDto);
//			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Patient Details Not Found In The Databse to Check the Appointment status");
//		throw new PatientInvalidDetailsException(
//				"Patient Details Not Found In The Databse to Check the Appointment status");
//	}
//
//	/**
//	 * Adding feedback
//	 */
//	@PostMapping("/add-feedback")
//	@ApiOperation(value = "Add FeedBack", notes = "Add FeedBack", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "FeedBack Details Added Successfully.."),
//			@ApiResponse(code = 404, message = "FeedBack Not Added!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> addFeedBack(@Valid @RequestBody FeedbackDto feedbackDto) {
//		FeedbackDto feedBack = patientService.saveFeedBack(feedbackDto);
//		if (feedBack != null) {
//			log.info("Patient Gave Feedback Sucessfully");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Patient Gave Feedback Sucessfully", feedBack);
//
//			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//
//		}
//		log.error("Patient Deatils Not Found In The Database To Write Review");
//		throw new PatientInvalidDetailsException("Patient Deatils Not Found In The Database To Write Review");
//
//	}
//
//	/**
//	 * Patient verification by OTP
//	 */
//	@GetMapping("/patient-verify/{OTP}")
//	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment was Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> patientVerify(@PathVariable("OTP") Long OTP) {
//		Patient patients = patientService.patientVerify(OTP);
//		if (patients != null) {
//			log.info("Patient Registration Was Done Successfully");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Patient Registration Was Done Successfully" + " " + "Patient Name is :    ",
//					patients.getFirstName() + " " + patients.getLastName());
//
//			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//
//		}
//		log.error("Something Went Wrong");
//		throw new PatientInvalidDetailsException("Something Went Wrong");
//
//	}
//
//	/**
//	 * Request for regeneration of OTP
//	 */
//	@GetMapping("/resendotp")
//	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment was Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> reSendOTP() {
//		String reSendOTP = patientService.reSendOTP();
//		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//				"Your OTP Is Send To This : ", reSendOTP);
//		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//	}
}
