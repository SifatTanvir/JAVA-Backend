package com.dbl.nsl.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Location;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.LocationRepository;


@Service
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	LocationRepository locationRepository;

	@Override
	public ResponseEntity<?> postCompanyLocation(Long companyId, Location locationDetails) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        company.getLocation().add(locationDetails);
        companyRepository.save(company);
		return ResponseEntity.ok().body(company);
	}

	@Override
	public ResponseEntity<?> updateCompany(Long companyId, Long locationId, Location locationDetails) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Location location = locationRepository.findById(locationId)
				.orElseThrow(() -> new ResourceNotFoundException("Location not found"));

		location.setAlias(locationDetails.getAlias());
		location.setAddress(locationDetails.getAddress());
		location.setDistrict(locationDetails.getDistrict());
		location.setThana(locationDetails.getThana());
		final Location updatedLocation = locationRepository.save(location);
		return ResponseEntity.ok(updatedLocation);
	}

	@Override
	public ResponseEntity<?> deleteLocation(Long companyId, Long locationId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Location location = locationRepository.findById(locationId)
				.orElseThrow(() -> new ResourceNotFoundException("Location not found"));
		locationRepository.delete(location);
		
		return ResponseEntity.ok().body( new MessageResponse("Location deleted Successfully"));
	}

	@Override
	public ResponseEntity<?> getLocation(Long companyId, Long locationId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Location location = locationRepository.findById(locationId)
				.orElseThrow(() -> new ResourceNotFoundException("Location not found"));
		return ResponseEntity.ok().body(location);
	}

	@Override
	public ResponseEntity<?> getAllLocation(Long companyId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		List<Location> location = company.getLocation();
		return ResponseEntity.ok().body(location);
	}

}
