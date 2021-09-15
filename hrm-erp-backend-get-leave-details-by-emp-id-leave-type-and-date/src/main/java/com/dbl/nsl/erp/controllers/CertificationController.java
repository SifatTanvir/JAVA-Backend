package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Certification;
import com.dbl.nsl.erp.services.CertificationService;

@RestController
public class CertificationController {
	
	@Autowired
	CertificationService certificateService;
	
	@PostMapping("/employee/{id}/certification/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addCertification(@PathVariable(value = "id") Long employeeId,
			@RequestBody Certification certificationDetails) throws ResourceNotFoundException {
		
		return certificateService.addCertification(employeeId, certificationDetails);
	}

	@PutMapping("/employee/{id1}/certification/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateCertification(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long certificationId, @RequestBody Certification certificationDetails)
			throws ResourceNotFoundException {
	
		return certificateService.updateCertification(employeeId, certificationId, certificationDetails);
	}

	@GetMapping("/employee/{id1}/certification/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getCertification(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long certificationId) throws ResourceNotFoundException {
	
		return certificateService.getCertification(employeeId, certificationId);
	}

	@GetMapping("/employee/{id1}/certification/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAllCertification(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {
	
		return certificateService.getAllCertification(employeeId);
	}
	
	@Transactional
	@DeleteMapping("/employee/{id1}/certification/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> deleteCertification(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long certificationId) throws ResourceNotFoundException {
		
		return certificateService.deleteCertification(employeeId, certificationId);
	}

}
