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
import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.services.CompanyService;

@RestController
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@PostMapping("/company/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addCompany(@RequestBody Company company) {

		return companyService.addCompany(company);
	}

	@GetMapping("/company/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getCompany(@PathVariable(value = "id") Long companyId) 
			throws ResourceNotFoundException {

		return companyService.getCompany(companyId);
	}

	@GetMapping("/company/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllCompany() throws ResourceNotFoundException {

		return companyService.getAllCompany();
	}

	@GetMapping("/company/all/department/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllDepartment() throws ResourceNotFoundException {

		return companyService.getAllDepartment();
	}

	@GetMapping("/company/all/location/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllLocation() throws ResourceNotFoundException {

		return companyService.getAllLocation();
	}

	@GetMapping("/company/all/department/all/designation/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllDesignation() throws ResourceNotFoundException {

		return companyService.getAllDesignation();
	}

	@PutMapping("/company/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateCompany(@PathVariable(value = "id") Long companyId,
			@RequestBody Company companyDetails) throws ResourceNotFoundException {

		return companyService.updateCompany(companyId, companyDetails);
	}

	@DeleteMapping("/company/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteCompany(@PathVariable(value = "id") Long companyId)
			throws ResourceNotFoundException {

		return companyService.deleteCompany(companyId);
	}

	// All employee under a specific company
	@GetMapping("/company/{id}/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllEmployeeBasedOnCompany( @PathVariable(value="id") Long companyId )
			throws ResourceNotFoundException {

		return companyService.getAllEmployeeBasedOnCompany(companyId);
	}

	@GetMapping("/company/{id}/department/count_employee")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> countEmployeeBasedOnDepartment( @PathVariable(value="id") Long companyId )
			throws ResourceNotFoundException {

		return companyService.countEmployeeBasedOnDepartment(companyId);
	}
	
	


}
