package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Certification;

public interface CertificationService {
	
	ResponseEntity<?> addCertification(Long employeeId, Certification certificationDetails) throws ResourceNotFoundException;

	ResponseEntity<?> getCertification( Long employeeId, Long certificationId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllCertification(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateCertification(Long employeeId, Long certificationId, Certification certificationDetails ) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteCertification(Long employeeId, Long certificationId) throws ResourceNotFoundException;

}
