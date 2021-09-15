package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;

public interface ExperienceService {

	ResponseEntity<?> createExperience( String expJson,  MultipartFile file, Long employeeId) throws ResourceNotFoundException;

	ResponseEntity<?> updateExperience( String expJson, MultipartFile file, Long employeeId, Long experienceId) throws ResourceNotFoundException;

	ResponseEntity<?> getExperience( Long employeeId, Long experienceId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllExperience(Long employeeId) throws ResourceNotFoundException;

	ResponseEntity<?> deleteExperience(Long employeeId, Long experienceId) throws ResourceNotFoundException;
	

}
