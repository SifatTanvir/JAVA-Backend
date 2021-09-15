package com.dbl.nsl.erp.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Nominee;

public interface NomineeService {
	
	ResponseEntity<?> addNominee(Long employeeId, Nominee nomineeDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> getNominees(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getNominee(Long employeeId, Long nomineeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateNominee( Long employeeId, List<Nominee> nomineeList) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteNominee( Long employeeId, Long nomineeId) throws ResourceNotFoundException; 

}
