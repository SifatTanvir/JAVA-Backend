package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.services.LineManagerService;


@RestController
public class LineManagerController {
	
	@Autowired
	LineManagerService lineManagerService;
	
	@GetMapping("/employee/{id}/line_manager")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getLineManager(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		
		return lineManagerService.getLineManager(employeeId);
	}
	
	
	@GetMapping("/line_manager/{id}/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getChildEmployee(@PathVariable(value = "id") Long lineManagerId)
			throws ResourceNotFoundException {
		
		return lineManagerService.getChildEmployee(lineManagerId);
	}
	
	
	@DeleteMapping("/line_manager/{id1}/employee/{id2}/delete")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateLineManager(@PathVariable(value = "id1") Long lineManagerId,
			@PathVariable(value = "id2") Long employeeId) throws ResourceNotFoundException {
		
		return lineManagerService.updateLineManager(lineManagerId, employeeId);
	}
	
	
	
	
	
	

}