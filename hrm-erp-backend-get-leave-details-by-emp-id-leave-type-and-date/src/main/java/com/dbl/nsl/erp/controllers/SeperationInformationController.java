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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.SeperationInformation;
import com.dbl.nsl.erp.services.SeperationInformationService;

@RestController
public class SeperationInformationController {

	@Autowired
	SeperationInformationService seperationInformationService;

	@RequestMapping(value = "/seperation/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addSeperation(
			@RequestParam(value = "seperationJson", required = true) String seperationJson,
			@RequestParam(required = false, value = "file") MultipartFile file)
			throws ParseException, ResourceNotFoundException {

		return seperationInformationService.addSeperationInformation(seperationJson, file);
	}

	@RequestMapping(value = "/seperation/{id}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateSeperation(
			@RequestParam(value = "seperationJson", required = true) String seperationJson,
			@RequestParam(required = false, value = "file") MultipartFile file,
			@PathVariable(value = "id") Long seperationId) throws ResourceNotFoundException {

		return seperationInformationService.updateSeperationInformation(seperationJson, file, seperationId);
	}

	@Transactional
	@DeleteMapping("/seperation/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteSeperation(@PathVariable(value = "id") Long seperationId)
			throws ResourceNotFoundException, ParseException {

		return seperationInformationService.deleteSeperationInformation(seperationId);
	}

	@GetMapping("/seperation/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getSeperationById(@PathVariable(value = "id") Long seperationId)
			throws ResourceNotFoundException {

		return seperationInformationService.getSeperationInformationById(seperationId);
	}

	@GetMapping("/seperation/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllSeperation() throws ResourceNotFoundException {

		return seperationInformationService.getAllSeperationInformation();
	}
}
