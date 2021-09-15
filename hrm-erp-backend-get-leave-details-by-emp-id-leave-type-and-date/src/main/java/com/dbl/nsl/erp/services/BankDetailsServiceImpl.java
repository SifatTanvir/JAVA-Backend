package com.dbl.nsl.erp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.BankDetails;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.repository.BankDetailsRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;


@Service
public class BankDetailsServiceImpl implements BankDetailsService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	BankDetailsRepository bankDetailsRepository; 
	
	@Override
	public ResponseEntity<?> getSalaryInformation(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		
		BankDetails bankDetails = employee.getBankDetails();
		if ( bankDetails == null ) throw new ResourceNotFoundException("Bank Details not found");
		return ResponseEntity.ok().body(bankDetails);
	}

	@Override
	public ResponseEntity<?> addSalaryBenefit(Long employeeId, BankDetails bankDetails) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		employee.setBankDetails(bankDetails);
		final Employee updatedemployee = employeeRepository.save(employee);
        return ResponseEntity.ok().body(updatedemployee);
	}

	@Override
	public ResponseEntity<?> deleteEmployeeSalaryBenefit(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		employee.setBankDetails(null); // employee_eid field set as null 
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok().body(updatedEmployee);
	}
	
	

}
