package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.payload.request.PersonalInformation;
import com.dbl.nsl.erp.services.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/employee/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> createEmployee(@RequestParam(value = "empJson", required = true) String empJson,
			@RequestParam(required = false, value = "file") MultipartFile file) throws ResourceNotFoundException  {

		return employeeService.createEmployee(empJson,file);
	}    

	@RequestMapping(value = "/employee/{id}/profile_picture/update", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> updateProfilePicture(@RequestParam(required = false, value = "file") MultipartFile file,
			@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException  {

		return employeeService.updateProfilePicture(file,employeeId);
	}
		
	@GetMapping("/employee/{id}")
	@PreAuthorize(" hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId) ")
	public ResponseEntity<?> getEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {

		return employeeService.getEmployee(employeeId);
	}
    
	@GetMapping("/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllEmployee() throws ResourceNotFoundException {
		
		return employeeService.getAllEmployee();
	}
	
	@GetMapping("/employees/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') ")
	public ResponseEntity<?> getAllEmployeeExceptSuperAdmin() throws ResourceNotFoundException {
		
		return employeeService.getAllEmployeeExceptSuperAdmin();
	}
	
//	@GetMapping("employee/active")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
//	public ResponseEntity<?> getAllActiveEmployee() throws ResourceNotFoundException {
//		
//		return employeeService.getAllEmployeeExceptSuperAdmin();
//	}
	
	@GetMapping("employee/active")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllActiveEmployeeExceptSuperAdmin() throws ResourceNotFoundException {
		
		return employeeService.getAllEmployeeExceptSuperAdminAndActive();
	}
	
	@GetMapping("employee/inactive")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllInactiveEmployeeExceptSuperAdmin() throws ResourceNotFoundException {
		
		return employeeService.getAllEmployeeExceptSuperAdminAndInactive();
	}
	
	@GetMapping("employee/contact_details")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAllActiveEmployeeExceptSuperAdminForAll() throws ResourceNotFoundException {
		
		return employeeService.getAllEmployeeExceptSuperAdminAndActiveForUserAndAdmin();
	}
	
	@GetMapping("/employee/{id}/official")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getOfficialInformation(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
				
		return employeeService.getOfficialInformation(employeeId);
	}
	
	@GetMapping("/employee/{id}/personal")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getPersonalInformation(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
				
		return employeeService.getPersonalInformation(employeeId);
	}
	
	@GetMapping("/employee/{id}/public")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getPublicInformation(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
				
		return employeeService.getPersonalInformation(employeeId);
	}
	
	@GetMapping("/employee/line_manager")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllLineManager() throws ResourceNotFoundException {		
		
		return employeeService.getAllLineManager();
	}
	
	@GetMapping("/employee/team_leader")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllTeamLeader() throws ResourceNotFoundException {		
		
		return employeeService.getAllTeamLeader();
	}
	
	@GetMapping("/employee/hod")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllHeadOfDepartment() throws ResourceNotFoundException {		
		
		return employeeService.getAllHeadOfDepartment();
	}
	
	
	@PutMapping("/employee/{id}/official/line")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateLineManager(@PathVariable(value = "id") Long employeeId,
			@RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		
		return employeeService.updateLineManager(employeeId, employeeDetails);
	}

	
	@PutMapping("/employee/{id}/official/hr")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateEmployeeOfficialInfo(@PathVariable(value = "id") Long employeeId,
			@RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		
		return employeeService.updateEmployeeOfficialInfo(employeeId, employeeDetails);
	}
	
	
	@PutMapping("/employee/{id}/personal")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> updatePersonalInformation(@PathVariable(value = "id") Long employeeId,
			@RequestBody PersonalInformation personalInformationDetails) throws ResourceNotFoundException {
		
		return employeeService.updatePersonalInformation(employeeId, personalInformationDetails);
	}
	

	@PutMapping("/employee/{id}/status")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long employeeId) 
			throws ResourceNotFoundException {
		
		return employeeService.updateStatus(employeeId);
	}
	
}
