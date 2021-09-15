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
import com.dbl.nsl.erp.services.HolidayDocService;
import com.dbl.nsl.erp.services.HolidayService;

@RestController
public class HolidayDocController {

	@Autowired
	HolidayDocService holidayDocService;
	
	@RequestMapping(value = "/holidaydoc/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addHolidayDoc( @RequestParam(value = "holidayDocJson", required = true) String holidayDocJson, 
			@RequestParam(required = false, value = "file") MultipartFile file) throws ParseException, ResourceNotFoundException {

		return holidayDocService.createHolidayDoc(holidayDocJson, file);
	}

	@Transactional
	@RequestMapping(value = "/holidaydoc/{id}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateHolidayDoc( @RequestParam(value = "holidayDocJson", required = true) String holidayDocJson,
			@RequestParam(required = false, value = "file") MultipartFile file, @PathVariable(value = "id") Long holidayDocId ) 
					throws ResourceNotFoundException, ParseException {

		return holidayDocService.updateHolidayDoc(holidayDocJson, file, holidayDocId);
	}

	@Transactional
	@DeleteMapping("/holidaydoc/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteHolidayDoc(@PathVariable(value = "id") Long holidayDocId)
			throws ResourceNotFoundException, ParseException {

		return holidayDocService.deleteHolidayDoc(holidayDocId);
	}

	@GetMapping("/holidaydoc/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getHolidayDocById(@PathVariable(value = "id") Long holidayId)
			throws ResourceNotFoundException {

		return holidayDocService.getHolidayDoc(holidayId);
	}

	@GetMapping("/holidaydoc/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or hasRole('USER')")
	public  ResponseEntity<?> getAllHolidayDoc() throws ResourceNotFoundException {

		return holidayDocService.getAllHolidayDoc();
	}
	
	
}
