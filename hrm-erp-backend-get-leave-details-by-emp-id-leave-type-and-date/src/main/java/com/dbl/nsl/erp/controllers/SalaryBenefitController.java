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
import com.dbl.nsl.erp.models.SalaryBenefit;
import com.dbl.nsl.erp.services.SalaryBenefitService;

@RestController
public class SalaryBenefitController {
	
	@Autowired
	SalaryBenefitService salaryBenefitService;
	
	@GetMapping("/employee/{id}/salary_benefit")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getSalaryInformation(@PathVariable(value = "id") long employeeId )
			throws ResourceNotFoundException {
		
		return salaryBenefitService.getSalaryInformation(employeeId);
	}
	
	@PutMapping("/employee/{id}/salary_benefit/update")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addSalaryBenefit( @PathVariable(value = "id") long employeeId,
			@RequestBody SalaryBenefit salaryBenefit ) throws ResourceNotFoundException {
		
		return salaryBenefitService.addSalaryBenefit(employeeId, salaryBenefit);
	}
	
	@DeleteMapping("/employee/{id}/salary_benefit/delete")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteEmployeeSalaryBenefit( @PathVariable(value = "id") long employeeId )
			throws ResourceNotFoundException {
	
		return salaryBenefitService.deleteEmployeeSalaryBenefit(employeeId);
	}
	
}
