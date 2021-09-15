package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;

public interface HolidayDocService {
	
	ResponseEntity<?> createHolidayDoc( String holidayDocJson,  MultipartFile file) throws ResourceNotFoundException;

	ResponseEntity<?> updateHolidayDoc( String holidayDocJson, MultipartFile file, Long holidayDocId) throws ResourceNotFoundException;

	ResponseEntity<?> getHolidayDoc( Long holidayDocId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllHolidayDoc() throws ResourceNotFoundException;

	ResponseEntity<?> deleteHolidayDoc( Long holidayDocId) throws ResourceNotFoundException;
	

}
