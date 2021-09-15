package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.EmergencyContact;
import com.dbl.nsl.erp.services.EmergencyContactService;

@RestController
public class EmergencyContactController {
	
	@Autowired
	EmergencyContactService emergencyContactService;
    
	@GetMapping("/employee/{id}/emergency_contact")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getEmergencyContact( @PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		
		return emergencyContactService.getEmergencyContact(employeeId);
	}

	@PutMapping("/employee/{id}/emergency_contact/update")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> updateEmergencyContact(  @PathVariable(value = "id") Long employeeId,
			@RequestBody EmergencyContact emergencyContact) throws ResourceNotFoundException {
		
		return emergencyContactService.updateEmergencyContact(employeeId, emergencyContact);
	}
	
	@DeleteMapping("/employee/{id}/emergency_contact/delete")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> deleteEmergencyContact(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		
		return emergencyContactService.deleteEmergencyContact(employeeId);
	}
	
	
}
