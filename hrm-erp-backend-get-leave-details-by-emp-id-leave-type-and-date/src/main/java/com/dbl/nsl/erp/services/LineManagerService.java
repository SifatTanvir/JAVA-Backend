package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;

public interface LineManagerService {
	
	ResponseEntity<?> getLineManager(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getChildEmployee(Long lineManagerId) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateLineManager(Long lineManagerId, Long employeeId) throws ResourceNotFoundException;
	
	

}
