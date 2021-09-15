package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.services.TeamLeaderService;

@RestController
public class TeamLeaderController {
	
	@Autowired
	TeamLeaderService teamLeaderService;
	
	@GetMapping("/employee/{id}/team_leader")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getTeamLeader(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		
		return teamLeaderService.getTeamLeader(employeeId);
	}
	
	
	@GetMapping("/team_leader/{id}/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getChildEmployee(@PathVariable(value = "id") Long teamLeaderId)
			throws ResourceNotFoundException {
		
		return teamLeaderService.getChildEmployee(teamLeaderId);
	}
	
	
	@DeleteMapping("/team_leader/{id1}/employee/{id2}/delete")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateTeamLeader(@PathVariable(value = "id1") long teamLeaderId,
			@PathVariable(value = "id2") long employeeId) throws ResourceNotFoundException {
				
		return teamLeaderService.updateTeamLeader(teamLeaderId, employeeId);
	}
	
	

}
