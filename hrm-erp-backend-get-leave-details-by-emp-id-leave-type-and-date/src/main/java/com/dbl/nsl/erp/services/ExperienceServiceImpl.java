package com.dbl.nsl.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.Experience;
import com.dbl.nsl.erp.models.FileUploadUtil;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.ExperienceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ExperienceServiceImpl implements ExperienceService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	ExperienceRepository experienceRepository;

	@Override
	public ResponseEntity<?> createExperience(String expJson, MultipartFile file, Long employeeId) throws ResourceNotFoundException {
		
		Experience experience = new Experience();
		try {
			experience = objectMapper.readValue(expJson, Experience.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to process the data"));
		}

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		String fileName = null;
		try {
			 String uploadDir = "src/main/resources/static/exp/";
			 fileName = "exp/" + FileUploadUtil.saveFile( employee.getId(), uploadDir, file );
		} catch( Exception e ) {
			
		}
		
		experience.setExperienceCertificateUrl(fileName); // experience file attach
		experience.setEmployee(employee);
		experienceRepository.save(experience);

		return ResponseEntity.ok(new MessageResponse("Experience registered successfully"));
	}

	@Override
	public ResponseEntity<?> updateExperience(String expJson, MultipartFile file, Long employeeId, Long experienceId) throws ResourceNotFoundException {
		
		Experience experienceDetails;
		try {
			experienceDetails = objectMapper.readValue(expJson, Experience.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to process the data"));
		}

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Experience experience = experienceRepository.findById(experienceId)
				.orElseThrow(() -> new ResourceNotFoundException("Experience not found"));
		
		String fileName = null;
		try {
			 String uploadDir = "src/main/resources/static/exp/";
			 fileName = "edu/" + FileUploadUtil.saveFile( employee.getId(), uploadDir, file );
		} catch( Exception e ) {
			
		}

		experience.setCompany(experienceDetails.getCompany());
		experience.setDesignation(experienceDetails.getDesignation());
		experience.setDuration(experienceDetails.getDuration());
		experience.setStartDate(experienceDetails.getStartDate());
		experience.setEndDate(experienceDetails.getEndDate());
		experience.setExperienceCertificateUrl(fileName); // experience file updated
		experienceRepository.save(experience);

		return ResponseEntity.ok(new MessageResponse("Experience updated successfully"));
	}

	@Override
	public ResponseEntity<?> getExperience(Long employeeId, Long experienceId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Experience experience = experienceRepository.findById(experienceId)
				.orElseThrow(() -> new ResourceNotFoundException("Experience not found"));
		return ResponseEntity.ok().body(experience);
	}

	@Override
	public ResponseEntity<?> getAllExperience(Long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		List<Experience> experiences = employee.getExperience();
		return ResponseEntity.ok().body(experiences);
	}

	@Override
	public ResponseEntity<?> deleteExperience(Long employeeId, Long experienceId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		experienceRepository.deleteById(experienceId);
				
		return ResponseEntity.ok().body( new MessageResponse("Experience Deleted Successfully"));
	}
	
	
	

}
