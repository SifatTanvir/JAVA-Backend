package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.BankDetails;

public interface BankDetailsService {
	
	ResponseEntity<?> getSalaryInformation(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> addSalaryBenefit(Long employeeId, BankDetails bankDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteEmployeeSalaryBenefit(Long employeeId) throws ResourceNotFoundException;

}
