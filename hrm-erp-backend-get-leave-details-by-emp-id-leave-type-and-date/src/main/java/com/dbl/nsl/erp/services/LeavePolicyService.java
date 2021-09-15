package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.LeavePolicy;

public interface LeavePolicyService {
	
	ResponseEntity<?> addLeavePolicy( LeavePolicy leavePolicyDetails);
	
	ResponseEntity<?> getLeavePolicy(Long leavePolicyId) throws ResourceNotFoundException;
	
	ResponseEntity<?> allLeavePolicy() throws ResourceNotFoundException;
	
	ResponseEntity<?> updateLeavePolicy(Long leavePolicyId, LeavePolicy leavePolicyDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> allEmployees(Long leavePolicyId) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateEmployeeLeavePolicy(Long employeeId, Long leavePolicyId) throws ResourceNotFoundException;
	


}
