package com.dbl.nsl.erp.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.payload.request.Message;
import com.dbl.nsl.erp.services.LeaveRequestService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
public class LeaveRequestController {
	
	@Autowired
	LeaveRequestService leaveRequestService;

	//save leave request
	@RequestMapping(value = "/send/leave_request", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or hasRole('USER')")
	public ResponseEntity<?> sendLeaveRequest( @RequestParam(value = "leaveReq", required = true) String leaveReq, 
			@RequestParam(value = "file", required = false) MultipartFile file ) 
					throws JsonParseException, JsonMappingException, IOException,ResourceNotFoundException, MessagingException, ParseException {

		return leaveRequestService.sendLeaveRequest(leaveReq, file);
	}

	//hit accept button, this end point will be executed
	@PutMapping("/employee/{id1}/sender/{id2}/leave_request/{id3}/accepted")  // employee who wants to perform action:[accept,reject] on leave request
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or ( @securityService.hasEntry(#employeeId) and @securityService.hasAccess(#employeeId, #senderId) )")
	public ResponseEntity<?> leaveRequestAccept( @PathVariable(value = "id1" ) Long employeeId, 
			@PathVariable(value = "id2") Long senderId, @PathVariable(value = "id3" ) Long leaveRequestId ) 
					throws ResourceNotFoundException, MessagingException, FileNotFoundException, ParseException {

		return leaveRequestService.leaveRequestAccept(employeeId, senderId, leaveRequestId);
	}

	//hit reject button, this end point will be executed
	@PutMapping("employee/{id1}/sender/{id2}/leave_request/{id3}/rejected")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or ( @securityService.hasEntry(#employeeId) and @securityService.hasAccess(#employeeId, #senderId) )")
	public ResponseEntity<?> leaveRequestReject( @PathVariable(value = "id1" ) Long employeeId, 
			@PathVariable(value = "id2") Long senderId, @PathVariable(value = "id3" ) Long leaveRequestId, @RequestBody Message reason ) 
					throws ResourceNotFoundException, MessagingException, ParseException {

		return leaveRequestService.leaveRequestReject(employeeId, senderId, leaveRequestId, reason);
	} 

	//individual employee all leave requests
	@GetMapping("/employee/{id}/leave_request/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAllLeaveRequestOfAnEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
	
		return leaveRequestService.getAllLeaveRequestOfAnEmployee(employeeId);
	}
	
	//get all leave requests
	@GetMapping("/leave_request/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getLeaveRequestOfAllEmployee() throws ResourceNotFoundException {
	
		return leaveRequestService.getLeaveRequestOfAllEmployee();
	}

	//specific employee specific leave request
	@GetMapping("/employee/{id1}/leave_request/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getSpecificLeaveRequest(@PathVariable(value = "id") Long employeeId, 
			@PathVariable(value = "id") Long leaveConsumeId) throws ResourceNotFoundException {
		
		return leaveRequestService.getSpecificLeaveRequest(employeeId, leaveConsumeId);
	}

	//line manager, team leader and hod child employee leave requests
	@GetMapping("/viewer/{id}/leave_request/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getLeaveRequestToPerformAction(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
	
		return leaveRequestService.getLeaveRequestToPerformAction(employeeId);
	}
	
	//line manager, team leader and hod child employee leave requests
	@GetMapping("/viewer/{id}/accepted_rejected_leave_request/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAcceptedOrRejectedLeaveRequest(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
	
		return leaveRequestService.getAcceptedOrRejectedLeaveRequest(employeeId);
	}
	
	@Transactional
	@DeleteMapping("/employee/{id1}/leave_request/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> deleteLeaveRequest(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long leaveRequestId) throws ResourceNotFoundException, ParseException {
		
		return leaveRequestService.deleteLeaveRequest(employeeId, leaveRequestId);
	}
	
	@Transactional
	@DeleteMapping("admin/{id0}/employee/{id1}/leave_request/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> deleteLeaveRequestByAdmin(@PathVariable(value = "id0") Long adminId, @PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long leaveRequestId) throws ResourceNotFoundException, ParseException {
		
		return leaveRequestService.deleteLeaveRequestByAdmin(adminId, employeeId, leaveRequestId);
	}
	
	@GetMapping("/employee/{id}/pending/leave_request/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAllRequestedLeaveOfAnEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
	
		return leaveRequestService.getAllRequestedLeaveOfAnEmployee(employeeId);
	}
	
	
	@GetMapping("/leave_request/employee/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAllByDateBetweenStartDateAndEndDate(@PathVariable(value = "id") Long employeeId,
			@RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
			@RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) throws ResourceNotFoundException {

		return leaveRequestService.getNumberOfWorkingDaysBetweenStartDateAndEndDate(employeeId, startdate, enddate);
	}
	




}
