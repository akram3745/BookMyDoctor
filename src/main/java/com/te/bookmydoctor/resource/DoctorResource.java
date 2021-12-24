package com.te.bookmydoctor.resource;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.bookmydoctor.dto.AppointmentFetchDto;
import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.FeedbackDto;
import com.te.bookmydoctor.exception.DoctorInvalidDetailsException;
import com.te.bookmydoctor.response.Message;
import com.te.bookmydoctor.response.ResponseMessage;
import com.te.bookmydoctor.service.DoctorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/doctor")
@Api(value = "/api/v1/doctor", tags = "Doctor Resource")
public class DoctorResource {

	@Autowired
	private DoctorService doctorService;
	
	/**
	 * Doctor registration
	 */

	@PostMapping("/register-doctor")
	@ApiOperation(value = "Doctor Registration", notes = "Registration Doctor", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.DOCTOR_REGISTRATION_SUCCESSFUL),
			@ApiResponse(code = 404, message = Message.DOCTOR_REGISTRATION_UNSUCCESSFUL),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> saveDoctor(@RequestBody DoctorDto doctorDto) {
		if (doctorDto != null) {
			DoctorDto saveDoctor = doctorService.saveDoctor(doctorDto);
			log.info(doctorDto.getFirstName() + Message.VERIFY_ACCOUNT);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
					saveDoctor.getFirstName() + Message.VERIFICATION_MESSAGE, saveDoctor);
			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.DOCTOR_REGISTRATION_UNSUCCESSFUL);
		throw new DoctorInvalidDetailsException(Message.DOCTOR_REGISTRATION_UNSUCCESSFUL);
	}

	/**
	 * For Checking all the appointments booked on a particular date
	 */

	@SuppressWarnings("static-access")
	@GetMapping("/check-appointment/{date}")
	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.APPOINTMENT_DETAILS),
			@ApiResponse(code = 404, message = Message.APPOINTMENT_NOT_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> seeAppointment(@PathVariable("date") Date date) {
		LocalDate localDate = date.toLocalDate();
		if (localDate.equals(LocalDate.now()) || localDate.isAfter(localDate.now())) {
			log.info(Message.APPOINTMENT_DETAILS);
			AppointmentFetchDto seeAppointment = doctorService.seeAppointment(date);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
					Message.APPOINTMENT_DETAILS, seeAppointment);
			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.APPOINTMENT_TIME_EXCEPTION);
		throw new DoctorInvalidDetailsException(Message.APPOINTMENT_TIME_EXCEPTION);
	}

	/**
	 * For viewing all the feedbacks
	 */

	@GetMapping("/feedback")
	@ApiOperation(value = "See FeedBack", notes = "See FeedBack", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.FEEDBACK_LIST),
			@ApiResponse(code = 404, message = Message.FEEDBACK_NOT_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> seeFeedBack() {
		List<FeedbackDto> seeFeedBack = doctorService.seeFeedBack();
		if (!seeFeedBack.isEmpty()) {
			log.info(Message.FEEDBACK_LIST);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
					Message.FEEDBACK_LIST, seeFeedBack);
			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.FEEDBACK_NOT_FOUND);
		throw new DoctorInvalidDetailsException(Message.FEEDBACK_NOT_FOUND);
	}

	/**
	 * For removing a particular doctor by mobile number
	 */

	@GetMapping("/remove-doctor/{mobileNumber}")
	@ApiOperation(value = "Remove Doctor", notes = "Remove Doctor", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.REMOVE_DOCTOR_DOCTOR),
			@ApiResponse(code = 404, message = Message.NO_DOCTOR_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> removeDoctor(@PathVariable("mobileNumber") Long mobileNumber) {
		Integer removeDoctor = doctorService.removeDoctor(mobileNumber);
		if (removeDoctor != 0) {
			log.info(Message.REMOVE_DOCTOR_DOCTOR);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
					Message.REMOVE_DOCTOR_DOCTOR, removeDoctor);
			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.NO_DOCTOR_FOUND);
		throw new DoctorInvalidDetailsException(Message.NO_DOCTOR_FOUND);
	}

	/**
	 * For checking the registration status
	 */

	@GetMapping("/registration-status")
	@ApiOperation(value = "Registration Status", notes = "Registration Status", tags = "Book My Doctor Application")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Message.DOCTOR_REGISTRATION_STATUS + Message.VERIFY_DOCTOR),
			@ApiResponse(code = 404, message = Message.REGISTRATION_DETAILS_NOT_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> checkStatus() {
		Integer checkStatus = doctorService.checkStatus();
		if (checkStatus != 0) {
			log.info(Message.DOCTOR_REGISTRATION_STATUS + Message.VERIFY_DOCTOR);
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
					Message.DOCTOR_REGISTRATION_STATUS + Message.VERIFY_DOCTOR, checkStatus);
			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		}
		log.info(Message.DOCTOR_REGISTRATION_STATUS + Message.DOCTOR_NOT_VERIFIED);
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
				Message.DOCTOR_REGISTRATION_STATUS, Message.DOCTOR_NOT_VERIFIED);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
