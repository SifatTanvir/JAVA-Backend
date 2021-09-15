package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Designation;

public interface DesignationService {
	
	ResponseEntity<?> addCompanyLocation(Long companyId, Long departmentId, Designation designationDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> updatedDesignation( Long companyId, Long departmentId, Long designationId, Designation designationDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteDesignation(Long companyId, Long departmentId, Long designationId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getDepartment(Long companyId, Long departmentId, Long designationId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllDesignation(Long companyId, Long departmentId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllDesignationInformation();

}
