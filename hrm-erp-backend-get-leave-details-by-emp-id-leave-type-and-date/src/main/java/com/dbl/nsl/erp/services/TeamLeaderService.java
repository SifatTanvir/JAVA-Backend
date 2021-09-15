package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;

public interface TeamLeaderService {
	
	ResponseEntity<?> getTeamLeader( Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getChildEmployee(Long teamLeaderId) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateTeamLeader(Long teamLeaderId, Long employeeId) throws ResourceNotFoundException;
	
	

}
