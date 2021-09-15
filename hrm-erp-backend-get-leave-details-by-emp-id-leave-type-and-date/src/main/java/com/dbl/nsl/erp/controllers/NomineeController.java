package com.dbl.nsl.erp.controllers;

import java.util.List;

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
import com.dbl.nsl.erp.models.Nominee;
import com.dbl.nsl.erp.services.NomineeService;

@RestController
public class NomineeController {

	@Autowired
	NomineeService nomineeService;
	
	
	@PostMapping("/employee/{id}/nominee/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> addNominee(@PathVariable(value = "id") Long employeeId,
			@RequestBody Nominee nomineeDetails) throws ResourceNotFoundException {

		return nomineeService.addNominee(employeeId, nomineeDetails);
	}

	@GetMapping("/employee/{id1}/nominee/all")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getNominees(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {

		return nomineeService.getNominees(employeeId);
	}

	@GetMapping("/employee/{id1}/nominee/{id2}")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getNominee(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long nomineeId) throws ResourceNotFoundException {

		return nomineeService.getNominee(employeeId, nomineeId);
	}

	@DeleteMapping("/employee/{id1}/nominee/{id2}/delete")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteNominee(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long nomineeId) throws ResourceNotFoundException {

		return nomineeService.deleteNominee(employeeId, nomineeId);
	}

	@PutMapping("/employee/{id}/nominee/all/update")
	@PreAuthorize("hasRole('ADMIN')  or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> updateNominee(@PathVariable(value = "id") Long employeeId,
			@RequestBody List<Nominee> nomineeList) throws ResourceNotFoundException {

		return nomineeService.updateNominee(employeeId, nomineeList);
	}

}
