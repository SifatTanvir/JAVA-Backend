package com.dbl.nsl.erp.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Attendance;
import com.dbl.nsl.erp.models.AttendanceRoaster;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.repository.AnnualLeaveRepository;
import com.dbl.nsl.erp.repository.AttendanceRepository;
import com.dbl.nsl.erp.repository.AttendanceRoasterRepository;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.DepartmentRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.HolidayListRepository;
import com.dbl.nsl.erp.services.AttendanceService;

import com.dbl.nsl.erp.models.AnnualLeave;

@RestController
public class AttendanceController {

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
	HolidayListRepository holidayListRepository;

	@Autowired
	AttendanceService attendanceService;

	@Autowired
	AnnualLeaveRepository annualLeaveRepository;
	
	@Value("${nsl.erp.newYearDate}")
	private String newYearDate;

	@PutMapping("/attendance/time")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> employeeAttendance(@RequestBody List<Map<String, Map<String, Long>>> punchRequest)
			throws ParseException {

		return attendanceService.employeeAttendance(punchRequest);
	}

	@GetMapping("/attendance/employee/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAllByDateBetween(@PathVariable(value = "id") Long employeeId,
			@RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
			@RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) {

		return attendanceService.getAllByDateBetween(employeeId, startdate, enddate);
	}

	@GetMapping("/attendance/admin")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllByDate(@RequestParam("company") String companyName,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

		return attendanceService.getAllByDate(companyName, date);
	}

	@GetMapping("/attendance/company/date_between")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllAttendanceByCompanyNameAndDateBetween(@RequestParam("company") String companyName,
			@RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
			@RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate)
			throws ResourceNotFoundException {

		return attendanceService.getAllAttendanceByCompanyNameAndDateBetween(companyName, startdate, enddate);
	}

	@GetMapping("/employee/{id}/attendance/childs_employee")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')  or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getAllChildsAttendance(@PathVariable(value = "id") Long employeeId,
			@RequestParam("company") String companyName,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws ResourceNotFoundException {

		return attendanceService.getAllChildsAttendance(employeeId, companyName, date);
	}

	@GetMapping("/attendance/company/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllByCompanyAndDate(@PathVariable(value = "id") Long companyId,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

		return attendanceService.getAllByCompanyAndDate(companyId, date);
	}

	@GetMapping("/attendance/department/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllByDepartmentAndDate(@PathVariable(value = "id") Long departmentId,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws ResourceNotFoundException {

		return attendanceService.getAllByDepartmentAndDate(departmentId, date);
	}

	@GetMapping("/attendance/group-admin")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllGroupByDate(
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

		return attendanceService.getAllGroupByDate(date);
	}

	@PutMapping("/attendance/employee/{id}/request")  // no need of employee id, but still taken to make this api meaningful
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")  // only logged user can send update request 
	public ResponseEntity<?> updateAttendanceRequest(@PathVariable(value = "id") Long employeeId,
			@RequestBody Attendance attendanceDetails) throws ResourceNotFoundException {

		return attendanceService.updateAttendanceRequest(employeeId, attendanceDetails);
	}
	
	@PutMapping("/attendance/{id1}/employee/{id2}/accept") // employee id is taken to make this api meaningful
	@PreAuthorize("hasRole('ADMIN')")  // only admin can accept update request 
	public ResponseEntity<?> updateAttendanceAccept( @PathVariable(value="id1") Long attendanceId, 
			@PathVariable(value = "id2") Long employeeId ) throws ResourceNotFoundException, ParseException {

		return attendanceService.updateAttendanceAccept(employeeId, attendanceId);
	}
	
	@PutMapping("/attendance/{id1}/employee/{id2}/reject") // employee id is taken to make this api meaningful
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateAttendanceReject( @PathVariable(value="id1") Long attendanceId, 
			@PathVariable(value = "id2") Long employeeId ) throws ResourceNotFoundException {

		return attendanceService.updateAttendanceReject(employeeId, attendanceId);
	}