//
//	@PostMapping("/register-doctor")
//	@ApiOperation(value="Doctor Registration", notes="Registration Doctor",tags="Book My Doctor Application")
//	@ApiResponses(value= { @ApiResponse(code=200,message="Doctor Registration Successfully!!"),
//	@ApiResponse(code=404,message="Doctor Not Added!!"),@ApiResponse(code=403,message="Access Denied!!")})
//	public ResponseEntity<ResponseMessage> saveDoctor(@RequestBody DoctorDto doctorDto) {
//		ResponseMessage saveDoctor = doctorService.saveDoctor(doctorDto);
//		return new ResponseEntity<ResponseMessage>(saveDoctor, HttpStatus.OK);
//	}
//
//	@GetMapping("/check-appointment/{date}")
//	@ApiOperation(value="Check Appointment", notes="Check Appointment",tags="Book My Doctor Application")
//	@ApiResponses(value= { @ApiResponse(code=200,message="Appointment Details Fetched Successfully.."),
//	@ApiResponse(code=404,message="Appointment Not Found!!"),@ApiResponse(code=403,message="Access Denied!!")})
//	public ResponseEntity<ResponseMessage> seeAppointment(@PathVariable("date") Date date) {
//		ResponseMessage saveDoctor = doctorService.seeAppointment(date);
//		return new ResponseEntity<ResponseMessage>(saveDoctor, HttpStatus.OK);
//	}
//
//	@GetMapping("/feedback")
//	@ApiOperation(value="See FeedBack", notes="See FeedBack",tags="Book My Doctor Application")
//	@ApiResponses(value= { @ApiResponse(code=200,message="FeedBack Details Fetched Successfully.."),
//	@ApiResponse(code=404,message="No FeedBack Found!!"),@ApiResponse(code=403,message="Access Denied!!")})
//	public ResponseEntity<ResponseMessage> seeFeedBack() {
//		ResponseMessage saveDoctor = doctorService.seeFeedBack();
//		return new ResponseEntity<ResponseMessage>(saveDoctor, HttpStatus.OK);
//	}
//
//	@GetMapping("/remove-doctor/{mobileNumber}")
//	@ApiOperation(value="Remove Doctor", notes="Remove Doctor",tags="Book My Doctor Application")
//	@ApiResponses(value= { @ApiResponse(code=200,message="Doctor Removed Successfully.."),
//	@ApiResponse(code=404,message="No Doctor Found!!"),@ApiResponse(code=403,message="Access Denied!!")})
//	public ResponseEntity<ResponseMessage> removeDoctor(@PathVariable("mobileNumber") Long mobileNumber) {
//		ResponseMessage responseMessage = doctorService.removeDoctor(mobileNumber);
//		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//	}
//
//	@GetMapping("/registration-status")
//	@ApiOperation(value="Registration Status", notes="Registration Status",tags="Book My Doctor Application")
//	@ApiResponses(value= { @ApiResponse(code=200,message="Registration Details Fetched Successfully.."),
//	@ApiResponse(code=404,message="Registration Details Not Found!!"),@ApiResponse(code=403,message="Access Denied!!")})
//	public ResponseEntity<ResponseMessage> checkStatus() {
//		ResponseMessage responseMessage = doctorService.checkStatus();
//		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//	}

