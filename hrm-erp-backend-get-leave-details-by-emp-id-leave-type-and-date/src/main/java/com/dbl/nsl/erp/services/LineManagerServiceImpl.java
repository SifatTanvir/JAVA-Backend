package com.dbl.nsl.erp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.LineManager;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.LineManagerRepository;

@Service
public class LineManagerServiceImpl implements LineManagerService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	LineManagerRepository lineManagerRepository;

	@Override
	public ResponseEntity<?> getLineManager(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
     
		Employee lineManager = employeeRepository.findById(employee.getLineManagerId())
				.orElseThrow( () -> new ResourceNotFoundException("Line Manager Not found") );
		return ResponseEntity.ok(lineManager);
	}

	@Override
	public ResponseEntity<?> getChildEmployee(Long lineManagerId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(lineManagerId)
				.orElseThrow(() -> new ResourceNotFoundException("Line Manager not found"));
		
		LineManager lineManager = lineManagerRepository.findById(lineManagerId)
				.orElseThrow(() -> new ResourceNotFoundException("Line Manager not found"));
		
		List<Long> allChilds = lineManager.getEmployeeId();
		if( allChilds.isEmpty() ) throw new ResourceNotFoundException("Employee not found");
		
		// we got all child ids now we have to find the object
		List<Employee> employees = new ArrayList<>();
		for( Long id: allChilds ) {
			Optional<Employee> OpE = employeeRepository.findById(id);
			Employee emp = OpE.get(); employees.add(emp);
		}
		
		return ResponseEntity.ok(employees);
	}

	@Override
	public ResponseEntity<?> updateLineManager(Long lineManagerId, Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found"));
		LineManager lineManager = lineManagerRepository.findById(lineManagerId)
				.orElseThrow(() -> new ResourceNotFoundException("Line Manager Not Found"));
		
		employee.setLineManagerId(null); // deleted from employee table
		employeeRepository.save(employee);
		
		List<Long> allChilds = lineManager.getEmployeeId();
		allChilds.remove(employeeId); // employee deleted 
		lineManager.setEmployeeId(allChilds); 
		lineManagerRepository.save(lineManager);
		
		return ResponseEntity.ok(employee);
	}

}
