package com.dbl.nsl.erp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.SalaryBenefit;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.SalaryBenefitRepository;

@Service
public class SalaryBenefitServiceImpl implements SalaryBenefitService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	SalaryBenefitRepository salaryBenefitRepository;

	@Override
	public ResponseEntity<?> getSalaryInformation(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		
		SalaryBenefit salaryBenefit = employee.getSalaryBenefit();
		if ( salaryBenefit == null ) throw new ResourceNotFoundException("Salary Benefit not found");

		return ResponseEntity.ok(salaryBenefit);
	}

	@Override
	public ResponseEntity<?> addSalaryBenefit(Long employeeId, SalaryBenefit salaryBenefit) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		
		employee.setSalaryBenefit(salaryBenefit);
		final Employee updatedemployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedemployee);
	}

	@Override
	public ResponseEntity<?> deleteEmployeeSalaryBenefit(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		employee.setSalaryBenefit(null); 
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	
	
	

}
