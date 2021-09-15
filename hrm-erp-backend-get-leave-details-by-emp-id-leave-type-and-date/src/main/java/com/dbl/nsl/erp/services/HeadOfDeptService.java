package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;

public interface HeadOfDeptService {
	
	 ResponseEntity<?> getHeadOfDept(Long employeeId) throws ResourceNotFoundException;
	 
	 ResponseEntity<?> getChildEmployee(Long hodId) throws ResourceNotFoundException;
	 
	 ResponseEntity<?> updateHeadOfDept(Long hodId, Long employeeId) throws ResourceNotFoundException;

}
