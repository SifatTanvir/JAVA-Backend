package com.dbl.nsl.erp.services;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Attendance;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.payload.response.GroupHrResponse;
import com.dbl.nsl.erp.repository.AttendanceRepository;
import com.dbl.nsl.erp.repository.AttendanceRoasterRepository;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.DepartmentRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.LineManagerRepository;
import com.dbl.nsl.erp.repository.TeamLeaderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceRepository attendanceRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DesignationRepository designationRepository;

	@Autowired
	AttendanceRoasterRepository attendanceRosterRepository;

	@Autowired
	LineManagerRepository lineManagerRepository;

	@Autowired
	TeamLeaderRepository teamLeaderRepository;
	
	@Autowired
	private EmailSenderService mailService;
	
	@Value("${nsl.erp.adminEmailAddress}")
	private String adminEmailAddress;
	
	ObjectMapper objectMapper = new ObjectMapper();


	public boolean isParentLineManager( Long employeeId, Long childId ) throws ResourceNotFoundException {

		if( employeeId.longValue() == childId.longValue() ) return false;

		Employee child = employeeRepository.findById(childId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Long lineManagerId = child.getLineManagerId(); // immediate line Manager 
		if( lineManagerId == null ) return false;

		while( lineManagerId.longValue() > 1L ) {  // loop throw till root : root id is 1
			if( lineManagerId.longValue() == employeeId.longValue() ) return true;
			Optional<Employee> optionalEmployee = employeeRepository.findById( lineManagerId );
			Employee tempEmployee = optionalEmployee.get();  // get parent employee
			lineManagerId = tempEmployee.getLineManagerId(); 
		}

		return false;
	}

	public String[] convertMiliSecondToDateAndTime(Long miliseconds) {

		String[] dateTimeString = null;

		// long currentDateTime = System.currentTimeMillis();
		// System.out.println(currentDateTime);
		System.out.println(miliseconds);
		long currentDateTime = miliseconds;

		Date currentDate = new Date(currentDateTime);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = df.format(currentDate);
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
			String newstr = new SimpleDateFormat("yyyy-MM-dd, H:mm:ss").format(date1);
			dateTimeString = newstr.split(",");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateTimeString;
	}

	public String attendanceStatus(Long Id, Time time) throws ParseException {

		String inTime = time.toString();

		String employeeInTime = attendanceRosterRepository.findInTimeByEmployeeId(Id);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date startTime = format.parse(employeeInTime);
		Date entryTime = format.parse(inTime);
		long difference = entryTime.getTime() - startTime.getTime();
		if (difference > 0) return "late";
		else return "present";
	}

	@Override
	public ResponseEntity<?> employeeAttendance(List<Map<String, Map<String, Long>>> punchRequest) throws ParseException {

		List<Long> inTimeAndOutTime = new ArrayList<>();
		String[] dateTimeString = null;
		Long employeeId = null;

		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

		for (Map<String, Map<String, Long>> eachOuterMap : punchRequest) {
			for (String key : eachOuterMap.keySet()) {
				String empId = key;
				employeeId = Long.parseLong(empId);
				Map<String, Long> eachInnerMap = eachOuterMap.get(key);
				boolean i = false;
				for (Long eachFirstTimeAndLastTime : eachInnerMap.values()) {
					inTimeAndOutTime.add(eachFirstTimeAndLastTime); // array of first and last
					if (i == false) {
						i = true;
						dateTimeString = convertMiliSecondToDateAndTime(eachFirstTimeAndLastTime);
						Date dateFromPunch = new SimpleDateFormat("yyyy-MM-dd").parse(dateTimeString[0]);
						Time timeFromPunch = new Time(formatter.parse(dateTimeString[1]).getTime());
						Optional<Attendance> attended = attendanceRepository.findByEmployeeIdAndDate(employeeId, dateFromPunch);
						if (attended.isPresent()) {
							Attendance updatedAttendance = attended.get();
							if (updatedAttendance.getInTime() == null) {
								updatedAttendance.setInTime(timeFromPunch);
								updatedAttendance.setOutTime(timeFromPunch);
								String status = attendanceStatus(employeeId, timeFromPunch);
								updatedAttendance.setStatus(status);
								attendanceRepository.save(updatedAttendance);
							} else {
								updatedAttendance.setOutTime(timeFromPunch);
								attendanceRepository.save(updatedAttendance);
							}
						}
					} else {
						if (eachFirstTimeAndLastTime != null) {
							dateTimeString = convertMiliSecondToDateAndTime(eachFirstTimeAndLastTime);
							Date dateFromPunch = new SimpleDateFormat("yyyy-MM-dd").parse(dateTimeString[0]);
							Time timeFromPunch = new Time(formatter.parse(dateTimeString[1]).getTime());
							Optional<Attendance> attended = attendanceRepository.findByEmployeeIdAndDate(employeeId, dateFromPunch);
							if (attended.isPresent()) {
								Attendance updatedAttendance = attended.get();
								updatedAttendance.setOutTime(timeFromPunch);
								attendanceRepository.save(updatedAttendance);
							}
						}
					}
				}
			}
		}

		return ResponseEntity.ok().body(punchRequest);
	}


	@Override
	public ResponseEntity<?> getAllByDateBetween(Long employeeId, Date startdate, Date enddate) {

		return ResponseEntity.ok().body(attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startdate, enddate));
	}

	@Override
	public ResponseEntity<?> getAllByDate(String companyName, Date date) {

		return ResponseEntity.ok().body(attendanceRepository.findByCompanyNameAndDate(companyName, date));
	}

	@Override
	public ResponseEntity<?> getAllChildsAttendance(Long employeeId, String companyName, Date date) 
			throws ResourceNotFoundException {

		List<Attendance> attendance =  attendanceRepository.findByCompanyNameAndDate(companyName, date);
		List<Attendance> filterAttendance = new ArrayList<>();

		for( Attendance it:attendance ) {
			if (isParentLineManager ( employeeId, it.getEmployeeId()) )
				filterAttendance.add(it);
		}

		return ResponseEntity.ok().body(filterAttendance);
	}

	@Override
	public ResponseEntity<?> getAllByCompanyAndDate(Long companyId, Date date) {

		return ResponseEntity.ok().body(attendanceRepository.findByCompanyIdAndDate(companyId, date));
	}

	@Override
	public ResponseEntity<?> getAllAttendanceByCompanyNameAndDateBetween(String companyName, Date startdate, Date enddate) 
			throws ResourceNotFoundException {

		List<Attendance> allAttendance =  attendanceRepository.findByCompanyNameAndDateBetween(companyName, startdate, enddate);

		if( allAttendance.isEmpty() ) throw new ResourceNotFoundException("Attendance Not found");
		return ResponseEntity.ok().body(allAttendance);

	}

	@Override
	public ResponseEntity<?> getAllByDepartmentAndDate(Long departmentId, Date date) throws ResourceNotFoundException {

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		return ResponseEntity.ok().body(attendanceRepository.findByDepartmentNameAndDate(department.getDepartmentName(), date));
	}

	@Override
	public ResponseEntity<?> getAllGroupByDate(Date date) {

		List<Long> companyIds = companyRepository.findCompanyId();
		List<GroupHrResponse> groupHrResponseList = new ArrayList<GroupHrResponse>();

		for (Long companyId : companyIds) {
			Long presentEmpCount = attendanceRepository.countByStatusAndDateAndCompanyId("present", date, companyId);
			Long lateEmpCount = attendanceRepository.countByStatusAndDateAndCompanyId("late", date, companyId);
			Long absentEmpCount = attendanceRepository.countByStatusAndDateAndCompanyId("absent", date, companyId);
			//	Long sickEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("sick", date, companyId);
			//	Long casualEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("casual", date, companyId);
			//	Long annualEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("annual", date, companyId);
			Long leaveEmpCount = attendanceRepository.countByStatusAndDateAndCompanyId("leave", date, companyId);
			String companyName = companyRepository.findCompanyNameByCompanyId(companyId);

			Long totalEmp = presentEmpCount + lateEmpCount + absentEmpCount;

			GroupHrResponse groupHrResponse = new GroupHrResponse();
			groupHrResponse.setTotalEmployee(totalEmp);
			groupHrResponse.setTotalPresentEmployee(presentEmpCount);
			groupHrResponse.setTotalLateEmployee(lateEmpCount);
			groupHrResponse.setTotalAbsentEmployee(absentEmpCount);
			groupHrResponse.setGroupName(companyName);
			groupHrResponse.setDate(date);
			//	groupHrResponse.setTotalSickLeaveEmployee(sickEmpCount);
			//	groupHrResponse.setTotalCasualLeaveEmployee(casualEmpCount);
			//	groupHrResponse.setTotalAnnualLeaveEmployee(annualEmpCount);
			groupHrResponse.setTotalLeaveEmployee(leaveEmpCount);
			groupHrResponseList.add(groupHrResponse);
		}

		return ResponseEntity.ok().body(groupHrResponseList);
	}

	@Override
	public ResponseEntity<?> updateAttendanceRequest(Long employeeId, Attendance attendanceDetails)  throws ResourceNotFoundException {
		
		Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, attendanceDetails.getDate())
				.orElseThrow(() ->  new ResourceNotFoundException("Attendance not found") );
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow( () -> new ResourceNotFoundException("Employee Not found"));
	
		attendance.setUpdatedInTime(attendanceDetails.getUpdatedInTime());
		attendance.setUpdatedOutTime(attendanceDetails.getUpdatedOutTime()); 
		attendance.setUpdatedStatus(attendanceDetails.getUpdatedStatus());
		attendance.setEditReason(attendanceDetails.getEditReason());
		attendance.setSendEditRequest(true);
		attendance.setIsEdited(false);
		attendance.setSendingDate(new Date());
		attendance.setIsInTimeEdited(false);
		attendance.setIsOutTimeEdited(false);
		
		List<String> emailAddresses = new ArrayList<String>();
		
