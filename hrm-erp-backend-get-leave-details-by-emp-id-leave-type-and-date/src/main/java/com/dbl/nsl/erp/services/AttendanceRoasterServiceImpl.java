package com.dbl.nsl.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.AttendanceRoaster;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.repository.AttendanceRoasterRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;

@Service
public class AttendanceRoasterServiceImpl implements AttendanceRoasterService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	AttendanceRoasterRepository attendanceRoasterRepository;

	@Override
	public ResponseEntity<?> addAttendanceRoaster(AttendanceRoaster attendanceRoaster) {
		
		String inTime = attendanceRoaster.getOfficeStartTime() + ":00";
		String outTime = attendanceRoaster.getOfficeEndTime() + ":00";
		String lateTime = attendanceRoaster.getLateTime() + ":00";

		attendanceRoaster.setOfficeStartTime(inTime);
		attendanceRoaster.setOfficeEndTime(outTime);
		attendanceRoaster.setLateTime(lateTime);

		final AttendanceRoaster savedAttendanceRoaster = attendanceRoasterRepository.save(attendanceRoaster);
		return ResponseEntity.ok().body(savedAttendanceRoaster);
	}
	
	@Override
	public ResponseEntity<?> getAllAttendanceRoaster() throws ResourceNotFoundException {
		
		List<AttendanceRoaster>  attendanceRoasters = attendanceRoasterRepository.findAll();
		if( attendanceRoasters.isEmpty() ) throw new ResourceNotFoundException("Attendance Roaster not found");
		return ResponseEntity.ok().body(attendanceRoasters);
	}

	@Override
	public ResponseEntity<?> getAttendanceRoaster(Long attendaceRoasterId) throws ResourceNotFoundException {
	    AttendanceRoaster attendanceRoaster = attendanceRoasterRepository.findById(attendaceRoasterId)
	    		.orElseThrow( ()-> new ResourceNotFoundException("Attendance Roaster not found"));
	    
	    return ResponseEntity.ok().body(attendanceRoaster);
	}

	@Override
	public ResponseEntity<?> getAllEmployees(Long attenadanceRoasterId) throws ResourceNotFoundException {
		
		AttendanceRoaster attendanceRoster = attendanceRoasterRepository.findById(attenadanceRoasterId)
				.orElseThrow(() -> new ResourceNotFoundException("Attendance Roster Not Found"));
		List<Employee> employee = attendanceRoster.getEmployee();
		if ( employee.isEmpty() ) throw new ResourceNotFoundException("No Employee found under this Roaster");
		
		return ResponseEntity.ok().body(employee);
	}

	@Override
	public ResponseEntity<?> updateAttendanceRoaster(Long attendaceRoasterId,
			AttendanceRoaster attendanceRoasterDetails) throws ResourceNotFoundException {
		
		AttendanceRoaster attendanceRoaster = attendanceRoasterRepository.findById(attendaceRoasterId)
				.orElseThrow(() -> new ResourceNotFoundException("Attendance roaster not found"));

		attendanceRoaster.setShiftName(attendanceRoasterDetails.getShiftName());
		attendanceRoaster.setOfficeStartTime(attendanceRoasterDetails.getOfficeStartTime());
		attendanceRoaster.setOfficeEndTime(attendanceRoasterDetails.getOfficeEndTime());
		attendanceRoaster.setLateTime(attendanceRoasterDetails.getLateTime());
		attendanceRoaster.setWeekends(attendanceRoasterDetails.getWeekends());
		attendanceRoasterRepository.save(attendanceRoaster);
		
		return ResponseEntity.ok().body(attendanceRoaster);
		
	}

	@Override
	public ResponseEntity<?> updateEmployee(Long employeeId, Long attendanceRoasterId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		AttendanceRoaster attendanceRoaster = attendanceRoasterRepository.findById(attendanceRoasterId)
				.orElseThrow(() -> new ResourceNotFoundException("Attendance Roaster Not Found"));

		employee.setAttendanceRoaster(attendanceRoaster);
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok().body(updatedEmployee);
	}


}
