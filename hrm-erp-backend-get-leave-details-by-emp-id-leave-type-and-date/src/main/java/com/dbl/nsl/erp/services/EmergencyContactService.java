package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.EmergencyContact;

public interface EmergencyContactService {

	ResponseEntity<?> getEmergencyContact(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateEmergencyContact(Long employeeId, EmergencyContact emergencyContact) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteEmergencyContact(Long employeeId) throws ResourceNotFoundException;
	
}
