package com.dbl.nsl.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Certification;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.CertificationRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;


@Service
public class CertificationServiceImpl implements CertificationService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CertificationRepository certificationRepository;

	@Override
	public ResponseEntity<?> addCertification(Long employeeId, Certification certificationDetails) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		
		certificationDetails.setEmployee(employee);
		certificationRepository.save(certificationDetails);
		return ResponseEntity.ok().body(employee);
	}

	@Override
	public ResponseEntity<?> getCertification(Long employeeId, Long certificationId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Certification certification = certificationRepository.findById(certificationId)
				.orElseThrow(() -> new ResourceNotFoundException("Certification not found"));
		return ResponseEntity.ok().body(certification);
	}

	@Override
	public ResponseEntity<?> getAllCertification(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		List<Certification> certifications = employee.getCertification();
		if (certifications.isEmpty()) throw new ResourceNotFoundException("Certification not found");
		
		return ResponseEntity.ok().body(certifications);
	}
	
	@Override
	public ResponseEntity<?> updateCertification(Long employeeId, Long certificationId,
			Certification certificationDetails) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Certification certification = certificationRepository.findById(certificationId)
				.orElseThrow(() -> new ResourceNotFoundException("Certification not found"));

		certification.setTitle(certificationDetails.getTitle());
		certification.setDuration(certificationDetails.getDuration());

		final Certification updatedCertification = certificationRepository.save(certification);
		return ResponseEntity.ok().body(updatedCertification);
	}

	@Override
	public ResponseEntity<?> deleteCertification(Long employeeId, Long certificationId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		certificationRepository.deleteById(certificationId);
		
		return ResponseEntity.ok().body( new MessageResponse("Certificate Deleted Successfully"));
	}
	
	
	

}
