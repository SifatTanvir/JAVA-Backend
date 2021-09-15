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
import com.dbl.nsl.erp.services.ExperienceService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ExperienceController {

	@Autowired 
	ExperienceService experienceService;
	
	ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/employee/{id}/experience/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> createExperience( @RequestParam(value = "expJson", required = true) String expJson,
			@RequestParam(required = false, value = "file") MultipartFile file, @PathVariable(value = "id") Long employeeId)
					throws ResourceNotFoundException {

		return experienceService.createExperience(expJson, file, employeeId);
	}

	@RequestMapping(value = "/employee/{id1}/experience/{id2}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateExperience( @RequestParam(value = "expJson", required = true) String expJson,
			@RequestParam(required = false, value = "file") MultipartFile file, @PathVariable(value = "id1") Long employeeId, 
			@PathVariable(value = "id2") Long experienceId) throws  ResourceNotFoundException {

	    return experienceService.updateExperience(expJson, file, employeeId, experienceId);
	}


	@GetMapping("/employee/{id1}/experience/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getExperience(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long experienceId) throws ResourceNotFoundException {
	
		return experienceService.getExperience(employeeId, experienceId);
	}

	@GetMapping("/employee/{id1}/experience/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAllExperience(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {
		
		return experienceService.getAllExperience(employeeId);
	}
	
	@Transactional
	@DeleteMapping("/employee/{id1}/experience/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> deleteExperience(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long experienceId) throws ResourceNotFoundException {
		
		return experienceService.deleteExperience(employeeId, experienceId);
	}
	
	
	
}
