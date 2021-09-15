package com.dbl.nsl.erp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.repository.EmployeeRepository;

@Service
public class SecurityService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public boolean hasEntry( Long employeeId ) throws ResourceNotFoundException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		
		Employee request = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		
		if( request.getUsername().equals(userDetails.getUsername()) ) return true;
		
		return false;
	}
	
	
	public boolean hasAccess(Long employeeId, Long senderId) throws ResourceNotFoundException {

		Employee sender = employeeRepository.findById(senderId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		if( (sender.getLineManagerId() != null  && employeeId.longValue() == sender.getLineManagerId().longValue()) ||
			(sender.getTeamLeaderId() != null  && employeeId.longValue() == sender.getTeamLeaderId().longValue() ) || 
			(sender.getHeadOfDepartmentId() != null  && employeeId.longValue() == sender.getHeadOfDepartmentId().longValue()) ) return true;
		
		else return false;
	}

	
	
}
