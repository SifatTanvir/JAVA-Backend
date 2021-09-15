package com.dbl.nsl.erp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.HeadOfDepartment;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.HeadOfDepartmentRepository;

@Service
public class HeadOfDeptServiceImpl implements HeadOfDeptService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	HeadOfDepartmentRepository HODRepository;

	@Override
	public ResponseEntity<?> getHeadOfDept(Long employeeId) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Employee HOD = employeeRepository.findById(employee.getHeadOfDepartmentId())
				.orElseThrow( () -> new ResourceNotFoundException("Head Of Dept Not found") );
		return ResponseEntity.ok().body(HOD);
	}

	@Override
	public ResponseEntity<?> getChildEmployee(Long hodId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(hodId)
				.orElseThrow(() -> new ResourceNotFoundException("Head Of Dept not found"));

		HeadOfDepartment HOD = HODRepository.findById(hodId)
				.orElseThrow(() -> new ResourceNotFoundException("Head Of Dept not found"));

		List<Long> allChilds = HOD.getEmployeeId();
		if( allChilds.size() == 0 ) throw new ResourceNotFoundException("Employee not found");

		// we got all child ids now we have to find the object
		List<Employee> employees = new ArrayList<>();
		for( Long id: allChilds ) {
			Optional<Employee> OpE = employeeRepository.findById(id);
			Employee emp = OpE.get(); employees.add(emp);
		}

		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> updateHeadOfDept(Long hodId, Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found"));
		HeadOfDepartment HOD = HODRepository.findById(hodId)
				.orElseThrow(() -> new ResourceNotFoundException("Head Of Dept Not Found"));

		employee.setLineManagerId(null); // deleted from employee table
		employeeRepository.save(employee);

		List<Long> allChilds = HOD.getEmployeeId();
		allChilds.remove(employeeId); // employee deleted 
		HOD.setEmployeeId(allChilds); 
		HODRepository.save(HOD);

		return ResponseEntity.ok().body(employee);
	}

}
