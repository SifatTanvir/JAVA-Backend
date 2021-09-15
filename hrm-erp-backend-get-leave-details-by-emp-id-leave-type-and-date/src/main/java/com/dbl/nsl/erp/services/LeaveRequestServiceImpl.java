package com.dbl.nsl.erp.services;

import java.io.FileNotFoundException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Attendance;
import com.dbl.nsl.erp.models.AttendanceRoaster;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.FileUploadUtil;
import com.dbl.nsl.erp.models.HeadOfDepartment;
import com.dbl.nsl.erp.models.LeaveConsumed;
import com.dbl.nsl.erp.models.LeaveRequest;
import com.dbl.nsl.erp.models.LineManager;
import com.dbl.nsl.erp.models.TeamLeader;
import com.dbl.nsl.erp.payload.request.Message;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.AttendanceRepository;
import com.dbl.nsl.erp.repository.AttendanceRoasterRepository;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.DepartmentRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.HeadOfDepartmentRepository;
import com.dbl.nsl.erp.repository.HolidayListRepository;
import com.dbl.nsl.erp.repository.LeaveConsumedRepository;
import com.dbl.nsl.erp.repository.LeaveRequestRepository;
import com.dbl.nsl.erp.repository.LineManagerRepository;
import com.dbl.nsl.erp.repository.TeamLeaderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private LeaveConsumedRepository leaveConsumedRepository;

	@Autowired
	private LineManagerRepository lineManagerRepository;

	@Autowired
	private TeamLeaderRepository teamLeaderRepository;

	@Autowired
	private HeadOfDepartmentRepository headOfDeptRepository;

	@Autowired
	AttendanceRepository attendanceRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DesignationRepository designationRepository;

	@Autowired
	private EmailSenderService mailService;

	@Autowired
	HolidayListRepository holidayListRepository;

	@Autowired
	AttendanceRoasterRepository attendanceRosterRepository;
	
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

	public String message(Employee sender, LeaveRequest leaveRequest) {

		String senderInfo = "Leave Request Details :\n\n" + "Name : " + sender.getFirstName() + " "
				+ sender.getLastName() + " , " + "Employee ID : " + sender.getId() + "\n";

		// List<Company> companies = sender.getCompany();
		// senderInfo = senderInfo + "Company : " + companies.get(0).getName() + "\n";
		List<Department> departments = sender.getDepartment();
		senderInfo = senderInfo + "Department : " + departments.get(0).getDepartmentName() + " , ";
		List<Designation> designations = sender.getDesignation();
		senderInfo = senderInfo + "Designation :  " + designations.get(0).getDesignationName() + "\n";

		String text = "From : " + leaveRequest.getStartDate() + " to : " + leaveRequest.getEndDate() + " , "
				+ "Duration : " + leaveRequest.getDuration() + " Days" + "\n" + "Applied for : "
				+ leaveRequest.getLeaveType() + " leave\n\n" + "Leave Reason : " + " " + leaveRequest.getMessage()
				+ "\n";

		final String message = senderInfo + text;

		return message;
	}

	public LeaveConsumed leaveCalculation(Employee employee, LeaveRequest leaveRequest)
			throws MessagingException, ParseException {

		Long employeeId = employee.getId();
		LeaveConsumed leaveConsumed = employee.getLeaveConsumed();
		String leaveType = leaveRequest.getLeaveType(); // requested leave type
		Long duration = leaveRequest.getDuration(); // requested days

		switch (leaveType) {

		case "sick":
			Long usedSick = leaveConsumed.getSick();
			leaveConsumed.setSick(usedSick + duration);
			break;

		case "casual":
			Long usedCasual = leaveConsumed.getCasual();
			leaveConsumed.setCasual(usedCasual + duration);
			break;

		case "annual":
			Long usedAnnual = leaveConsumed.getAnnual();
			leaveConsumed.setAnnual(usedAnnual + duration);
			break;

		case "maternity":
			Long usedMaternity = leaveConsumed.getMaternity();
			leaveConsumed.setMaternity(usedMaternity + duration);
			break;

		case "other":
			Long used = leaveConsumed.getSick();
			leaveConsumed.setOther(used + duration);
			break;
		}

		leaveConsumedRepository.save(leaveConsumed);

		// save attendance with appropriate "leave" status
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(leaveRequest.getStartDate());
		Date endDate = formatter.parse(leaveRequest.getEndDate());

		Date currentDate = startDate;
		while (currentDate.equals(endDate) || currentDate.before(endDate)) {

			// process date here
			Optional<Attendance> optionalAttendance = attendanceRepository.findByEmployeeIdAndDate(employeeId,
					currentDate);

			if (optionalAttendance.isPresent()) {
				Attendance attendance = optionalAttendance.get();
				attendance.setStatus(leaveRequest.getLeaveType());
				attendanceRepository.save(attendance);
			}

			else {
				Attendance attendance = new Attendance();
				attendance = attendanceProperties(employeeId, attendance); // add properties name, department,
																			// designation, group, group id
				attendance.setDate(currentDate);
				attendance.setEmployeeId(employeeId);
				attendance.setInTime(null);
				attendance.setOutTime(null);
				attendance.setStatus(leaveRequest.getLeaveType());
				attendanceRepository.save(attendance);
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 1);
			currentDate = calendar.getTime();
		}

		String subject = "Regarding Your Leave Request";
		String sendTo = employee.getUsername(); // it's sender official email address

		String message = "Hi " + employee.getFirstName() + " " + employee.getLastName() + ",\n\n"
				+ "Your leave request has been accepted. Stay safe. See you soon.\n\n" + "Best Wishes,"
				+ "\nTafuri Technologies Ltd.";
		try {
			mailService.sendEmail(sendTo, subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveConsumed;
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
	
	
	public void setAttendanceStatusForRejectedAndDeletedLeaveRequests(Long employeeId, LeaveRequest leaveRequest) throws ParseException {
		
		Date date = new Date();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(leaveRequest.getStartDate());
		Date endDate = formatter.parse(leaveRequest.getEndDate());

		Date currentDate = startDate;
		while (currentDate.equals(endDate) || currentDate.before(endDate)) {

			//less than 0 means previous date, equal zero means present date and greater than zero means future date
			if (currentDate.compareTo(date) <= 0) {
				// leave request for previous and present date, update status to absent
				Optional<Attendance> optionalAttendance = attendanceRepository.findByEmployeeIdAndDate(employeeId,
						currentDate);
				Attendance attendance = optionalAttendance.get();
				if(attendance.getInTime() == null) {
					attendance.setStatus("absent");
				}else {
					String status = attendanceStatus(employeeId, attendance.getInTime());
					attendance.setStatus(status);
				}
				attendanceRepository.save(attendance);
			} else {
				// leave request for future date, delete attendance
				Optional<Attendance> optionalAttendance = attendanceRepository.findByEmployeeIdAndDate(employeeId,
						currentDate);
				Attendance attendance = optionalAttendance.get();
				attendanceRepository.delete(attendance);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 1);
			currentDate = calendar.getTime();
		}
	}

	@Override
	public ResponseEntity<?> sendLeaveRequest(String leaveReq, MultipartFile file)
			throws ResourceNotFoundException, FileNotFoundException, MessagingException, ParseException {

		LeaveRequest leaveRequest = new LeaveRequest();
		try {
			leaveRequest = objectMapper.readValue(leaveReq, LeaveRequest.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse("Failed to process the data"));
		}

		Long SenderId = leaveRequest.getSenderId();
		Employee sender = employeeRepository.findById(SenderId)
				.orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

		String attachment = null;
		try {
			String uploadDir = "src/main/resources/static/file/";
			attachment = FileUploadUtil.saveFile(sender.getId(), uploadDir, file);
		} catch (Exception e) {

		}

		leaveRequest.setSenderFirstName(sender.getFirstName());
		leaveRequest.setSenderLastName(sender.getLastName());
		leaveRequest.setEmployee(sender);
		leaveRequest.setSendingDate(new Date());
		leaveRequest.setIsAccepted(0);
		leaveRequest.setIsAcceptedByLineManager(0);
		leaveRequest.setIsAcceptedByTeamLeader(0);
		leaveRequest.setIsAcceptedByHeadOfDept(0);
		leaveRequest.setAttachmentPath("file/" + attachment);
		leaveRequestRepository.save(leaveRequest);

		String subject = "Application For Leave";

		Long lineManagerId = sender.getLineManagerId();
		Employee lineManager = employeeRepository.findById(lineManagerId)
				.orElseThrow(() -> new ResourceNotFoundException("Line Manager not found"));

		String sendTo = lineManager.getUsername(); // get line Manager official email

		try {
			if (attachment == null)
				mailService.sendEmail(sendTo, subject, message(sender, leaveRequest));
			else
				mailService.sendEmailWithAttachment(sendTo, subject, message(sender, leaveRequest), attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// save attendance with leave application status
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(leaveRequest.getStartDate());
		Date endDate = formatter.parse(leaveRequest.getEndDate());

		Date currentDate = startDate;
		while (currentDate.equals(endDate) || currentDate.before(endDate)) {

			// process date here
			Optional<Attendance> optionalAttendance = attendanceRepository.findByEmployeeIdAndDate(sender.getId(),
					currentDate);

			if (optionalAttendance.isPresent()) {
				Attendance attendance = optionalAttendance.get();
				attendance.setStatus("AFL");
				attendanceRepository.save(attendance);
			}

			else {
				Attendance attendance = new Attendance();
				attendance = attendanceProperties(sender.getId(), attendance); // add properties name, department,
																				// designation, group, group id
				attendance.setDate(currentDate);
				attendance.setEmployeeId(sender.getId());
				attendance.setInTime(null);
				attendance.setOutTime(null);
				attendance.setStatus("AFL");
				attendanceRepository.save(attendance);
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 1);
			currentDate = calendar.getTime();
		}

		// Sending Notification to line manager
		// String uri = "http://localhost:8080/send_notification";
		// RestTemplate restTemplate = new RestTemplate();
		// restTemplate.getForObject(uri, Notification.class);

		return ResponseEntity.ok(new MessageResponse("Your mail has sent. Wait till it get approved"));
	}

	@Override
	public ResponseEntity<?> leaveRequestAccept(Long employeeId, Long senderId, Long leaveRequestId)
			throws ResourceNotFoundException, MessagingException, FileNotFoundException, ParseException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave Request not found"));

		Employee sender = employeeRepository.findById(leaveRequest.getSenderId())
				.orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

		String subject = "Application For Leave";

		if (leaveRequest.getIsAccepted() != 0)
			return ResponseEntity.ok(new MessageResponse(" Opps! Somthing went wrong. Please Try again later"));

		// if current employee is line manager
		else if (leaveRequest.getIsAcceptedByLineManager() == 0
				&& employeeId.longValue() == sender.getLineManagerId().longValue()) {

			leaveRequest.setActionDateByLineManager(new Date());
			leaveRequest.setIsAcceptedByLineManager(1);
			leaveRequestRepository.save(leaveRequest);

			// team leader not exist
			if (sender.getTeamLeaderId() == null) {

				// head of department not exist
				if (sender.getHeadOfDepartmentId() == null) {
					leaveRequest.setIsAccepted(1);
					leaveRequestRepository.save(leaveRequest);
					return ResponseEntity.ok(leaveCalculation(sender, leaveRequest)); // Calculate Leave
				}
				// head of department exist
				else {

					Employee headOfDept = employeeRepository.findById(sender.getHeadOfDepartmentId())
							.orElseThrow(() -> new ResourceNotFoundException("Head Of Department Not not found"));

					// line manager and head of department same person
					if (sender.getHeadOfDepartmentId().longValue() == sender.getLineManagerId().longValue()) {

						leaveRequest.setActionDateByHeadOfDept(new Date());
						leaveRequest.setIsAcceptedByHeadOfDept(1);
						leaveRequest.setIsAccepted(1);
						leaveRequestRepository.save(leaveRequest);

						return ResponseEntity.ok(leaveCalculation(sender, leaveRequest)); // Calculate Leave
					} else {

						String sendTo = headOfDept.getUsername(); // get head of department official email
						String attachment = leaveRequest.getAttachmentPath();

						try {
							String emailBody = message(sender, leaveRequest);
							emailBody = emailBody + "\n" + "This request has already been accepted by : Line Manager - "
									+ sender.getLineManagerName();
							if (attachment == null)
								mailService.sendEmail(sendTo, subject, emailBody);
							else
								mailService.sendEmailWithAttachment(sendTo, subject, emailBody, attachment);

						} catch (Exception e) {
							e.printStackTrace();
						}
						return ResponseEntity
								.ok(new MessageResponse("Leave request is forwarded to associated Head Of Department"));
					}

				}
			}

			// line manager and team leader same person
			else if (sender.getTeamLeaderId().longValue() == sender.getLineManagerId().longValue()) {

				leaveRequest.setActionDateByTeamLeader(new Date());
				leaveRequest.setIsAcceptedByTeamLeader(1);
				leaveRequestRepository.save(leaveRequest);

				// head of department not exist
				if (sender.getHeadOfDepartmentId() == null) {
					leaveRequest.setIsAccepted(1);
					leaveRequestRepository.save(leaveRequest);
					return ResponseEntity.ok(leaveCalculation(sender, leaveRequest)); // Calculate Leave
				}
				// head of department exist
				else {

					Employee headOfDept = employeeRepository.findById(sender.getHeadOfDepartmentId())
							.orElseThrow(() -> new ResourceNotFoundException("Head Of Department Not not found"));

					// line manager and head of department same person
					if (sender.getHeadOfDepartmentId().longValue() == sender.getTeamLeaderId().longValue()) {

						leaveRequest.setActionDateByHeadOfDept(new Date());
						leaveRequest.setIsAcceptedByHeadOfDept(1);
						leaveRequest.setIsAccepted(1);
						leaveRequestRepository.save(leaveRequest);

						return ResponseEntity.ok(leaveCalculation(sender, leaveRequest)); // Calculate Leave
					} else {

						String sendTo = headOfDept.getUsername(); // get head of department official email
						String attachment = leaveRequest.getAttachmentPath();

						try {
							String emailBody = message(sender, leaveRequest);
							emailBody = emailBody + "\n"
									+ "This request has already been accepted by : Line Manager And Team Leader - "
									+ sender.getLineManagerName();
							if (attachment == null)
								mailService.sendEmail(sendTo, subject, emailBody);
							else
								mailService.sendEmailWithAttachment(sendTo, subject, emailBody, attachment);

						} catch (Exception e) {
							e.printStackTrace();
						}
						return ResponseEntity
								.ok(new MessageResponse("Leave request is forwarded to associated Head Of Department"));

					}

				}

			}

			// line manager and team leader different person
			else {

				Employee teamLeader = employeeRepository.findById(sender.getTeamLeaderId())
						.orElseThrow(() -> new ResourceNotFoundException("Team Leader Not not found"));

				String sendTo = teamLeader.getUsername(); // get team leader official email
				String attachment = leaveRequest.getAttachmentPath();

				try {
					String emailBody = message(sender, leaveRequest);
					emailBody = emailBody + "\n" + "This request has already been accepted by : Line Manager - "
							+ sender.getLineManagerName();
					if (attachment == null)
						mailService.sendEmail(sendTo, subject, emailBody);
					else
						mailService.sendEmailWithAttachment(sendTo, subject, emailBody, attachment);

				} catch (Exception e) {
					e.printStackTrace();
				}

				return ResponseEntity.ok(new MessageResponse("Leave request is forwarded to associated Team Leader"));

			}

		}

		// if current employee is Team leader
		else if (leaveRequest.getIsAcceptedByTeamLeader() == 0
				&& employeeId.longValue() == sender.getTeamLeaderId().longValue()) {

			leaveRequest.setActionDateByTeamLeader(new Date());
			leaveRequest.setIsAcceptedByTeamLeader(1);
			leaveRequestRepository.save(leaveRequest);

			// head of department not exist
			if (sender.getHeadOfDepartmentId() == null) {
				leaveRequest.setIsAccepted(1);
				leaveRequestRepository.save(leaveRequest);
				return ResponseEntity.ok(leaveCalculation(sender, leaveRequest)); // Calculate Leave
			}
			// head of department exist
			else {

				Employee headOfDept = employeeRepository.findById(sender.getHeadOfDepartmentId())
						.orElseThrow(() -> new ResourceNotFoundException("Head Of Department Not not found"));

				// Team Leader and head of department same person
				if ((sender.getHeadOfDepartmentId().longValue() == sender.getTeamLeaderId().longValue())
						|| (sender.getHeadOfDepartmentId().longValue() == sender.getLineManagerId().longValue())) {

					leaveRequest.setActionDateByHeadOfDept(new Date());
					leaveRequest.setIsAcceptedByHeadOfDept(1);
					leaveRequest.setIsAccepted(1);
					leaveRequestRepository.save(leaveRequest);

					return ResponseEntity.ok(leaveCalculation(sender, leaveRequest)); // Calculate Leave
				}
				//Team Leader and head of department different person
				else {

					String sendTo = headOfDept.getUsername(); // get head of department official email
					String attachment = leaveRequest.getAttachmentPath();

					try {
						String emailBody = message(sender, leaveRequest);
						emailBody = emailBody + "\n" + "This request has already been accepted by : Line Manager - "
								+ sender.getLineManagerName() + " and Team Leader - " + sender.getTeamLeaderName();
						if (attachment == null)
							mailService.sendEmail(sendTo, subject, emailBody);
						else
							mailService.sendEmailWithAttachment(sendTo, subject, emailBody, attachment);

					} catch (Exception e) {
						e.printStackTrace();
					}
					return ResponseEntity
							.ok(new MessageResponse("Leave request is forwarded to associated Head Of Department"));

				}

			}

		}

		else if (leaveRequest.getIsAccepted() == 0 && leaveRequest.getIsAcceptedByHeadOfDept() == 0
				&& employeeId.longValue() == sender.getHeadOfDepartmentId().longValue()) {

			leaveRequest.setActionDateByHeadOfDept(new Date());
			leaveRequest.setIsAcceptedByHeadOfDept(1);
			leaveRequest.setIsAccepted(1);
			leaveRequestRepository.save(leaveRequest);

			return ResponseEntity.ok().body(leaveCalculation(sender, leaveRequest)); // Calculate Leave
		}

		return ResponseEntity.ok(new MessageResponse(" Opps! Somthing went wrong. Please Try again later"));
	}

	@Override
	public ResponseEntity<?> leaveRequestReject(Long employeeId, Long senderId, Long leaveRequestId, Message reason)
			throws ResourceNotFoundException, ParseException {

		LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave Request not found"));

		Employee employee = employeeRepository.findById(leaveRequest.getSenderId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Employee sender = employeeRepository.findById(leaveRequest.getSenderId())
				.orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

		leaveRequest.setIsAccepted(2);

		if (employeeId.longValue() == sender.getLineManagerId().longValue()) {
			leaveRequest.setIsAcceptedByLineManager(2);
			leaveRequest.setActionDateByLineManager(new Date());
		}

		else if (employeeId.longValue() == sender.getTeamLeaderId().longValue()) {
			leaveRequest.setIsAcceptedByTeamLeader(2);
			leaveRequest.setActionDateByTeamLeader(new Date());
		}

		else if (employeeId.longValue() == sender.getHeadOfDepartmentId().longValue()) {
			leaveRequest.setIsAcceptedByHeadOfDept(2);
			leaveRequest.setActionDateByHeadOfDept(new Date());
		}

		leaveRequestRepository.save(leaveRequest);
		
		//call function to set attendance status according in time in attendance model
		setAttendanceStatusForRejectedAndDeletedLeaveRequests(senderId, leaveRequest);

		String sendTo = employee.getUsername(); // it's sender official email address

//		String message = "Hi "+ employee.getFirstName() + " " + employee.getLastName() + ",\n\n"
//				+ "Sorry ! Your leave request has been rejected.\n" +  "Reason : " + reason.getContent() + "\n\n"
//				+ "Sincerely,\n" + employee.getFirstName() + " " + employee.getLastName() + "\n"
//				+ employee.getDesignation().get(0).getDesignationName() + ", " + employee.getDepartment().get(0).getDepartmentName() + "\n"
//				+ "Tafuri Technologies Ltd.";

		String message = "Hi " + employee.getFirstName() + " " + employee.getLastName() + ",\n\n"
				+ "Sorry ! Your leave request has been rejected.\n" + "Reason : " + reason.getContent() + "\n\n"
				+ "Best wishes from,\n" + "\n" + "Tafuri Technologies Ltd.";

		String subject = "Regarding Your Leave Request";

		try {
			mailService.sendEmail(sendTo, subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(new MessageResponse("Rejection email successfully send to employee"));
	}

	@Override
	public ResponseEntity<?> getAllLeaveRequestOfAnEmployee(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		List<LeaveRequest> allLeaveRequests = employee.getLeaveRequest();
		if (allLeaveRequests.isEmpty()) {
			throw new ResourceNotFoundException("Leave consume not found");
		}
		return ResponseEntity.ok().body(allLeaveRequests);
	}

	@Override
	public ResponseEntity<?> getLeaveRequestOfAllEmployee() throws ResourceNotFoundException {

		List<LeaveRequest> allLeaveRequests = leaveRequestRepository.findAll();

		if (allLeaveRequests.isEmpty()) {
			throw new ResourceNotFoundException("Leave request not found");
		}

		return ResponseEntity.ok().body(allLeaveRequests);
	}

	@Override
	public ResponseEntity<?> getSpecificLeaveRequest(Long employeeId, Long leaveRequestId)
			throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
		return ResponseEntity.ok().body(leaveRequest);
	}

	@Override
	public ResponseEntity<?> getLeaveRequestToPerformAction(Long employeeId) throws ResourceNotFoundException {
		Employee viewer = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Set<LeaveRequest> leaveRequests = new HashSet<>();

		// Is viewer a line manager ?
		Optional<LineManager> optionalLineManager = lineManagerRepository.findById(employeeId);

		if (optionalLineManager.isPresent()) {
			LineManager lineManager = optionalLineManager.get();
			List<Long> childs = lineManager.getEmployeeId();
			if (childs.size() > 0) {
				for (Long id : childs) {
					Optional<Employee> optionalEmployee = employeeRepository.findById(id);
					Employee employee = optionalEmployee.get();
					List<LeaveRequest> leaveReq = employee.getLeaveRequest();
					if (leaveReq.size() > 0) {
						for (LeaveRequest leaveRequest : leaveReq) {
							if (leaveRequest.getIsAccepted() == 0 && leaveRequest.getIsAcceptedByLineManager() == 0)
								leaveRequests.add(leaveRequest);
						}
					}
				}
			}
		}

		// Is viewer a Team Leader ?
		Optional<TeamLeader> optionalTeamLeader = teamLeaderRepository.findById(employeeId);

		if (optionalTeamLeader.isPresent()) {
			TeamLeader teamLeader = optionalTeamLeader.get();
			List<Long> childs = teamLeader.getEmployeeId();
			if (childs.size() > 0) {
				for (Long id : childs) {
					Optional<Employee> optionalEmployee = employeeRepository.findById(id);
					Employee employee = optionalEmployee.get();
					List<LeaveRequest> leaveReq = employee.getLeaveRequest();
					if (leaveReq.size() > 0) {
						for (LeaveRequest leaveRequest : leaveReq) {
							if (leaveRequest.getIsAccepted() == 0 && leaveRequest.getIsAcceptedByLineManager() == 1
									&& leaveRequest.getIsAcceptedByTeamLeader() == 0)
								leaveRequests.add(leaveRequest);
						}
					}
				}
			}
		}

		// Is viewer a head of department ?
		Optional<HeadOfDepartment> optionalHeadOfDept = headOfDeptRepository.findById(employeeId);

		if (optionalHeadOfDept.isPresent()) {
			HeadOfDepartment HOD = optionalHeadOfDept.get();
			List<Long> childs = HOD.getEmployeeId();
			if (childs.size() > 0) {
				for (Long id : childs) {
					Optional<Employee> optionalEmployee = employeeRepository.findById(id);
					Employee employee = optionalEmployee.get();
					List<LeaveRequest> leaveReq = employee.getLeaveRequest();
					if (leaveReq.size() > 0) {
						for (LeaveRequest leaveRequest : leaveReq) {
							if (leaveRequest.getIsAccepted() == 0 && leaveRequest.getIsAcceptedByLineManager() == 1
									&& (employee.getTeamLeaderId() != null
											&& leaveRequest.getIsAcceptedByTeamLeader() == 1)
									&& leaveRequest.getIsAcceptedByHeadOfDept() == 0)
								leaveRequests.add(leaveRequest);
						}
					}
				}
			}
		}

		if (leaveRequests.size() == 0)
			return ResponseEntity.ok(new MessageResponse(" Leave Request Not found..."));

		return ResponseEntity.ok(leaveRequests);
	}

	@Override
	public ResponseEntity<?> deleteLeaveRequest(Long employeeId, Long leaveRequestId)
			throws ResourceNotFoundException, ParseException {

//		Date date = new Date();

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

		// if leave request is not accepted or rejected by line manager, then delete
		if (!(leaveRequest.getIsAcceptedByLineManager() > 0)) {
			
			//call function to set attendance status according in time in attendance model 
			setAttendanceStatusForRejectedAndDeletedLeaveRequests(employeeId, leaveRequest);
			
			leaveRequestRepository.deleteById(leaveRequestId);
			
			return ResponseEntity.ok().body("Leave Request Deleted Successfully");
		} else {
			return ResponseEntity.ok().body("Leave Request can not be deleted");
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveRequestByAdmin(Long adminId, Long employeeId, Long leaveRequestId)
			throws ResourceNotFoundException, ParseException {

		Date date = new Date();

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
		
		
		LeaveConsumed leaveConsumed = leaveConsumedRepository.findById(employee.getLeaveConsumed().getLeaveConsumedId())
				.orElseThrow(() -> new ResourceNotFoundException("Leave Consumed not found"));
		
		// leave duration in applied leave request
		Long duration = leaveRequest.getDuration();
		
		
		//call function to set attendance status according in time in attendance model 
		setAttendanceStatusForRejectedAndDeletedLeaveRequests(employeeId, leaveRequest);
		
		//if accepted leave request is cancelled by admin, then leave duration will be subtracted from leave consumed
		if((leaveRequest.getIsAccepted() == 1)) {
			if(leaveRequest.getLeaveType().equals("annual")) { 
				// subtract duration from annual leave
				Long total = leaveConsumed.getAnnual() - duration;
				leaveConsumed.setAnnual(total);
				leaveConsumedRepository.save(leaveConsumed);
			}else if(leaveRequest.getLeaveType().equals("casual")) {
				// subtract duration from casual leave
				Long total = leaveConsumed.getCasual() - duration;
				leaveConsumed.setCasual(total);
				leaveConsumedRepository.save(leaveConsumed);
			}else if(leaveRequest.getLeaveType().equals("sick")) { 
				// subtract duration from sick leave
				Long total = leaveConsumed.getSick() - duration;
				leaveConsumed.setSick(total);
				leaveConsumedRepository.save(leaveConsumed);
			}else if(leaveRequest.getLeaveType().equals("maternity")) { 
				// subtract duration from maternity leave
				Long total = leaveConsumed.getMaternity() - duration;
				leaveConsumed.setMaternity(total);
				leaveConsumedRepository.save(leaveConsumed);
			}else {
				//subtract from other leave
				Long total = leaveConsumed.getOther() - duration;
				leaveConsumed.setOther(total);
				leaveConsumedRepository.save(leaveConsumed);
			}
		}

		leaveRequestRepository.deleteById(leaveRequestId);

		return ResponseEntity.ok().body("Leave Request Deleted Successfully");
	}

	@Override
	public ResponseEntity<?> getAllRequestedLeaveOfAnEmployee(Long employeeId) {

		List<LeaveRequest> allLeaveRequest = leaveRequestRepository.findBySenderIdAndisAccepted(employeeId, 0);

		Map<String, Long> countDay = new HashMap<>();
		countDay.put("casual", 0L);
		countDay.put("annual", 0L);
		countDay.put("sick", 0L);
		countDay.put("other", 0L);
		countDay.put("maternity", 0L);
		countDay.put("unpaid", 0L);

		for (LeaveRequest leaveRequest : allLeaveRequest) {
			String type = leaveRequest.getLeaveType();
			countDay.get(type);
			countDay.put(type, countDay.get(type) + leaveRequest.getDuration());
		}

		return ResponseEntity.ok().body(countDay);
	}

	@Override
	public ResponseEntity<?> getNumberOfWorkingDaysBetweenStartDateAndEndDate(Long employeeId, Date startDate,
			Date endDate) throws ResourceNotFoundException {

		// Convert date to day for checking if weekend or not
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely

		Long totalWorkingDays = 0l;

		Long attendanceRoasterId = attendanceRosterRepository.findByEmployeeId(employeeId);

		AttendanceRoaster attendanceRoaster = attendanceRosterRepository.findById(attendanceRoasterId)
				.orElseThrow(() -> new ResourceNotFoundException("Attendance Roaster not found"));

		String weekends = attendanceRoaster.getWeekends().toString();

		Date currentDate = startDate;
		while (currentDate.equals(endDate) || currentDate.before(endDate)) {

			String day = simpleDateformat.format(currentDate);

			if (holidayListRepository.existsByDate(currentDate)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DATE, 1);
				currentDate = calendar.getTime();
				continue;
			} else if (weekends.contains(day)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DATE, 1);
				currentDate = calendar.getTime();
				continue;
			}else {
				System.out.println("Dates not in Attendance and weekends.");
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 1);
			currentDate = calendar.getTime();

			totalWorkingDays = totalWorkingDays + 1;
		}

		return ResponseEntity.ok().body(totalWorkingDays);
	}
	
	@Override
	public ResponseEntity<?> getAcceptedOrRejectedLeaveRequest(Long employeeId) throws ResourceNotFoundException {
		Employee viewer = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Set<LeaveRequest> leaveRequests = new HashSet<>();

		// Is viewer a line manager ?
		Optional<LineManager> optionalLineManager = lineManagerRepository.findById(employeeId);

		if (optionalLineManager.isPresent()) {
			LineManager lineManager = optionalLineManager.get();
			List<Long> childs = lineManager.getEmployeeId();
			if (childs.size() > 0) {
				for (Long id : childs) {
					Optional<Employee> optionalEmployee = employeeRepository.findById(id);
					Employee employee = optionalEmployee.get();
					List<LeaveRequest> leaveReq = employee.getLeaveRequest();
					if (leaveReq.size() > 0) {
						for (LeaveRequest leaveRequest : leaveReq) {
							//all accepted or rejected leave requests by line manager
							if (leaveRequest.getIsAcceptedByLineManager() > 0)
							leaveRequests.add(leaveRequest);
						}
					}
				}
			}
		}

		// Is viewer a Team Leader ?
		Optional<TeamLeader> optionalTeamLeader = teamLeaderRepository.findById(employeeId);

		if (optionalTeamLeader.isPresent()) {
			TeamLeader teamLeader = optionalTeamLeader.get();
			List<Long> childs = teamLeader.getEmployeeId();
			if (childs.size() > 0) {
				for (Long id : childs) {
					Optional<Employee> optionalEmployee = employeeRepository.findById(id);
					Employee employee = optionalEmployee.get();
					List<LeaveRequest> leaveReq = employee.getLeaveRequest();
					if (leaveReq.size() > 0) {
						for (LeaveRequest leaveRequest : leaveReq) {
							//all accepted or rejected leave requests by team lead
							if (leaveRequest.getIsAcceptedByTeamLeader() > 0)
								leaveRequests.add(leaveRequest);
						}
					}
				}
			}
		}

		// Is viewer a head of department ?
		Optional<HeadOfDepartment> optionalHeadOfDept = headOfDeptRepository.findById(employeeId);

		if (optionalHeadOfDept.isPresent()) {
			HeadOfDepartment HOD = optionalHeadOfDept.get();
			List<Long> childs = HOD.getEmployeeId();
			if (childs.size() > 0) {
				for (Long id : childs) {
					Optional<Employee> optionalEmployee = employeeRepository.findById(id);
					Employee employee = optionalEmployee.get();
					List<LeaveRequest> leaveReq = employee.getLeaveRequest();
					if (leaveReq.size() > 0) {
						for (LeaveRequest leaveRequest : leaveReq) {
							//all accepted or rejected leave requests by HoD
							if (leaveRequest.getIsAcceptedByHeadOfDept() > 0)
								leaveRequests.add(leaveRequest);
						}
					}
				}
			}
		}

		return ResponseEntity.ok(leaveRequests);
	}
	
}
