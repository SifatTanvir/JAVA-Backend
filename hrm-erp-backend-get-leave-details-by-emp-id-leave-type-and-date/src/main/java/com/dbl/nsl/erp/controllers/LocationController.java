package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Location;
import com.dbl.nsl.erp.services.LocationService;

@RestController
public class LocationController {
	
	@Autowired
	LocationService locationService;
	
	@PostMapping("/company/{id}/location/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> postCompanyLocation(@PathVariable(value = "id") Long companyId,
			@RequestBody Location locationDetails) throws ResourceNotFoundException {
		
		return locationService.postCompanyLocation(companyId, locationDetails);
	}

	@PutMapping("/company/{id1}/location/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateCompany(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long locationId, @RequestBody Location locationDetails)
			throws ResourceNotFoundException {
	
		return locationService.updateCompany(companyId, locationId, locationDetails);
	}

	@DeleteMapping("/company/{id1}/location/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteLocation(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long locationId) throws ResourceNotFoundException {
		
		return locationService.deleteLocation(companyId, locationId);
	}

	@GetMapping("/company/{id1}/location/{id2}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getLocation(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long locationId) throws ResourceNotFoundException {
	
		return locationService.getLocation(companyId, locationId);
	}

	@GetMapping("/company/{id1}/location/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getLocation(@PathVariable(value = "id1") Long companyId)
			throws ResourceNotFoundException {
		
		return locationService.getAllLocation(companyId);
	}
}