	@GetMapping("/attendance/findall")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllAttendance() throws ResourceNotFoundException {

		return attendanceService.getAllAttendance();
	}
	
	@GetMapping("/attendance/findall/edit_request")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getAllEditAttendanceRequest() throws ResourceNotFoundException {

		return attendanceService.getAllEditAttendanceRequest();
	}

	@GetMapping("/attendance/company/monthly_report/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> getMonthlyReport(@RequestParam("company") Long companyId,
			@RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
			@RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate)
			throws ResourceNotFoundException {

		return attendanceService.getAttendanceMonthlyReport(companyId, startdate, enddate);
	}

	public Attendance attendanceProperties(Long Id, Attendance attendance) {

		String employeeFirstName = employeeRepository.findFirstNameById(Id);
		String employeeLastName = employeeRepository.findLastNameById(Id);
		String employmentStatus = employeeRepository.findEmployeementStatus(Id);

		Set<String> employeeDesignationSet = designationRepository.findDesignationByEmployeeId(Id);
		String employeeDesignation = String.join(", ", employeeDesignationSet);
		Set<String> employeeDepartmentSet = departmentRepository.findDepartmentByEmployeeId(Id);
		String employeeDepartment = String.join(", ", employeeDepartmentSet);
		Set<String> employeeCompanySet = companyRepository.findNameByEmployeeId(Id);
		String employeeCompany = String.join(",", employeeCompanySet);

		Set<Long> employeeCompanyIdSet = companyRepository.findCompanyIdByEmployeeId(Id);

		for (Long eid : employeeCompanyIdSet) {
			attendance.setCompanyId(eid);
		}

		attendance.setFirstName(employeeFirstName);
		attendance.setLastName(employeeLastName);
		attendance.setEmploymentStatus(employmentStatus);
		attendance.setDesignationName(employeeDesignation);
		attendance.setDepartmentName(employeeDepartment);
		attendance.setCompanyName(employeeCompany);

		return attendance;

	}

//	@Scheduled(cron = "0/50 * * * * ?") // every 50 seconds
	// @Scheduled(cron = "0 0/15 * 1/1 * ?") // every 15 minutes
	// @Scheduled(cron = "0 0 10 1/1 * ?") // everyday 10 am
	// @Scheduled(cron = "0 35 15 1/1 * ?") // everyday 10:35 am
	// @Scheduled(cron = "0 0 3 31 12 ?") // every December at 3 am
//     @Scheduled(cron = "0/50 * * * * ?") // everyday 1 am at night
	@Scheduled(cron = "0 0 1 1/1 * ?") // everyday 1 am at night
	public void attendanceTableFilledWithDefalutValue() throws ParseException {

		System.out.println("---------attendance scheduler executed--------");
		
		String zeroHour = "00:00:00";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date startTime = format.parse(zeroHour);

		// Current date : debug
		Date date = new Date();
//		System.out.println("Current date: " + date);

		// Convert date to day for checking if weekend or not
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
		String day = simpleDateformat.format(date);

		// Check if current date exists in holiday list in DB
		Boolean isHolidayTrue = holidayListRepository.existsByDate(date);

		// Find all active employee except super Admin
		List<Long> employeeIds = employeeRepository.findEmployeeIdsGreaterThanOneAndActiveTrue(1L, true);

		for (Long employeeId : employeeIds) {

			// process date here
			Optional<Attendance> optionalAttendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, date);
			if (optionalAttendance.isPresent())
				continue; // if attendance is already exist then

			Attendance attendance = new Attendance();
			attendance = attendanceProperties(employeeId, attendance); // add properties name, department, designation,
																		// group, group id
			attendance.setDate(date);
			attendance.setEmployeeId(employeeId);
			attendance.setInTime(null);
			attendance.setOutTime(null);

			Long attendanceRoasterId = attendanceRosterRepository.findByEmployeeId(employeeId);
			Optional<AttendanceRoaster> attendanceRoaster = attendanceRosterRepository.findById(attendanceRoasterId);
			if (attendanceRoaster.isPresent()) {

				AttendanceRoaster attRoaster = attendanceRoaster.get();
				String weekends = attRoaster.getWeekends().toString();

				if (weekends.contains(day)) {
					attendance.setStatus("weekend");
				} else if (isHolidayTrue) {
					attendance.setStatus("holiday");
				} else {
					attendance.setStatus("absent");
				}
			}

			attendanceRepository.save(attendance);
		}
	}
	
