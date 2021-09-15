package com.dbl.nsl.erp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.EmergencyContact;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.EmployeeRepository;


@Service
public class EmergencyContactServiceImpl implements EmergencyContactService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public ResponseEntity<?> getEmergencyContact(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		
		EmergencyContact emergencyContact = employee.getEcontact();
		if ( emergencyContact == null ) 
			return ResponseEntity.ok().body( new MessageResponse( "Emergency Contact Not Found") );
		return ResponseEntity.ok(emergencyContact);
	}

	@Override
	public ResponseEntity<?> updateEmergencyContact(Long employeeId, EmergencyContact emergencyContact) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		employee.setEcontact(emergencyContact);
		final Employee updatedemployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedemployee);
	}

	@Override
	public ResponseEntity<?> deleteEmergencyContact(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		employee.setEcontact(null); // Set emergency_contact field null 
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	
}
