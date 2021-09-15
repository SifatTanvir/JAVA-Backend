package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;

public interface EducationService {
	
	ResponseEntity<?> createEducation(String eduJson, MultipartFile file, Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateEducation(String eduJson, MultipartFile file, Long employeeId, Long educationId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getEducation(Long employeeId, Long educationId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllEducation(Long employeeId) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteEducation(Long employeeId, Long educationId) throws ResourceNotFoundException;


}