//	@Autowired
//	private DoctorService doctorService;
//
//	/**
//	 * Doctor registration
//	 */
//
//	@PostMapping("/register-doctor")
//	@ApiOperation(value = "Doctor Registration", notes = "Registration Doctor", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Doctor Registration Successfully!!"),
//			@ApiResponse(code = 404, message = "Doctor Not Added!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> saveDoctor(@RequestBody DoctorDto doctorDto) {
//		if (doctorDto != null) {
//			DoctorDto saveDoctor = doctorService.saveDoctor(doctorDto);
//			log.info(doctorDto.getFirstName() + " Registration Done Successfully And Need to Verify the Details ");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
//					saveDoctor.getFirstName() + " Your Registraion Done Successfully And Wait For The Verification",
//					saveDoctor);
//			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Doctor Registration Was Failed");
//		throw new DoctorInvalidDetailsException("Your Registration Was Failed ,Try Again Once");
//	}
//
//	/**
//	 * For Checking all the appointments booked on a particular date
//	 */
//
//	@SuppressWarnings("static-access")
//	@GetMapping("/check-appointment/{date}")
//	@ApiOperation(value = "Check Appointment", notes = "Check Appointment", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Appointment Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> seeAppointment(@PathVariable("date") Date date) {
//		LocalDate localDate = date.toLocalDate();
//		if (localDate.equals(LocalDate.now()) || localDate.isAfter(localDate.now())) {
//			log.info("Doctor Sucessfully Fetching His Appointment Details");
//			AppointmentFetchDto seeAppointment = doctorService.seeAppointment(date);
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
//					"Appointment Details Sucessfully Fetched And Your Appointment Is With The Dcotor  ",
//					seeAppointment);
//			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Appointment Time Should be Greater Than Or Equal to The Current Date ");
//		throw new DoctorInvalidDetailsException(
//				"Appointment Time Should be Greater Than Or Equal to The Current Date ");
//	}
//
//	/**
//	 * For viewing all the feedbacks
//	 */
//
//	@GetMapping("/feedback")
//	@ApiOperation(value = "See FeedBack", notes = "See FeedBack", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "FeedBack Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "No FeedBack Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> seeFeedBack() {
//		List<FeedbackDto> seeFeedBack = doctorService.seeFeedBack();
//		if (!seeFeedBack.isEmpty()) {
//			log.info("Doctor Sucessfully Fetching His FeedBack Details");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
//					"Your FeedBack Details Are Fetched", seeFeedBack);
//			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Doctor Deatils Not Found To Display The Feedback Details");
//		throw new DoctorInvalidDetailsException("Doctor Deatils Not Found To Display The Feedback");
//	}
//
//	/**
//	 * For removing a particular doctor by mobile number
//	 */
//
//	@GetMapping("/remove-doctor/{mobileNumber}")
//	@ApiOperation(value = "Remove Doctor", notes = "Remove Doctor", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Doctor Removed Successfully.."),
//			@ApiResponse(code = 404, message = "No Doctor Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> removeDoctor(@PathVariable("mobileNumber") Long mobileNumber) {
//		Integer removeDoctor = doctorService.removeDoctor(mobileNumber);
//		if (removeDoctor != 0) {
//			log.info("Doctor Details Was Deleted Successfully");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
//					" Your Deatils Was Deleted Successfully", removeDoctor);
//			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
//		}
//		log.error("Doctor Deatils Not Found To Delete");
//		throw new DoctorInvalidDetailsException("No Doctor Details Present With Given Mobile Number");
//	}
//
//	/**
//	 * For checking the registration status of a particular doctor
//	 */
//
//	@GetMapping("/registration-status")
//	@ApiOperation(value = "Registration Status", notes = "Registration Status", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Registration Details Fetched Successfully.."),
//			@ApiResponse(code = 404, message = "Registration Details Not Found!!"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> checkStatus() {
//		Integer checkStatus = doctorService.checkStatus();
//		if (checkStatus != 0) {
//			log.info("Doctor Viewing his Registration Verification Status And Status Is Verified ");
//			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
//					" Doctor Checking his Registration Status Sucessfully", checkStatus);
//			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
//		}
//		log.info("Doctor Viewing his Registration Verification Status And Status Is Not Verified ");
//		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new java.util.Date(), false,
//				" Doctor Checking his Registration Status Sucessfully", "Doctor Your Account Is Not Verified Yet");
//		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
//	}
}
