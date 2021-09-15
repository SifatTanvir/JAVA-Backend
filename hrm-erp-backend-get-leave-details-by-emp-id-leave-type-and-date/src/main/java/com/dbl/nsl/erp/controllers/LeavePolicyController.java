package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.LeavePolicy;
import com.dbl.nsl.erp.services.LeavePolicyService;

@RestController
public class LeavePolicyController {

	@Autowired
	LeavePolicyService leavePolicyService;

	@PostMapping("/leave_policy/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addLeavePolicy(@RequestBody LeavePolicy leavePolicyDetails)
			throws ResourceNotFoundException {
		
		return leavePolicyService.addLeavePolicy(leavePolicyDetails);
	}
	
	@GetMapping("/leave_policy/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
    public ResponseEntity<?> getLeavePolicy(@PathVariable(value = "id") Long leavePolicyId)
        throws ResourceNotFoundException {
       
		return leavePolicyService.getLeavePolicy(leavePolicyId);
    }
	
	@GetMapping("/leave_policy/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> allLeavePolicy() throws ResourceNotFoundException {
		
		return leavePolicyService.allLeavePolicy();
	}
	
	@PutMapping("/leave_policy/{id}/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
    public ResponseEntity<?> updateLeavePolicy(@PathVariable(value = "id") Long leavePolicyId,
    		@RequestBody LeavePolicy leavePolicyDetails) throws ResourceNotFoundException {
      
		return leavePolicyService.updateLeavePolicy(leavePolicyId, leavePolicyDetails);
    }
	
	@GetMapping("/leave_policy/{id}/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> allEmployees( @PathVariable( value = "id" ) Long leavePolicyId )  
			throws ResourceNotFoundException {
	
		return leavePolicyService.allEmployees(leavePolicyId);
	}
	
	@PutMapping("/employee/{id1}/leave_policy/{id2}/update")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateEmployeeLeavePolicy(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long leavePolicyId) throws ResourceNotFoundException {
		
		return leavePolicyService.updateEmployeeLeavePolicy(employeeId, leavePolicyId);
	}
	
	
	
	
}
