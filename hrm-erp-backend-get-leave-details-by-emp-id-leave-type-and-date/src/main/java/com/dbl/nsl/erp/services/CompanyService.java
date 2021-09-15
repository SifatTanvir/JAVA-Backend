package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Company;

public interface CompanyService {

	ResponseEntity<?> addCompany(Company company);

	ResponseEntity<?> getCompany(Long companyId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllCompany() throws ResourceNotFoundException;

	ResponseEntity<?> getAllDepartment() throws ResourceNotFoundException;

	ResponseEntity<?> getAllLocation() throws ResourceNotFoundException;

	ResponseEntity<?> getAllDesignation() throws ResourceNotFoundException;

	ResponseEntity<?> updateCompany( Long companyId, Company companyDetails) throws ResourceNotFoundException;

	ResponseEntity<?> deleteCompany(Long companyId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllEmployeeBasedOnCompany(Long companyId) throws ResourceNotFoundException;
	
	ResponseEntity<?> countEmployeeBasedOnDepartment(Long companyId) throws ResourceNotFoundException;
	
	

}