//		String adminEmailAddress = employeeRepository.findAdminEmailAddressGreaterThanOneAndActiveTrue(1l, true, "Admin");
		Long lineManagerId = employee.getLineManagerId();
		
		Employee lineManager = employeeRepository.findById(lineManagerId)
				.orElseThrow( () -> new ResourceNotFoundException("Line Manager Not found"));
		
		String lineManagerEmailAddress = lineManager.getUsername();
		
		// send request email to admin
		String subject = "About Attendance IN/OUT Time Update";
		
		String text =   "Attendance Update Request Details:\n" 
				      + "Name : " + employee.getFirstName() + " " + employee.getLastName() + " , "
				      + "Employee ID : " + employee.getId() + "\n";
		
		String attendanceInfo = "Date: " + attendance.getDate() + "\n"
		                        + "Current In Time : " + attendance.getInTime() + "\nUpdated In Time : " + attendance.getUpdatedInTime() + "\n"
		                        + "Current Out Time : " + attendance.getOutTime() + "\nUpdated Out Time : " + attendance.getUpdatedOutTime()+ "\n\n"
		                        + "Reason: " + attendance.getEditReason();
		
		String message = text + "\n" + attendanceInfo;
		
		String sendTo = adminEmailAddress;  // put admin email here
		
		try {
			mailService.sendEmail(sendTo, subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
		String sendToLineManager = lineManagerEmailAddress ;  // put line manager email here
		
		try {
			mailService.sendEmail(sendToLineManager, subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}  

		final Attendance updatedAttendance = attendanceRepository.save(attendance);
		return ResponseEntity.ok(updatedAttendance);
	}
	
	@Override
	public ResponseEntity<?> updateAttendanceAccept(Long employeeId, Long AttendanceId) 
			throws ResourceNotFoundException, ParseException {
		
		Attendance attendance = attendanceRepository.findById(AttendanceId)
				.orElseThrow(() ->  new ResourceNotFoundException("Attendance not found"));
		
		if(!(attendance.getInTime().equals(attendance.getUpdatedInTime()))) {
			attendance.setIsInTimeEdited(true);
		}
		
		if(!(attendance.getOutTime().equals(attendance.getUpdatedOutTime()))) {
			attendance.setIsOutTimeEdited(true);
		}
		
		attendance.setAcceptedDate(new Date());
		
		String employeeInTime = attendanceRosterRepository.findInTimeByEmployeeId(employeeId);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date startTime = format.parse(employeeInTime);
		Date entryTime = attendance.getUpdatedInTime();
		long difference = entryTime.getTime() - startTime.getTime();
		if( difference <= 0 ) {
			 if( attendance.getUpdatedStatus() == "WFH" ) attendance.setStatus("WFH");
			 else attendance.setStatus("present");
		}
		else attendance.setStatus("late");
		
		// reset all the attribute 
		attendance.setSendEditRequest(false);
		attendance.setIsEdited(true);

		final Attendance updatedAttendance = attendanceRepository.save(attendance);
		return ResponseEntity.ok(updatedAttendance);
	}
	
	@Override
	public ResponseEntity<?> updateAttendanceReject(Long employeeId, Long AttendanceId)
			throws ResourceNotFoundException {
		
		Attendance attendance = attendanceRepository.findById(AttendanceId)
				.orElseThrow(() ->  new ResourceNotFoundException("Attendance not found") );
		
		// reset all the attribute 
		attendance.setRejectedDate(new Date());
		attendance.setSendEditRequest(false);
		attendance.setIsEdited(false);

		final Attendance updatedAttendance = attendanceRepository.save(attendance);
		return ResponseEntity.ok(updatedAttendance);
	}


	@Override
	public ResponseEntity<?> getAllAttendance() throws ResourceNotFoundException {

		List<Attendance> allAttendance =  attendanceRepository.findAll();
		if( allAttendance.isEmpty() ) throw new ResourceNotFoundException("Attendance Not found");
		return ResponseEntity.ok().body(allAttendance);
	}
	
	
	@Override
	public ResponseEntity<?> getAllEditAttendanceRequest() throws ResourceNotFoundException {

		List<Attendance> allAttendance = new ArrayList<Attendance>();
		
		allAttendance =  attendanceRepository.findAllEditAttendanceRequest(true);
		
		return ResponseEntity.ok().body(allAttendance);
	}

	@Override
	public ResponseEntity<?> getAttendanceMonthlyReport(Long companyId, Date startdate, Date enddate) throws ResourceNotFoundException {

		List< Pair< Employee, Map<String,Long> > >  report = new ArrayList<>();
		Set<Long> allEmployeeId = employeeRepository.findEmployeeBasedOnCompany(companyId);

		for( Long employeeId : allEmployeeId ) {

			List<Attendance> allAttendance =  attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startdate, enddate);
			Map<String,Long> countDay = new HashMap<>();
			countDay.put("absent", 0L);
			countDay.put("present", 0L);
			countDay.put("late", 0L);
			countDay.put("WFH", 0L);
			countDay.put("AFL", 0L);
			countDay.put("weekend", 0L);
			countDay.put("holiday", 0L);
			countDay.put("casual", 0L);
			countDay.put("annual", 0L);
			countDay.put("sick", 0L);
			countDay.put("other", 0L);
			countDay.put("maternity", 0L);
			countDay.put("unpaid", 0L);

			for( Attendance attendance : allAttendance ) {
				String status = attendance.getStatus();
				countDay.get(status);
				countDay.put(status, countDay.get(status) + 1L );
			}

			Employee employee = employeeRepository.findById(employeeId)
					.orElseThrow( () -> new ResourceNotFoundException("Employee Not found"));

			report.add( new Pair<Employee, Map<String,Long>> (employee,countDay) );
		}

		return ResponseEntity.ok().body(report);
	}


}