//This scheduler checks for annual leave. If an employee is in probation or permanent stage, his/her 
//annual leave will be calculated. On the other hand, all annual leave set to zero for trainee employee.	

//	@Scheduled(cron = "0/50 * * * * ?")
	@Scheduled(cron = "0 0 2 1/1 * ?") // everyday 2 am at night
	public void calculateAnnualLeaveByEmployeeId() {

		System.out.println("---------annual leave scheduler executed--------");

		String statusPresent = "present";
		String statusLate = "late";
		Date currentDate = new Date();

		Long superAdminId = 1l;
//		List<Employee> employees = employeeRepository.findAllEmployeeIdGreaterThanOne(superAdminId);

		List<Employee> employees = employeeRepository.findAllEmployeeIdGreaterThanOneAndActiveStatusTrue(superAdminId,
				true);

		for (Employee employee : employees) {
			Date joiningDate = employee.getJoiningDate();
//			String newYearDate = "2021-01-01";
			Date firstDate = null;
			try {
				firstDate = new SimpleDateFormat("yyyy-MM-dd").parse(newYearDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// check whether the probation start date is first date and current date is
			// greater the first date
			// then the first date to probation date for calculating annual leave from
			// 2021-01-01
			if (joiningDate != null) {
				if (employee.getEmploymentStatus().equals("probation")
						|| employee.getEmploymentStatus().equals("permanent")) {

					if ((joiningDate.compareTo(firstDate) < 0) && (currentDate.compareTo(firstDate) > 0)) {
						joiningDate = firstDate;
					}

					Long countAttendanceByPresentStatus = attendanceRepository.countAllPresentDateTillToday(joiningDate,
							currentDate, statusPresent, employee.getId());
					Long countAttendanceByLateStatus = attendanceRepository.countAllPresentDateTillToday(joiningDate,
							currentDate, statusLate, employee.getId());

					Long annualLeave = (countAttendanceByPresentStatus + countAttendanceByLateStatus) / 18;

					// employee has null annual leave then create and add
					if (employee.getAnnualLeaveModel() == null) {
						AnnualLeave annualLeaveObj = new AnnualLeave();
						annualLeaveObj.setTotalLeave(annualLeave);
						annualLeaveObj.setPreviousTotalLeave(0L);
						employee.setAnnualLeaveModel(annualLeaveObj);
						employeeRepository.save(employee);
					} else {
						// employee has annual leave then update
						Long id = employee.getAnnualLeaveModel().getId();
						Optional<AnnualLeave> storedAnnualLeave = annualLeaveRepository.findById(id);
						if (storedAnnualLeave.isPresent()) {
							AnnualLeave empAnnLeave = storedAnnualLeave.get();
							empAnnLeave.setTotalLeave(annualLeave);
							annualLeaveRepository.save(empAnnLeave);
						}
					}
				} else { // for trainee employee create annual leave with zero value
					System.out.println("employee is trainee");
					if (employee.getAnnualLeaveModel() == null) {
						AnnualLeave annualLeaveObj = new AnnualLeave();
						annualLeaveObj.setTotalLeave(0L);
						annualLeaveObj.setPreviousTotalLeave(0L);
						employee.setAnnualLeaveModel(annualLeaveObj);
						employeeRepository.save(employee);
					}else {
						System.out.println("Trainee employee annual leave already created");
					}
				}
			} else {
				System.out.println("employee has empty joining date.");
			}
		}
	}
}
