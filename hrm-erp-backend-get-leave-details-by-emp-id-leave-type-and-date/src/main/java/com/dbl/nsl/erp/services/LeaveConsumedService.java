package com.dbl.nsl.erp.services;

import java.util.Date;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;

public interface LeaveConsumedService {
	
	ResponseEntity<?> getLeaveInfo(Long employeeId) throws ResourceNotFoundException; 
	
	ResponseEntity<?> leaveReport(Long employeeId, Date startdate, Date enddate) throws ResourceNotFoundException;

	ResponseEntity<?> leaveReportByEmpIdAndDate(Long employeeId, String type, Date date);

}
