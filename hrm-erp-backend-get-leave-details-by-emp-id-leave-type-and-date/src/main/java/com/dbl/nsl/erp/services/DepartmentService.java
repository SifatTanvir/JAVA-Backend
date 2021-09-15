package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Department;

public interface DepartmentService {

	ResponseEntity<?> addCompanyLocation(Long companyId, Department departmentDetails) throws ResourceNotFoundException;

	ResponseEntity<?> getAllDepartment() throws ResourceNotFoundException;

	ResponseEntity<?> updatedDepartment(Long companyId, Long departmentId, Department departmentDetails) throws ResourceNotFoundException;

	ResponseEntity<?> deleteDepartment(Long companyId, Long departmentId) throws ResourceNotFoundException;

	ResponseEntity<?> getDepartment(Long companyId, Long departmentId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllDepartment(Long companyId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllDepartmentInformation() throws ResourceNotFoundException;

	ResponseEntity<?> getAllEmployee(Long departmentId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllEmployeeByCompanyIdAndDepartmentId(Long companyId, Long departmentId) throws ResourceNotFoundException;


}
