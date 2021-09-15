package com.dbl.nsl.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.LeavePolicy;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.LeavePolicyRepository;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private LeavePolicyRepository leavePolicyRepository;

	@Override
	public ResponseEntity<?> addLeavePolicy(LeavePolicy leavePolicyDetails) {

		final LeavePolicy leavePolicy = leavePolicyRepository.save(leavePolicyDetails);
		return ResponseEntity.ok().body(leavePolicy);
	}

	@Override
	public ResponseEntity<?> getLeavePolicy(Long leavePolicyId) throws ResourceNotFoundException {

		LeavePolicy leavePolicy = leavePolicyRepository.findById(leavePolicyId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave Policy not found"));
		return ResponseEntity.ok().body(leavePolicy);
	}

	@Override
	public ResponseEntity<?> allLeavePolicy() throws ResourceNotFoundException {

		List<LeavePolicy> allLeavePolicy = leavePolicyRepository.findAll();
		if( allLeavePolicy.isEmpty() ) throw new ResourceNotFoundException("Leave Policy not found");
		return ResponseEntity.ok().body(allLeavePolicy);
	}

	@Override
	public ResponseEntity<?> updateLeavePolicy(Long leavePolicyId, LeavePolicy leavePolicyDetails) throws ResourceNotFoundException {
		LeavePolicy leavePolicy = leavePolicyRepository.findById(leavePolicyId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave Policy not found"));

		leavePolicy.setPolicyName(leavePolicyDetails.getPolicyName());
		leavePolicy.setSick(leavePolicyDetails.getSick());
		leavePolicy.setAnnual(leavePolicyDetails.getAnnual());
		leavePolicy.setCasual(leavePolicyDetails.getCasual());
		leavePolicy.setMaternity(leavePolicyDetails.getMaternity());
		leavePolicy.setOther(leavePolicyDetails.getOther());
		leavePolicyRepository.save(leavePolicy);

		return ResponseEntity.ok().body(leavePolicy);
	}

	@Override
	public ResponseEntity<?> allEmployees(Long leavePolicyId) throws ResourceNotFoundException {

		LeavePolicy leavePolicy = leavePolicyRepository.findById(leavePolicyId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave Policy not found"));

		List<Employee> employees = leavePolicy.getEmployee();
		if( employees.size() == 0 ) return ResponseEntity.ok().body( new MessageResponse("Employee not found"));
		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> updateEmployeeLeavePolicy(Long employeeId, Long leavePolicyId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		LeavePolicy leavePolicy = leavePolicyRepository.findById(leavePolicyId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave Policy not found"));

		employee.setLeavePolicy(leavePolicy);
		final Employee savedEmployee = employeeRepository.save(employee);

		return ResponseEntity.ok().body(savedEmployee);
	}





}
