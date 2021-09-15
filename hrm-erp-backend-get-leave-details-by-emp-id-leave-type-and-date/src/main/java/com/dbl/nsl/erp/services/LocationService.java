package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Location;

public interface LocationService {
	
	ResponseEntity<?> postCompanyLocation( Long companyId, Location locationDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateCompany(Long companyId, Long locationId, Location locationDetails) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteLocation(Long companyId, Long locationId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getLocation( Long companyId, Long locationId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllLocation(Long companyId) throws ResourceNotFoundException;

}
