package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.payload.request.PersonalInformation;
import com.dbl.nsl.erp.payload.request.PublicInformation;

public interface EmployeeService {

	ResponseEntity<?> createEmployee(String empJson, MultipartFile file) throws ResourceNotFoundException;

	ResponseEntity<?> updateProfilePicture(MultipartFile file, Long employeeId) throws ResourceNotFoundException;

	ResponseEntity<?> getEmployee(Long employeeId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllEmployee() throws ResourceNotFoundException;

	ResponseEntity<?> getAllEmployeeExceptSuperAdmin() throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllEmployeeExceptSuperAdminAndActiveForUserAndAdmin() throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllEmployeeExceptSuperAdminAndActive() throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllEmployeeExceptSuperAdminAndInactive() throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllActiveEmployee() throws ResourceNotFoundException;
	
	ResponseEntity<?> getOfficialInformation(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getPersonalInformation(Long employeeId) throws ResourceNotFoundException;

	ResponseEntity<?> getPublicInformation(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllLineManager() throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllTeamLeader() throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllHeadOfDepartment() throws ResourceNotFoundException;
	
	ResponseEntity<?> updateEmployeeOfficialInfo(Long employeeId, Employee employeeDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateLineManager(Long employeeId, Employee employeeDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> updatePersonalInformation(Long employeeId, PersonalInformation personalInformationDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> updatePublicInformation(Long employeeId, PublicInformation publicInformation) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateStatus(Long employeeId) throws ResourceNotFoundException;


	
}
