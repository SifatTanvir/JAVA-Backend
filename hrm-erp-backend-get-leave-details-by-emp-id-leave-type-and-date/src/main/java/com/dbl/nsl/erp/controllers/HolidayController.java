package com.dbl.nsl.erp.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.services.HolidayService;

@RestController
public class HolidayController {

	@Autowired
	HolidayService holidayService;
	
	@RequestMapping(value = "/holiday/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addHoliday( @RequestParam(value = "holidayJson", required = true) String holidayJson, 
			@RequestParam(required = false, value = "file") MultipartFile file) throws ParseException {

		return holidayService.addHoliday(holidayJson,file);
	}

	@Transactional
	@RequestMapping(value = "/holiday/{id}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateHoliday( @RequestParam(value = "holidayJson", required = true) String holidayJson,
			@RequestParam(required = false, value = "file") MultipartFile file, @PathVariable(value = "id") Long holidayId ) 
					throws ResourceNotFoundException, ParseException {

		return holidayService.updateHoliday(holidayJson, file, holidayId);
	}

	@Transactional
	@DeleteMapping("/holiday/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteHoliday(@PathVariable(value = "id") Long holidayId)
			throws ResourceNotFoundException, ParseException {

		return holidayService.deleteHoliday(holidayId);
	}

	@GetMapping("/holiday/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getHolidayById(@PathVariable(value = "id") Long holidayId)
			throws ResourceNotFoundException {

		return holidayService.getHolidayById(holidayId);
	}

	@GetMapping("/holiday/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or hasRole('USER')")
	public  ResponseEntity<?> getAllDepartment() throws ResourceNotFoundException {

		return holidayService.getAllHoliday();
	}
	
	
}
