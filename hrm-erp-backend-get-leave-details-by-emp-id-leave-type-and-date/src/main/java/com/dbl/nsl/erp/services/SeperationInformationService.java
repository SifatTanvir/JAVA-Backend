package com.dbl.nsl.erp.services;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.SeperationInformation;

public interface SeperationInformationService {

	ResponseEntity<?> addSeperationInformation(String seperationJson, MultipartFile file)
			throws ResourceNotFoundException;

	ResponseEntity<?> updateSeperationInformation(String seperationJson, MultipartFile file, Long seperationId)
			throws ResourceNotFoundException;

	ResponseEntity<?> deleteSeperationInformation(Long seperationId) throws ResourceNotFoundException, ParseException;

	ResponseEntity<?> getSeperationInformationById(Long seperationId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllSeperationInformation() throws ResourceNotFoundException;

}
