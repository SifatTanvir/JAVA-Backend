package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.AttendanceRoaster;
import com.dbl.nsl.erp.services.AttendanceRoasterService;

@RestController
public class AttendanceRoasterController {
	
	@Autowired
	AttendanceRoasterService attendanceRoasterService;

	@GetMapping("/attendance_roaster/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllAttendanceRoaster() throws ResourceNotFoundException {
		
		return attendanceRoasterService.getAllAttendanceRoaster();
	}

	@PostMapping("/attendance_roaster/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> addAttendanceRoaster(@RequestBody AttendanceRoaster attendanceRoaster) {
		
		return attendanceRoasterService.addAttendanceRoaster(attendanceRoaster);
	}

	@GetMapping("/attendance_roaster/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAttendanceRoaster(@PathVariable(value = "id") Long attendaceRoasterId)
			throws ResourceNotFoundException {
		
		return attendanceRoasterService.getAttendanceRoaster(attendaceRoasterId);
	}

	@GetMapping("/attendance_roaster/{id}/employees")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllEmployees(@PathVariable(value = "id") Long attenadanceRoasterId)
			throws ResourceNotFoundException {
		
		return attendanceRoasterService.getAllEmployees(attenadanceRoasterId);
	}
	
	@PutMapping("/attendance_roaster/{id}/update")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateAttendanceRoaster(@PathVariable(value = "id") Long attendaceRoasterId,
			@RequestBody AttendanceRoaster attendanceRoasterDetails ) throws ResourceNotFoundException {
		
		return attendanceRoasterService.updateAttendanceRoaster(attendaceRoasterId, attendanceRoasterDetails);
	}
	
	@PutMapping("/employee/{id1}/attendance_roaster/{id2}/update")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateEmployee(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long attendanceRoasterId) throws ResourceNotFoundException {
		
		return attendanceRoasterService.updateEmployee(employeeId, attendanceRoasterId);
	}
	
	
	

}
