package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.AttendanceRoaster;

public interface AttendanceRoasterService {
	
	ResponseEntity<?> addAttendanceRoaster(AttendanceRoaster attendanceRoaster);
	
	ResponseEntity<?> getAllAttendanceRoaster() throws ResourceNotFoundException;
	
	ResponseEntity<?> getAttendanceRoaster(Long attendaceRoasterId) throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllEmployees(Long attenadanceRoasterId) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateAttendanceRoaster( Long attendaceRoasterId, AttendanceRoaster attendanceRoasterDetails ) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateEmployee(Long employeeId, Long attendanceRoasterId) throws ResourceNotFoundException;
	
}
