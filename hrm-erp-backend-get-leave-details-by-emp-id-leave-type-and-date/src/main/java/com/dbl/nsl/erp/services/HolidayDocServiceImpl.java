package com.dbl.nsl.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.FileUploadUtil;
import com.dbl.nsl.erp.models.HolidayDoc;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.HolidayDocRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class HolidayDocServiceImpl implements HolidayDocService {
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	HolidayDocRepository holidayDocRepository; 

	@Override
	public ResponseEntity<?> createHolidayDoc(String holidayJson, MultipartFile file) throws ResourceNotFoundException {
		
		HolidayDoc holidayDoc = new HolidayDoc();
		try {
			holidayDoc = objectMapper.readValue(holidayJson, HolidayDoc.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to process the data"));
		}

		String fileName = null;
		try {
			 String uploadDir = "src/main/resources/static/exp/";
			 fileName = "exp/" + FileUploadUtil.saveFile( 123456l, uploadDir, file );
		} catch( Exception e ) {
			
		}
		
		holidayDoc.setAttachmentPath(fileName);
		holidayDocRepository.save(holidayDoc);

		return ResponseEntity.ok(new MessageResponse("Holiday Document registered successfully"));
	}

	@Override
	public ResponseEntity<?> updateHolidayDoc(String holidayJson, MultipartFile file, Long holidayDocId) throws ResourceNotFoundException {
		
		HolidayDoc holidayDocDetails;
		try {
			holidayDocDetails = objectMapper.readValue(holidayJson, HolidayDoc.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to process the data"));
		}

		HolidayDoc holidayDoc = holidayDocRepository.findById(holidayDocId)
				.orElseThrow(() -> new ResourceNotFoundException("Holiday Doc not found"));
		
		String fileName = null;
		try {
			 String uploadDir = "src/main/resources/static/exp/";
			 fileName = "exp/" + FileUploadUtil.saveFile( 1234560l, uploadDir, file );
		} catch( Exception e ) {
			
		}
		
		holidayDoc.setYear(holidayDocDetails.getYear());
		holidayDoc.setAttachmentPath(fileName); // holiday document updated

		holidayDocRepository.save(holidayDoc);

		return ResponseEntity.ok(new MessageResponse("holiday doc updated successfully"));
	}

	@Override
	public ResponseEntity<?> getHolidayDoc(Long holidayDocId) throws ResourceNotFoundException {
		
		HolidayDoc holidayDoc = holidayDocRepository.findById(holidayDocId)
				.orElseThrow(() -> new ResourceNotFoundException("Holiday Doc not found"));

		return ResponseEntity.ok().body(holidayDoc);
	}

	@Override
	public ResponseEntity<?> getAllHolidayDoc() throws ResourceNotFoundException {
		
		List<HolidayDoc> holidayDoc = holidayDocRepository.findAll();

		return ResponseEntity.ok().body(holidayDoc);
	}

	@Override
	public ResponseEntity<?> deleteHolidayDoc(Long holidayDocId) throws ResourceNotFoundException {
		
		holidayDocRepository.deleteById(holidayDocId);
				
		return ResponseEntity.ok().body( new MessageResponse("Holiday Doc is deleted Successfully"));
	}
}
