package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.SalaryBenefit;

public interface SalaryBenefitService {
	
	ResponseEntity<?> getSalaryInformation(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> addSalaryBenefit(Long employeeId, SalaryBenefit salaryBenefit) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteEmployeeSalaryBenefit(Long employeeId) throws ResourceNotFoundException;
	

}
