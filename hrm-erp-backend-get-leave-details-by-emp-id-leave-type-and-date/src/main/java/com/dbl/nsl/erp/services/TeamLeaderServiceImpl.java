package com.dbl.nsl.erp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.TeamLeader;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.TeamLeaderRepository;

@Service
public class TeamLeaderServiceImpl implements TeamLeaderService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	TeamLeaderRepository teamLeaderRepository;

	@Override
	public ResponseEntity<?> getTeamLeader(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Employee teamLeader = employeeRepository.findById(employee.getTeamLeaderId())
				.orElseThrow( () -> new ResourceNotFoundException("Team Leader Not found") );
		return ResponseEntity.ok(teamLeader);
	}

	@Override
	public ResponseEntity<?> getChildEmployee(Long teamLeaderId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(teamLeaderId)
				.orElseThrow(() -> new ResourceNotFoundException("Team Leader not found"));

		TeamLeader teamLeader = teamLeaderRepository.findById(teamLeaderId)
				.orElseThrow(() -> new ResourceNotFoundException("Team Leader not found"));

		List<Long> allChilds = teamLeader.getEmployeeId();
		if( allChilds.isEmpty() ) throw new ResourceNotFoundException("Child Employee not found");

		// we got all child ids now we have to find the object
		List<Employee> employees = new ArrayList<>();
		for( Long id: allChilds ) {
			Optional<Employee> OpE = employeeRepository.findById(id);
			Employee emp = OpE.get(); employees.add(emp);
		}

		return ResponseEntity.ok(employees);
	}

	@Override
	public ResponseEntity<?> updateTeamLeader(Long teamLeaderId, Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found"));
		TeamLeader teamLeader = teamLeaderRepository.findById(teamLeaderId)
				.orElseThrow(() -> new ResourceNotFoundException("Line Manager Not Found"));

		employee.setLineManagerId(null); // deleted from employee table
		employeeRepository.save(employee);

		List<Long> allChilds = teamLeader.getEmployeeId();
		allChilds.remove(employeeId); // employee deleted 
		teamLeader.setEmployeeId(allChilds); 
		teamLeaderRepository.save(teamLeader);

		return ResponseEntity.ok(employee);
	}

}
