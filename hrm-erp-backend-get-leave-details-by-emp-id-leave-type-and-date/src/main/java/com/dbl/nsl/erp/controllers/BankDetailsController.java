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
import com.dbl.nsl.erp.models.BankDetails;
import com.dbl.nsl.erp.services.BankDetailsService;

@RestController
public class BankDetailsController {
	
	@Autowired
	BankDetailsService bankDetailsService;
	
	@GetMapping("/employee/{id}/bank_details")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getSalaryInformation(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		
		return bankDetailsService.getSalaryInformation(employeeId);
	}

	@PutMapping("/employee/{id}/bank_details/update")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addSalaryBenefit( @PathVariable(value = "id") Long employeeId,
			@RequestBody BankDetails bankDetails ) throws ResourceNotFoundException {
		
		return bankDetailsService.addSalaryBenefit(employeeId, bankDetails);
	}
	
	@DeleteMapping("/employee/{id}/bank_details/delete")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteEmployeeSalaryBenefit( @PathVariable(value = "id") Long employeeId )
			throws ResourceNotFoundException {
		
		return bankDetailsService.deleteEmployeeSalaryBenefit(employeeId);
	}
	

}
