 package com.te.bookmydoctor.resource;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.bookmydoctor.dto.DoctorDto;
import com.te.bookmydoctor.dto.DoctorFetchAllDto;
import com.te.bookmydoctor.dto.PatientDto;
import com.te.bookmydoctor.exception.AdminInvalidDeatilsException;
import com.te.bookmydoctor.exception.DoctorInvalidDetailsException;
import com.te.bookmydoctor.exception.PatientInvalidDetailsException;
import com.te.bookmydoctor.response.Message;
import com.te.bookmydoctor.response.ResponseMessage;
import com.te.bookmydoctor.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@Api(value = "/api/v1/admin", tags = "Admin Resource")
public class AdminResource {
	@Autowired
	private AdminService adminService;
	

	/**
	 * For fetching details of all the doctors
	 */

	@GetMapping("/doctors")
	@ApiOperation(value = "All Doctors", notes = "All Doctors", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.ALL_DOCTORS_FETCHED),
			@ApiResponse(code = 404, message = Message.NO_DOCTOR_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> getAllDoctor() {
		List<DoctorFetchAllDto> allDoctor = adminService.getAllDoctor();
		if (!allDoctor.isEmpty()) {
			log.info(Message.ALL_DOCTORS_FETCHED);
			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.ALL_DOCTORS_FETCHED, allDoctor), HttpStatus.OK);
		}
		log.error(Message.NO_DOCTOR_FOUND);
		throw new AdminInvalidDeatilsException(Message.NO_DOCTOR_FOUND);
	}

	/**
	 * For fetching particular doctor by mobile number
	 */

	@GetMapping("/doctor/{mobileNumber}")
	@ApiOperation(value = "Particular Doctor", notes = "Particular Doctor", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.DOCTOR_FETCHED),
			@ApiResponse(code = 404, message = Message.NO_DOCTOR_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> getDoctor(@PathVariable("mobileNumber") Long mobileNumber) {
		DoctorDto doctorDto = adminService.getDoctor(mobileNumber);
		if (doctorDto != null) {
			log.info(Message.DOCTOR_FETCHED);
			return new ResponseEntity<ResponseMessage>(
					new ResponseMessage(HttpStatus.OK.value(), new Date(), false, Message.DOCTOR_FETCHED, doctorDto),
					HttpStatus.OK);
		}
		log.error(Message.NO_DOCTOR_FOUND);
		throw new DoctorInvalidDetailsException(Message.NO_DOCTOR_FOUND);
	}

	/**
	 * For removing a particular doctor by mobile number
	 */
	@GetMapping("/remove-doctor/{mobileNumber}")
	@ApiOperation(value = "Particular Doctor", notes = "Particular Doctor", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.REMOVE_DOCTOR_ADMIN),
			@ApiResponse(code = 404, message = Message.NO_DOCTOR_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> removeDoctor(@PathVariable("mobileNumber") Long mobileNumber) {
		Integer removeDoctor = adminService.removeDoctor(mobileNumber);
		if (removeDoctor > 0) {
			log.info(Message.REMOVE_DOCTOR_ADMIN);
			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.REMOVE_DOCTOR_ADMIN, removeDoctor), HttpStatus.OK);
		}
		log.error(Message.NO_DOCTOR_FOUND);
		throw new DoctorInvalidDetailsException(Message.NO_DOCTOR_FOUND);
	}

	/**
	 * For removing a particular patient by mobile number
	 */

	@GetMapping("/remove-patient/{mobileNumber}")
	@ApiOperation(value = "Particular Patient", notes = "Particular Patient", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.REMOVE_PATIENT),
			@ApiResponse(code = 404, message = Message.NO_PATIENT_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> removePatient(@PathVariable("mobileNumber") Long mobileNumber) {
		Integer removePatient = adminService.removePatient(mobileNumber);
		if (removePatient > 0) {
			log.info(Message.REMOVE_PATIENT);
			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.REMOVE_PATIENT, removePatient), HttpStatus.OK);
		}
		log.error(Message.NO_PATIENT_FOUND);
		throw new PatientInvalidDetailsException(Message.NO_PATIENT_FOUND);
	}

	/**
	 * For doctor verification by mobile number
	 */
	@GetMapping("/verify-doctor/{mobileNumber}")
	@ApiOperation(value = "Particular Doctor", notes = "Particular Doctor", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.VERIFY_DOCTOR),
			@ApiResponse(code = 404, message = Message.NO_DOCTOR_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> doctorVerified(@PathVariable("mobileNumber") Long mobileNumber) {
		Integer verification = adminService.doctorVerification(mobileNumber);
		if (verification != 0) {
			log.info(Message.VERIFY_DOCTOR);
			return new ResponseEntity<ResponseMessage>(
					new ResponseMessage(HttpStatus.OK.value(), new Date(), false, Message.VERIFY_DOCTOR, verification),
					HttpStatus.OK);
		}
		log.error(Message.NO_DOCTOR_FOUND);
		throw new DoctorInvalidDetailsException(Message.NO_DOCTOR_FOUND);
	}

	/**
	 * For patient verification by mobile number
	 */
	@GetMapping("/verify-patient/{mobileNumber}")
	@ApiOperation(value = "Particular Patient", notes = "Particular Patient", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.VERIFY_PATIENT),
			@ApiResponse(code = 404, message = Message.NO_PATIENT_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> patientVerified(@PathVariable("mobileNumber") Long mobileNumber) {
		Integer verification = adminService.patientVerification(mobileNumber);
		if (verification != 0) {
			log.info(Message.VERIFY_PATIENT);
			return new ResponseEntity<ResponseMessage>(
					new ResponseMessage(HttpStatus.OK.value(), new Date(), false, Message.VERIFY_PATIENT, verification),
					HttpStatus.OK);
		}
		log.error(Message.NO_PATIENT_FOUND);
		throw new DoctorInvalidDetailsException(Message.NO_PATIENT_FOUND);
	}

	/**
	 * For fetching details of particular patient by mobile number
	 */
	@GetMapping("/patient/{mobileNumber}")
	@ApiOperation(value = "view Particular Patient", notes = "View Particular Patient", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.PATIENT_FETCHED),
			@ApiResponse(code = 404, message = Message.NO_PATIENT_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> getPatient(@PathVariable("mobileNumber") Long mobileNumber) {
		PatientDto patient = adminService.getPatient(mobileNumber);
		if (patient != null) {
			log.info("Patient " + patient.getFirstName() + " " + patient.getLastName());
			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					"Patient " + patient.getFirstName() + " " + patient.getLastName(), patient), HttpStatus.OK);
		}
		log.error(Message.NO_PATIENT_FOUND);
		throw new DoctorInvalidDetailsException(Message.NO_PATIENT_FOUND);
	}

	/**
	 * For fetching details of all patients
	 */
	@GetMapping("/patients")
	@ApiOperation(value = "View All Patients", notes = "View All Patients", tags = "Book My Doctor Application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Message.ALL_PATIENTS_FETCHED),
			@ApiResponse(code = 404, message = Message.NO_PATIENT_FOUND),
			@ApiResponse(code = 403, message = Message.ACCESS_DENIED) })
	public ResponseEntity<ResponseMessage> getAllPatient() {
		List<PatientDto> allPatient = adminService.getAllPatient();
		if (!allPatient.isEmpty()) {
			log.info(Message.ALL_PATIENTS_FETCHED);
			return new ResponseEntity<ResponseMessage>(
					new ResponseMessage(HttpStatus.OK.value(), new Date(), false, " All Patient Fetch ", allPatient),
					HttpStatus.OK);
		}
		log.error(Message.NO_PATIENT_FOUND);
		throw new DoctorInvalidDetailsException(Message.NO_PATIENT_FOUND);
	}
	
