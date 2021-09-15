package com.dbl.nsl.erp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.Nominee;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.NomineeRepository;

@Service
public class NomineeServiceImpl implements NomineeService{


	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	NomineeRepository nomineeRepository;

	@Override
	public ResponseEntity<?> addNominee(Long employeeId, Nominee nomineeDetails) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		nomineeDetails.setEmployee(employee);
		nomineeRepository.save(nomineeDetails);
		return ResponseEntity.ok(employee);
	}

	@Override
	public ResponseEntity<?> getNominees(Long employeeId) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		List<Nominee> nominees = employee.getNominee();
		List<Nominee> activeNominee = new ArrayList<>();
		for(Nominee nominee:nominees) { 
			if( nominee.isDeleted() == false ) 
				activeNominee.add(nominee);
		}
		
		if (nominees.isEmpty()) throw new ResourceNotFoundException("Nominee not found");
		return ResponseEntity.ok(activeNominee);
	}

	@Override
	public ResponseEntity<?> getNominee(Long employeeId, Long nomineeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Nominee nominee = nomineeRepository.findById(nomineeId)
				.orElseThrow(() -> new ResourceNotFoundException("Nominee not found"));

		return ResponseEntity.ok(nomineeRepository.findById(nomineeId));
	}

	@Override
	public ResponseEntity<?> updateNominee(Long employeeId, List<Nominee> nomineeList) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		
		List<Nominee> nominees = employee.getNominee();
		
		for(Nominee nominee:nominees) {
			nominee.setDeleted(true);
			nomineeRepository.save(nominee);
		}

		for (Nominee nomineeDetails : nomineeList) {
			if ( nomineeDetails.getId() != null ) {
				
				Nominee oldNominee = nomineeRepository.findById(nomineeDetails.getId())
						.orElseThrow(() -> new ResourceNotFoundException("Nominee not found"));

				oldNominee.setName(nomineeDetails.getName());
				oldNominee.setBirthDate(nomineeDetails.getBirthDate());
				oldNominee.setRelation(nomineeDetails.getRelation());
				oldNominee.setAddress(nomineeDetails.getAddress());
				oldNominee.setMobile(nomineeDetails.getMobile());
				oldNominee.setPercentage(nomineeDetails.getPercentage());
				oldNominee.setDeleted(false);
				nomineeRepository.save(oldNominee);
				
			} else {
				nomineeDetails.setEmployee(employee);
				nomineeDetails.setDeleted(false);
				Nominee nominee = nomineeRepository.save(nomineeDetails);
				employee.getNominee().add(nominee);
			}
		}
		
		Employee savedEmployee = employeeRepository.save(employee);

		return ResponseEntity.ok(savedEmployee);
	}

	@Override
	public ResponseEntity<?> deleteNominee(Long employeeId, Long nomineeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Nominee nominee = nomineeRepository.findById(nomineeId)
				.orElseThrow(() -> new ResourceNotFoundException("Nominee not found"));

		nominee.setEmployee(null);
		nomineeRepository.save(nominee);
		return ResponseEntity.ok("Nominee Deleted successfully");
	}

}
