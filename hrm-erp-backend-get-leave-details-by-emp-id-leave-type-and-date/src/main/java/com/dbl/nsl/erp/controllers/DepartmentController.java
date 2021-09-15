package com.dbl.nsl.erp.controllers;

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
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.services.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	
	@PostMapping("/company/{id}/department/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addCompanyLocation(@PathVariable(value = "id") Long companyId,
			@RequestBody Department departmentDetails) throws ResourceNotFoundException {

		return departmentService.addCompanyLocation(companyId, departmentDetails);
	}

	@GetMapping("/department/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllDepartment() throws ResourceNotFoundException {
		
		return departmentService.getAllDepartment();
	}
	
	@PutMapping("/company/{id1}/department/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updatedDepartment(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long departmentId, @RequestBody Department departmentDetails)
			throws ResourceNotFoundException {
		
		return departmentService.updatedDepartment(companyId, departmentId, departmentDetails);
	}
	
	@DeleteMapping("/company/{id1}/department/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long departmentId) throws ResourceNotFoundException {
	
		return departmentService.deleteDepartment(companyId, departmentId);
	}
	
	@GetMapping("/company/{id1}/department/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getDepartment(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long departmentId) throws ResourceNotFoundException {
		
		return departmentService.getDepartment(companyId, departmentId);
	}
	
	@GetMapping("/company/{id1}/department/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllDepartment(@PathVariable(value = "id1") Long companyId)
			throws ResourceNotFoundException {
		
		return departmentService.getAllDepartment(companyId);
	}

	
	@GetMapping("/departments/admin")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public  ResponseEntity<?> getAllDepartmentInformation() throws ResourceNotFoundException {
		
		return departmentService.getAllDepartmentInformation();
	}
	
	// All employee under a specific department
	@GetMapping("/department/{id}/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllEmployee( @PathVariable(value="id") Long departmentId )
			throws ResourceNotFoundException {
		
		return departmentService.getAllEmployee(departmentId);
	}
	
	
	@GetMapping("company/{id1}/department/{id2}/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllEmployeeByCompanyIdAndDeptId( @PathVariable(value="id1") Long companyId, 
			@PathVariable(value="id2") Long departmentId )
			throws ResourceNotFoundException {
		
		return departmentService.getAllEmployeeByCompanyIdAndDepartmentId(companyId, departmentId);
	}
	
	
	
	
	
}


