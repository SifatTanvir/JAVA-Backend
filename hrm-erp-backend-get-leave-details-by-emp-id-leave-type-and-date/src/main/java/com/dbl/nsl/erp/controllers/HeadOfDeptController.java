package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.services.HeadOfDeptService;

@RestController
public class HeadOfDeptController {
	
	@Autowired
	HeadOfDeptService headOfDeptService;
	
	@GetMapping("/employee/{id}/head_of_dept")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getHeadOfDept(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		
		return headOfDeptService.getHeadOfDept(employeeId);
	}
	
	@GetMapping("/head_of_dept/{id}/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getChildEmployee(@PathVariable(value = "id") Long hodId)
			throws ResourceNotFoundException {
		
		return headOfDeptService.getChildEmployee(hodId);
	}
	
	
	@DeleteMapping("/head_of_dept/{id1}/employee/{id2}/delete")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateHeadOfDept(@PathVariable(value = "id1") Long hodId,
			@PathVariable(value = "id2") Long employeeId) throws ResourceNotFoundException {
		
		return headOfDeptService.updateHeadOfDept(hodId, employeeId);
	}
	
	

}
