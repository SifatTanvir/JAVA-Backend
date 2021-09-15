package com.dbl.nsl.erp.services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Attendance;

public interface AttendanceService {

	ResponseEntity<?> employeeAttendance(List<Map<String, Map<String, Long>>> punchRequest) throws ParseException;

	ResponseEntity<?> getAllByDateBetween(Long employeeId, Date startdate, Date enddate);

	ResponseEntity<?> getAllByDate(String companyName, Date date);

	ResponseEntity<?> getAllChildsAttendance(Long employeeId, String companyName, Date date) throws ResourceNotFoundException;

	ResponseEntity<?> getAllAttendanceByCompanyNameAndDateBetween(String companyName, Date startdate, Date enddate) throws ResourceNotFoundException;

	ResponseEntity<?> getAllByCompanyAndDate( Long companyId, Date date);

	ResponseEntity<?> getAllByDepartmentAndDate(Long departmentId, Date date) throws ResourceNotFoundException;

	ResponseEntity<?> getAllGroupByDate(Date date);

	ResponseEntity<?> updateAttendanceRequest(Long employeeId, Attendance details) throws ResourceNotFoundException;
	
	ResponseEntity<?> updateAttendanceAccept(Long employeeId, Long AttendanceId) throws ResourceNotFoundException, ParseException;
	
	ResponseEntity<?> updateAttendanceReject(Long employeeId, Long AttendanceId) throws ResourceNotFoundException;

	ResponseEntity<?> getAllAttendance() throws ResourceNotFoundException;
	
	ResponseEntity<?> getAllEditAttendanceRequest() throws ResourceNotFoundException;

	ResponseEntity<?> getAttendanceMonthlyReport(Long companyId, Date startdate, Date enddate) throws ResourceNotFoundException;
	

}
