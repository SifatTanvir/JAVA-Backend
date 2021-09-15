package com.dbl.nsl.erp.services;

import java.io.FileNotFoundException;
import java.text.ParseException;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.payload.request.Message;

import java.util.Date;

public interface LeaveRequestService {

	ResponseEntity<?> sendLeaveRequest( String leaveReq, MultipartFile file ) 
			throws ResourceNotFoundException, FileNotFoundException, MessagingException, ParseException;

	ResponseEntity<?> leaveRequestAccept(Long employeeId, Long senderId, Long leaveRequestId) 
			throws ResourceNotFoundException, MessagingException, FileNotFoundException, ParseException;

	ResponseEntity<?> leaveRequestReject(Long employeeId, Long senderId, Long leaveRequestId, Message reason) 
			throws ResourceNotFoundException, ParseException;

	ResponseEntity<?> getAllLeaveRequestOfAnEmployee(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getLeaveRequestOfAllEmployee() throws ResourceNotFoundException;

	ResponseEntity<?> getSpecificLeaveRequest(Long employeeId, Long leaveConsumeId) throws ResourceNotFoundException;

	ResponseEntity<?> getLeaveRequestToPerformAction(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAcceptedOrRejectedLeaveRequest(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getNumberOfWorkingDaysBetweenStartDateAndEndDate(Long employeeId, Date startDate, Date endDate) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteLeaveRequest(Long employeeId, Long leaveConsumeId) throws ResourceNotFoundException, ParseException;
	
	ResponseEntity<?> deleteLeaveRequestByAdmin(Long adminId, Long employeeId, Long leaveConsumeId) throws ResourceNotFoundException, ParseException;

	ResponseEntity<?> getAllRequestedLeaveOfAnEmployee(Long employeeId);

	
}
