package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.services.EducationService;

@RestController
public class EducationController {
	
	@Autowired
	EducationService educationService;
	
	
	@RequestMapping(value = "/employee/{id}/education/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> createEducation( @RequestParam(value = "eduJson", required = true) String eduJson,
			@RequestParam(required = false, value = "file") MultipartFile file, @PathVariable(value = "id") Long employeeId)
					throws ResourceNotFoundException {
		
		return educationService.createEducation(eduJson, file, employeeId);
	}
	
	@RequestMapping(value = "/employee/{id1}/education/{id2}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateEducation( @RequestParam(value = "eduJson", required = true) String eduJson,
			@RequestParam(required = false, value = "file") MultipartFile file, @PathVariable(value = "id1") Long employeeId, 
			@PathVariable(value = "id2") Long educationId)
					throws ResourceNotFoundException {
		
		return educationService.updateEducation(eduJson, file, employeeId, educationId);
	}

	@GetMapping("/employee/{id1}/education/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getEducation(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long educationId) throws ResourceNotFoundException {
		
		return educationService.getEducation(employeeId, educationId);
	}

	@GetMapping("/employee/{id1}/education/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAllEducation(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {
		
		return educationService.getAllEducation(employeeId);
	}
	
	@Transactional
	@DeleteMapping("/employee/{id1}/education/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> deleteEducation(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long educationId) throws ResourceNotFoundException {
		
		return educationService.deleteEducation(employeeId, educationId);
	}
	
	


}