//	/**
//	 * For fetching details of all the doctors
//	 */
//
//	@GetMapping("/doctors")
//	@ApiOperation(value = "All Doctors", notes = "All Doctors", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "All Doctors Details Fetched Successfully!!"),
//			@ApiResponse(code = 404, message = "No Doctor Found"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> getAllDoctor() {
//		List<DoctorFetchAllDto> allDoctor = adminService.getAllDoctor();
//		if (!allDoctor.isEmpty()) {
//			log.info("All Doctor Details Fetched Successfully");
//			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"All Doctor Details Fetched Successfully ", allDoctor), HttpStatus.OK);
//		}
//		log.error("Admin Not able to Fetch The Doctors, Try Again Once");
//		throw new AdminInvalidDeatilsException("Admin Not able to Fetch The Doctors, Try Again Once");
//	}
//	
//	/**
//	 * For fetching particular doctor by mobile number
//	 */
//	
//	@GetMapping("/doctor/{mobileNumber}")
//	@ApiOperation(value = "Particular Doctor", notes = "Particular Doctor", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Doctor Details Fetched Successfully!!"),
//			@ApiResponse(code = 404, message = "No Doctor Found"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> getDoctor(@PathVariable("mobileNumber") Long mobileNumber) {
//		DoctorDto doctorDto = adminService.getDoctor(mobileNumber);
//		if (doctorDto != null) {
//			log.info("Doctor Fetched Successfully");
//			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					" Doctor Fetched Successfully", doctorDto), HttpStatus.OK);
//		}
//		log.error("Doctor Not Present In The Database");
//		throw new DoctorInvalidDetailsException("Doctor Not Present In The Database");
//	}
//
//	/**
//	 * For removing a particular doctor by mobile number
//	 */
//	@GetMapping("/remove-doctor/{mobileNumber}")
//	@ApiOperation(value = "Particular Doctor", notes = "Particular Doctor", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Doctor Removed Successfully!!"),
//			@ApiResponse(code = 404, message = "No Doctor Found"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> removeDoctor(@PathVariable("mobileNumber") Long mobileNumber) {
//		Integer removeDoctor = adminService.removeDoctor(mobileNumber);
//		if (removeDoctor > 0) {
//			log.info("Doctor Deleted Successfully");
//			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Doctor Deleted Successfully", removeDoctor), HttpStatus.OK);
//		}
//		log.error("Doctor Not Present In the Database To Delete");
//		throw new DoctorInvalidDetailsException("Doctor Not Present In the Database To Delete");
//	}
//	
//	/**
//	 * For removing a particular patient by mobile number
//	 */
//
//	@GetMapping("/remove-patient/{mobileNumber}")
//	@ApiOperation(value = "Particular Patient", notes = "Particular Patient", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Patient Removed Successfully!!"),
//			@ApiResponse(code = 404, message = "No Patient Found"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> removePatient(@PathVariable("mobileNumber") Long mobileNumber) {
//		Integer removePatient = adminService.removePatient(mobileNumber);
//		if (removePatient > 0) {
//			log.info("Patient Deleted Sucessfully");
//			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Patient Deleted Sucessfully", removePatient), HttpStatus.OK);
//		}
//		log.error("Patient Not Present In the Database To Delete");
//		throw new PatientInvalidDetailsException("Patient Not Present In the Database To Delete");
//	}
//
//	/**
//	 * For doctor verification by mobile number
//	 */
//	@GetMapping("/verify-doctor/{mobileNumber}")
//	@ApiOperation(value = "Particular Doctor", notes = "Particular Doctor", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Doctor  Verified Successfully!!"),
//			@ApiResponse(code = 404, message = "No Patient Found"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> doctorVerified(@PathVariable("mobileNumber") Long mobileNumber) {
//		Integer verification = adminService.doctorVerification(mobileNumber);
//		if (verification != 0) {
//			log.info("Doctor is Verified Successfully");
//			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Doctor is Verified Successfully", verification), HttpStatus.OK);
//		}
//		log.error("Doctor Not Present In the Database To Verify");
//		throw new DoctorInvalidDetailsException("Doctor Not Present In the Database To Verify");
//	}
//
//	/**
//	 * For patient verification by mobile number
//	 */
//	@GetMapping("/verify-patient/{mobileNumber}")
//	@ApiOperation(value = "Particular Patient", notes = "Particular Patient", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Patient  Verified Successfully!!"),
//			@ApiResponse(code = 404, message = "No Patient Found"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> patientVerified(@PathVariable("mobileNumber") Long mobileNumber) {
//		Integer verification = adminService.patientVerification(mobileNumber);
//		if (verification != 0) {
//			log.info("Patient is Verified Successfully");
//			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Patient is Verified Successfully", verification), HttpStatus.OK);
//		}
//		log.error("Patient Not Present In the Database To Verify");
//		throw new DoctorInvalidDetailsException("Patient Not Present In the Database To Verify");
//	}
//
//	/**
//	 * For fetching details of particular patient by mobile number
//	 */
//	@GetMapping("/patient/{mobileNumber}")
//	@ApiOperation(value = "view Particular Patient", notes = "View Particular Patient", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "View Particular Patient!!"),
//			@ApiResponse(code = 404, message = "No Patient Found"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> getPatient(@PathVariable("mobileNumber") Long mobileNumber) {
//		PatientDto patient = adminService.getPatient(mobileNumber);
//		if (patient != null) {
//			log.info("Patient " + patient.getFirstName() + " " + patient.getLastName());
//			return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
//					"Patient " + patient.getFirstName() + " " + patient.getLastName(), patient), HttpStatus.OK);
//		}
//		log.error("Patient Not Found");
//		throw new DoctorInvalidDetailsException("Patient Not Found");
//	}
//
//	/**
//	 * For fetching details of all patients
//	 */
//	@GetMapping("/patients")
//	@ApiOperation(value = "View All Patients", notes = "View All Patients", tags = "Book My Doctor Application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "View All Patients!!!"),
//			@ApiResponse(code = 404, message = "No Patient Found"),
//			@ApiResponse(code = 403, message = "Access Denied!!") })
//	public ResponseEntity<ResponseMessage> getAllPatient() {
//		List<PatientDto> allPatient = adminService.getAllPatient();
//		if (!allPatient.isEmpty()) {
//			log.info("All Doctor Details Fetched Successfully");
//			return new ResponseEntity<ResponseMessage>(
//					new ResponseMessage(HttpStatus.OK.value(), new Date(), false, " All Patient Fetch ", allPatient),
//					HttpStatus.OK);
//		}
//		log.error("Patient Not Found");
//		throw new DoctorInvalidDetailsException("Patient Not Found");
//	}
}
