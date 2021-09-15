package com.dbl.nsl.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Education;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.FileUploadUtil;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.EducationRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EducationRepository educationRepository;

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ResponseEntity<?> createEducation(String eduJson, MultipartFile file, Long employeeId) throws ResourceNotFoundException {

		Education education = new Education();
		try {
			education = objectMapper.readValue(eduJson, Education.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to process the data"));
		}

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		String fileName = null;
		try {
			String uploadDir = "src/main/resources/static/edu/";
			fileName = "edu/" + FileUploadUtil.saveFile( employee.getId(), uploadDir, file );
		} catch( Exception e ) {

		}

		education.setEducationCertificateUrl(fileName); // education certificate added
		education.setEmployee(employee);
		educationRepository.save(education);

		return ResponseEntity.ok(new MessageResponse("Education registered successfully"));
	}

	@Override
	public ResponseEntity<?> updateEducation(String eduJson, MultipartFile file, Long employeeId, Long educationId) 
			throws ResourceNotFoundException {

		Education educationDetails = new Education();

		try {
			educationDetails = objectMapper.readValue(eduJson, Education.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to process the data"));
		}

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Education education = educationRepository.findById(educationId)
				.orElseThrow(() -> new ResourceNotFoundException("Education not found"));

		String fileName = null;
		try {
			String uploadDir = "src/main/resources/static/edu/";
			fileName = "edu/" + FileUploadUtil.saveFile( employee.getId(), uploadDir, file );
		} catch( Exception e ) {

		}

		education.setDegree(educationDetails.getDegree());
		education.setInstitute(educationDetails.getInstitute());
		education.setPassingYear(educationDetails.getPassingYear());
		education.setMajor(educationDetails.getMajor());
		education.setGrade(educationDetails.getGrade());
		education.setResult(educationDetails.getResult());
		education.setEducationCertificateUrl(fileName); // education certificate updated
		educationRepository.save(education);

		return ResponseEntity.ok(new MessageResponse("Education updated successfully"));
	}

	@Override
	public ResponseEntity<?> getEducation(Long employeeId, Long educationId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Education education = educationRepository.findById(educationId)
				.orElseThrow(() -> new ResourceNotFoundException("Education not found"));
		return ResponseEntity.ok().body(education);
	}

	@Override
	public ResponseEntity<?> getAllEducation(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		List<Education> educations = employee.getEducation();
		if( educations.isEmpty() ) throw new ResourceNotFoundException("Education not found");

		return ResponseEntity.ok().body(educations);
	}

	@Override
	public ResponseEntity<?> deleteEducation(Long employeeId, Long educationId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		educationRepository.deleteById(educationId);

		return ResponseEntity.ok().body( new MessageResponse("Education Deleted successfull"));
	}
	
	

}
