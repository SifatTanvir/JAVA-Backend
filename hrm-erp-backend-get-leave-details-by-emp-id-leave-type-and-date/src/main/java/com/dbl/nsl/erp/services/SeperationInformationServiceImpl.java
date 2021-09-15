package com.dbl.nsl.erp.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.FileUploadUtil;
import com.dbl.nsl.erp.models.SeperationInformation;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.SeperationInformationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SeperationInformationServiceImpl implements SeperationInformationService {

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private SeperationInformationRepository seperationInformationRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public ResponseEntity<?> addSeperationInformation(String seperationJson, MultipartFile file)
			throws ResourceNotFoundException {

		SeperationInformation seperationInformation = new SeperationInformation();

		try {
			seperationInformation = objectMapper.readValue(seperationJson, SeperationInformation.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse("Failed to process the data"));
		}

		Employee employee = employeeRepository.findById(seperationInformation.getEmployeeId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		employee.setActive(false);
		seperationInformation.setFirstName(employee.getFirstName());
		seperationInformation.setLastName(employee.getLastName());

		employeeRepository.save(employee);

		try {
			String uploadDir = "src/main/resources/static/img/";
			String fileName = "sep/" + FileUploadUtil.saveFile(employee.getId(), uploadDir, file);
			seperationInformation.setSeperationPicPath(fileName);
		} catch (Exception e) {

		}

		final SeperationInformation savedSeperationInformation = seperationInformationRepository
				.save(seperationInformation);
		return ResponseEntity.ok(savedSeperationInformation);

	}

	@Override
	public ResponseEntity<?> updateSeperationInformation(String seperationJson, MultipartFile file, Long seperationId)
			throws ResourceNotFoundException {

		SeperationInformation seperationInformationDetails = new SeperationInformation();

		try {
			seperationInformationDetails = objectMapper.readValue(seperationJson, SeperationInformation.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse("Failed to process the data"));
		}

		Employee employee = employeeRepository.findById(seperationInformationDetails.getEmployeeId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		String fileName = null;
		try {
			String uploadDir = "src/main/resources/static/img/";
			fileName = "sep/" + FileUploadUtil.saveFile(employee.getId(), uploadDir, file);
		} catch (Exception e) {

		}

		employee.setActive(false);
		seperationInformationDetails.setFirstName(employee.getFirstName());
		seperationInformationDetails.setLastName(employee.getLastName());

		employeeRepository.save(employee);

		SeperationInformation seperationInformation = seperationInformationRepository.findById(seperationId)
				.orElseThrow(() -> new ResourceNotFoundException("Seperation Information not found"));

		seperationInformation.setEmployeeId(seperationInformationDetails.getEmployeeId());
		seperationInformation.setFirstName(seperationInformationDetails.getFirstName());
		seperationInformation.setLastName(seperationInformationDetails.getLastName());
		seperationInformation.setDateOfSeperation(seperationInformationDetails.getDateOfSeperation());
		seperationInformation.setNoticePeriod(seperationInformationDetails.getNoticePeriod());
		seperationInformation.setSeperationType(seperationInformationDetails.getSeperationType());
		seperationInformation.setSeperationReason(seperationInformationDetails.getSeperationReason());
		seperationInformation.setDateOfResignation(seperationInformationDetails.getDateOfResignation());
		seperationInformation.setDateOfSalaryEntitlement(seperationInformationDetails.getDateOfSalaryEntitlement());
		seperationInformation.setSeperationPicPath(fileName);

		final SeperationInformation updatedseperationInformation = seperationInformationRepository
				.save(seperationInformation);
		return ResponseEntity.ok(updatedseperationInformation);
	}

	@Override
	public ResponseEntity<?> deleteSeperationInformation(Long seperationId)
			throws ResourceNotFoundException, ParseException {

		SeperationInformation seperationInformation = seperationInformationRepository.findById(seperationId)
				.orElseThrow(() -> new ResourceNotFoundException("Seperation Information not found"));

		seperationInformationRepository.delete(seperationInformation);
		return ResponseEntity.ok().body("Seperation Information Deleted Successfully");
	}

	@Override
	public ResponseEntity<?> getSeperationInformationById(Long seperationId) throws ResourceNotFoundException {

		SeperationInformation seperationInformation = seperationInformationRepository.findById(seperationId)
				.orElseThrow(() -> new ResourceNotFoundException("Seperation Information not found"));
		return ResponseEntity.ok().body(seperationInformation);
	}

	@Override
	public ResponseEntity<?> getAllSeperationInformation() throws ResourceNotFoundException {

		List<SeperationInformation> seperationInformations = new ArrayList<SeperationInformation>();
		seperationInformations = this.seperationInformationRepository.findAll();
		if (seperationInformations.isEmpty())
			throw new ResourceNotFoundException("Seperation Information not found");

		return ResponseEntity.ok().body(seperationInformations);
	}

}
