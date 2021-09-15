package com.dbl.nsl.erp.services;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;

public interface HolidayService {
	
	ResponseEntity<?> addHoliday(String holidayJson, MultipartFile file) throws ParseException;
	
	ResponseEntity<?> updateHoliday(String holidayJson, MultipartFile file, Long holidayId) throws ResourceNotFoundException, ParseException;
	
	ResponseEntity<?> deleteHoliday(Long holidayId) throws ResourceNotFoundException, ParseException;
	
	ResponseEntity<?> getHolidayById(Long holidayId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllHoliday() throws ResourceNotFoundException;

}
