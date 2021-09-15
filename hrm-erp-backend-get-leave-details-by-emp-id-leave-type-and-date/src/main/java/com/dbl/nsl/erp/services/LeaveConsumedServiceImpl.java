package com.dbl.nsl.erp.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Attendance;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.Holiday;
import com.dbl.nsl.erp.models.LeaveRequest;
import com.dbl.nsl.erp.repository.AttendanceRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.HolidayListRepository;
import com.dbl.nsl.erp.repository.HolidayRepository;
import com.dbl.nsl.erp.repository.LeaveConsumedRepository;
import com.dbl.nsl.erp.repository.LeaveRequestRepository;

@Service
public class LeaveConsumedServiceImpl implements LeaveConsumedService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	LeaveConsumedRepository leaveConsumedRepository;

	@Autowired
	AttendanceRepository attendanceRepository;

	@Autowired
	HolidayListRepository holidayListRepository;
	
	@Autowired
	HolidayRepository holidayRepository;
	
	@Autowired
	LeaveRequestRepository leaveRequestRepository;

	@Override
	public ResponseEntity<?> getLeaveInfo(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		return ResponseEntity.ok().body(employee.getLeaveConsumed());
	}

	// this function generates leave related map for showing holiday and leave
	@Override
	public ResponseEntity<?> leaveReport(Long employeeId, Date startdate, Date enddate)
			throws ResourceNotFoundException {

		// create map of string and list of dates
		Map<String, List<Date>> statusAndDates = new HashMap<>();

		// create list of dates
		List<Date> emptyDates = new ArrayList<Date>();

		statusAndDates.put("holiday", emptyDates);
		statusAndDates.put("casual", emptyDates);
		statusAndDates.put("annual", emptyDates);
		statusAndDates.put("sick", emptyDates);
		statusAndDates.put("maternity", emptyDates);
		statusAndDates.put("unpaid", emptyDates);
		statusAndDates.put("AFL", emptyDates);

		//iterate each key from map and then query from repository using employee id, key, start date and end date
		for (String leaveType : statusAndDates.keySet()) {
			List<Date> dates = new ArrayList<Date>();
			if (leaveType.equals("holiday")) {
				dates = holidayListRepository.findAllDateByEmpIdAndDateRange(startdate, enddate);
				statusAndDates.put(leaveType, dates);
			} else {
				dates = attendanceRepository.findSickDatesByEmpIdAndStatusAndDateBetween(employeeId, leaveType, startdate,
						enddate);
				statusAndDates.put(leaveType, dates);
			}
		}

		return ResponseEntity.ok().body(statusAndDates);
	}

	@Override
	public ResponseEntity<?> leaveReportByEmpIdAndDate(Long employeeId, String type, Date date) {
		
		if(type.equals("holiday")) {
			Holiday holiday = holidayRepository.findByDate(date, date);
			return ResponseEntity.ok().body(holiday);
		}else {
			LeaveRequest leaveRequest = leaveRequestRepository.findByEmpIdAndTypeAndDate(employeeId, type, date);
			return ResponseEntity.ok().body(leaveRequest);
		}
	}

}
