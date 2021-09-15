package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.services.DesignationService;

@RestController
public class DesignationController {
    
	@Autowired
	DesignationService designationService;
	

	@PostMapping("/company/{id1}/department/{id2}/designation/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addCompanyLocation(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long departmentId, @RequestBody Designation designationDetails)
			throws ResourceNotFoundException {
	
		return designationService.addCompanyLocation(companyId, departmentId, designationDetails);
	}

	@PutMapping("/company/{id1}/department/{id2}/designation/{id3}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updatedDepartment(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long departmentId, @PathVariable(value = "id3") Long designationId,
			@RequestBody Designation designationDetails) throws ResourceNotFoundException {
	
		return designationService.updatedDesignation(companyId, departmentId, designationId, designationDetails);
	}

	@DeleteMapping("/company/{id1}/department/{id2}/designation/{id3}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteDesignation(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long departmentId, @PathVariable(value = "id3") Long designationId)
			throws ResourceNotFoundException {
		
		return designationService.deleteDesignation(companyId, departmentId, designationId);
	}

	@GetMapping("/company/{id1}/department/{id2}/designation/{id3}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getDepartment(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long departmentId, @PathVariable(value = "id3") Long designationId)
			throws ResourceNotFoundException {
	
		return designationService.getDepartment(companyId, departmentId, designationId);
	}

	@GetMapping("/company/{id1}/department/{id2}/designation/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllDesignation(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long departmentId) throws ResourceNotFoundException {
		
		return designationService.getAllDesignation(companyId, departmentId);
	}

	@GetMapping("/designations/admin")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllDesignationInformation() {

		return designationService.getAllDesignationInformation();
	}
	
	
	
}
