package com.dbl.nsl.erp.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.AnnualLeave;
import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.ERole;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.FileUploadUtil;
import com.dbl.nsl.erp.models.HeadOfDepartment;
import com.dbl.nsl.erp.models.LineManager;
import com.dbl.nsl.erp.models.Nominee;
import com.dbl.nsl.erp.models.Role;
import com.dbl.nsl.erp.models.TeamLeader;
import com.dbl.nsl.erp.payload.request.PersonalInformation;
import com.dbl.nsl.erp.payload.request.PublicInformation;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.payload.response.ShortEmployeeInfo;
import com.dbl.nsl.erp.repository.CertificationRepository;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.DepartmentRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.EducationRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.ExperienceRepository;
import com.dbl.nsl.erp.repository.HeadOfDepartmentRepository;
import com.dbl.nsl.erp.repository.LineManagerRepository;
import com.dbl.nsl.erp.repository.NomineeRepository;
import com.dbl.nsl.erp.repository.RoleRepository;
import com.dbl.nsl.erp.repository.SequenceStorerRepository;
import com.dbl.nsl.erp.repository.TeamLeaderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DesignationRepository designationRepository;

	@Autowired
	ExperienceRepository experienceRepository;

	@Autowired
	EducationRepository educationRepository;

	@Autowired
	CertificationRepository certificationRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	LineManagerRepository lineManagerRepositoy;

	@Autowired
	TeamLeaderRepository teamLeaderRepositoy;

	@Autowired
	HeadOfDepartmentRepository headOfDepartmentRepositoy;

	@Autowired
	SequenceStorerRepository sequenceStorerRepository;

	@Autowired
	NomineeRepository nomineeRepository;

	@Autowired
	private EmailSenderService mailService;

	@Override
	public ResponseEntity<?> createEmployee(String empJson, MultipartFile file) throws ResourceNotFoundException {

		// SequenceStorer seqStorerPresent = new SequenceStorer();
		// Long seqNumber = null;
		// Optional<SequenceStorer> seqStorer = sequenceStorerRepository.findById(1l);
		// if(seqStorer.isPresent()) {
		// seqStorerPresent = seqStorer.get();
		// seqNumber = seqStorerPresent.getSequenceNumber();
		// seqNumber = seqNumber + 1;
		// }
		//
		// employee.setId(seqNumber);

		Employee employee = new Employee();

		try {
			employee = objectMapper.readValue(empJson, Employee.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse("Failed to process the data"));
		}

		if (employeeRepository.existsById(employee.getId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Id already used"));

		} else if (employeeRepository.existsByUsername(employee.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Username already used"));
		}

		String employeeRole = employee.getRole();

		if (employeeRole.equals("Admin")) {
			ERole e = ERole.ROLE_ADMIN;
			Optional<Role> role = roleRepository.findByName(e);
			Role rols = role.get();
			Set<Role> roles = new HashSet<>();
			roles.add(rols);
			employee.setRoles(roles);
		} else if (employeeRole.equals("Assistant Admin")) {
			ERole e = ERole.ROLE_ASSISTANT_ADMIN;
			Optional<Role> role = roleRepository.findByName(e);
			Role rols = role.get();
			Set<Role> roles = new HashSet<>();
			roles.add(rols);
			employee.setRoles(roles);
		} else if (employeeRole.equals("User")) {
			ERole e = ERole.ROLE_USER;
			Optional<Role> role = roleRepository.findByName(e);
			Role rols = role.get();
			Set<Role> roles = new HashSet<>();
			roles.add(rols);
			employee.setRoles(roles);
		}

		String autoGeneratedRandomPassword = AuthServiceImpl.generateRandomPassword(); // random password generation
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(autoGeneratedRandomPassword); // password encoded
		employee.setPassword(hashedPassword);

		// console print : debug
		System.out.println("Employee Id: " + employee.getId() + "  Password : " + autoGeneratedRandomPassword);

		try {
			// set Project Manager name
			Optional<Employee> temp = employeeRepository.findById(employee.getProjectManagerId());
			if (temp.isPresent()) {
				Employee projectManager = temp.get();
				employee.setProjectManagerName(projectManager.getFirstName());
			}
		} catch (Exception e) {

		}

		try {

			Optional<LineManager> opLineManager = lineManagerRepositoy.findById(employee.getLineManagerId());
			if (opLineManager.isPresent()) {
				LineManager lineManager = opLineManager.get();
				lineManager.getEmployeeId().add(employee.getId());
				lineManagerRepositoy.save(lineManager);
			} else {
				LineManager lineManager = new LineManager();
				lineManager.setId(employee.getLineManagerId());
				List<Long> empId = new ArrayList<>();
				empId.add(employee.getId());
				lineManager.setEmployeeId(empId);
				lineManagerRepositoy.save(lineManager);
			}

			// set line manger name
			Optional<Employee> temp = employeeRepository.findById(employee.getLineManagerId());
			if (temp.isPresent()) {
				Employee emp = temp.get();
				employee.setLineManagerName(emp.getFirstName());
			}

		} catch (Exception e) {

		}

		try {

			Optional<TeamLeader> opTeamLeader = teamLeaderRepositoy.findById(employee.getTeamLeaderId());
			if (opTeamLeader.isPresent()) {
				TeamLeader teamLeader = opTeamLeader.get();
				teamLeader.getEmployeeId().add(employee.getId());
				teamLeaderRepositoy.save(teamLeader);
			} else {
				TeamLeader teamLeader = new TeamLeader();
				teamLeader.setId(employee.getTeamLeaderId());
				List<Long> empId = new ArrayList<>();
				empId.add(employee.getId());
				teamLeader.setEmployeeId(empId);
				teamLeaderRepositoy.save(teamLeader);
			}

			// set team leader name
			Optional<Employee> temp = employeeRepository.findById(employee.getTeamLeaderId());
			if (temp.isPresent()) {
				Employee emp = temp.get();
				employee.setTeamLeaderName(emp.getFirstName());
			}

		} catch (Exception e) {

		}

		try {

			Optional<HeadOfDepartment> opHeadOfDepartment = headOfDepartmentRepositoy
					.findById(employee.getHeadOfDepartmentId());
			if (opHeadOfDepartment.isPresent()) {
				HeadOfDepartment headOfDepartment = opHeadOfDepartment.get();
				headOfDepartment.getEmployeeId().add(employee.getId());
				headOfDepartmentRepositoy.save(headOfDepartment);
			} else {
				HeadOfDepartment headOfDepartment = new HeadOfDepartment();
				headOfDepartment.setId(employee.getHeadOfDepartmentId());
				List<Long> empId = new ArrayList<>();
				empId.add(employee.getId());
				headOfDepartment.setEmployeeId(empId);
				headOfDepartmentRepositoy.save(headOfDepartment);
			}

			// set head of department name
			Optional<Employee> temp = employeeRepository.findById(employee.getHeadOfDepartmentId());
			if (temp.isPresent()) {
				Employee emp = temp.get();
				employee.setHeadOfDepartmentName(emp.getFirstName());
			}

		} catch (Exception e) {

		}

		// every employee is listed into lineManger table but not shown in drop down
		// only mark selected employees are shown in the drop down section
		// another way to add line manager, team leader, head of dept into db
		// if employee not in line manager table, then create
		if (!lineManagerRepositoy.existsById(employee.getId())) {
			LineManager lineManager = new LineManager();
			lineManager.setId(employee.getId());
			List<Long> childEmp = new ArrayList<>();
			lineManager.setEmployeeId(childEmp);
			lineManagerRepositoy.save(lineManager);
		}

		// if employee not in team lead, then create
		if (!teamLeaderRepositoy.existsById(employee.getId())) {
			TeamLeader teamLeader = new TeamLeader();
			teamLeader.setId(employee.getId());
			List<Long> empId = new ArrayList<>();
			teamLeader.setEmployeeId(empId);
			teamLeaderRepositoy.save(teamLeader);
		}

		// if employee not in head of the department table, then create
		if (!headOfDepartmentRepositoy.existsById(employee.getId())) {
			HeadOfDepartment headOfDepartment = new HeadOfDepartment();
			headOfDepartment.setId(employee.getId());
			List<Long> empId = new ArrayList<>();
			headOfDepartment.setEmployeeId(empId);
			headOfDepartmentRepositoy.save(headOfDepartment);
		}

		// seqStorerPresent.setSequenceNumber(seqNumber);
		// sequenceStorerRepository.save(seqStorerPresent);

		try {
			String uploadDir = "src/main/resources/static/img/";
			String fileName = "img/" + FileUploadUtil.saveFile(employee.getId(), uploadDir, file);
			employee.setProfilePicPath(fileName);
		} catch (Exception e) {

		}

		if (employee.getAnnualLeaveModel() == null) {
			AnnualLeave annualLeaveObj = new AnnualLeave();
			annualLeaveObj.setTotalLeave(0L);
			annualLeaveObj.setPreviousTotalLeave(0L);
			employee.setAnnualLeaveModel(annualLeaveObj);
		} else {
			System.out.println("Annual leave is created for employee before.");
		}

		Employee savedEmployee = employeeRepository.save(employee);

		Long nomineeId = savedEmployee.getNominee().get(0).getId();
		Nominee storedNominee = nomineeRepository.findById(nomineeId)
				.orElseThrow(() -> new ResourceNotFoundException("Nominee not found"));
		storedNominee.setEmployee(savedEmployee);
		nomineeRepository.save(storedNominee);

		String sendTo = employee.getUsername(); // employee official email

		String subject = "Greetings from Tafuri Technologies Ltd";

		String message = "Hi " + employee.getFirstName() + " " + employee.getLastName() + ",\n\n"
				+ "Welcome to our community ! Your Account has been created. This email includes your account details, so please keep it safe.\n"
				+ "Your Username: " + employee.getUsername() + "\nPasswrod: " + autoGeneratedRandomPassword + "\n\n"
				+ "Best Regards,\nTafuri Technologies Ltd";

		try {
			mailService.sendEmail(sendTo, subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(savedEmployee);
	}

	@Override
	public ResponseEntity<?> updateProfilePicture(MultipartFile file, Long employeeId)
			throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		try {
			String uploadDir = "src/main/resources/static/img/";
			String fileName = "img/" + FileUploadUtil.saveFile(employee.getId(), uploadDir, file);
			employee.setProfilePicPath(fileName);
		} catch (Exception e) {

		}

		final Employee savedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(savedEmployee);
	}

	@Override
	public ResponseEntity<?> getEmployee(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		return ResponseEntity.ok().body(employee);
	}

	@Override
	public ResponseEntity<?> getAllEmployee() throws ResourceNotFoundException {

		List<Employee> employees = employeeRepository.findAll();
		if (employees.isEmpty())
			throw new ResourceNotFoundException("Employee not found");
		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> getAllEmployeeExceptSuperAdmin() throws ResourceNotFoundException {

		List<Employee> employees = employeeRepository.findByIdGreaterThan(1L);
		if (employees.isEmpty())
			throw new ResourceNotFoundException("Employee not found");
		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> getAllEmployeeExceptSuperAdminAndActiveForUserAndAdmin() throws ResourceNotFoundException {

		List<Employee> employees = employeeRepository.findAllEmployeeIdGreaterThanOneAndActiveStatusTrue(1L, true);
		if (employees.isEmpty())
			throw new ResourceNotFoundException("Employee not found");
		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> getAllEmployeeExceptSuperAdminAndActive() throws ResourceNotFoundException {

		List<Employee> employees = employeeRepository.findAllEmployeeIdGreaterThanOneAndActiveStatusTrue(1L, true);
		if (employees.isEmpty())
			throw new ResourceNotFoundException("Employee not found");
		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> getAllEmployeeExceptSuperAdminAndInactive() throws ResourceNotFoundException {

		List<Employee> employees = employeeRepository.findAllEmployeeIdGreaterThanOneAndActiveStatusTrue(1L, false);
		if (employees.isEmpty())
			throw new ResourceNotFoundException("Employee not found");
		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> getAllActiveEmployee() throws ResourceNotFoundException {

		List<Employee> employees = employeeRepository.findByActiveTrue();
		if (employees.isEmpty())
			throw new ResourceNotFoundException("Employee not found");
		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> getOfficialInformation(Long employeeId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getPersonalInformation(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		PersonalInformation personalInformation = new PersonalInformation();
		personalInformation.setFatherName(employee.getFatherName());
		personalInformation.setMotherName(employee.getMotherName());

		return ResponseEntity.ok(personalInformation);
	}

	@Override
	public ResponseEntity<?> getPublicInformation(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		PublicInformation publicInformation = new PublicInformation();
		publicInformation.setFirstName(employee.getFirstName());
		publicInformation.setLastName(employee.getLastName());
		publicInformation.setEmail(employee.getEmail());
		publicInformation.setContact(employee.getOfficialContact());

		Set<String> employeeDesignationSet = designationRepository.findDesignationByEmployeeId(employeeId);
		String employeeDesignation = String.join(", ", employeeDesignationSet);
		Set<String> employeeDepartmentSet = departmentRepository.findDepartmentByEmployeeId(employeeId);
		String employeeDepartment = String.join(", ", employeeDepartmentSet);
		Set<String> employeeCompanySet = companyRepository.findNameByEmployeeId(employeeId);
		String employeeCompany = String.join(",", employeeCompanySet);

		publicInformation.setDesignationName(employeeDesignation);
		publicInformation.setDepartmentName(employeeDepartment);
		publicInformation.setCompanyName(employeeCompany);

		return ResponseEntity.ok().body(publicInformation);

	}

	@Override
	public ResponseEntity<?> getAllLineManager() throws ResourceNotFoundException {

		List<ShortEmployeeInfo> lineManagers = new ArrayList<>();
//		List<Employee> allEmployee = employeeRepository.findAllLineManager(true);

		List<Employee> allEmployee = employeeRepository.findAllActiveLineManager(true, true);

		for (Employee employee : allEmployee) {
			ShortEmployeeInfo info = new ShortEmployeeInfo();
			info.setEmployeeId(employee.getId());
			info.setFirstName(employee.getFirstName());
			info.setLastName(employee.getLastName());
			lineManagers.add(info);
		}
		if (lineManagers.isEmpty())
			throw new ResourceNotFoundException("Line manager not found");
		return ResponseEntity.ok().body(lineManagers);

	}

	@Override
	public ResponseEntity<?> getAllTeamLeader() throws ResourceNotFoundException {

		List<ShortEmployeeInfo> teamLeaders = new ArrayList<>();
//		List<Employee> allEmployee = employeeRepository.findAllTeamLeader(true);

		List<Employee> allEmployee = employeeRepository.findAllActiveTeamLeader(true, true);

		for (Employee employee : allEmployee) {
			ShortEmployeeInfo info = new ShortEmployeeInfo();
			info.setEmployeeId(employee.getId());
			info.setFirstName(employee.getFirstName());
			info.setLastName(employee.getLastName());
			teamLeaders.add(info);
		}
		if (teamLeaders.isEmpty())
			throw new ResourceNotFoundException("Team Leader not found");
		return ResponseEntity.ok().body(teamLeaders);
	}

	@Override
	public ResponseEntity<?> getAllHeadOfDepartment() throws ResourceNotFoundException {

		List<ShortEmployeeInfo> headOfDepts = new ArrayList<>();
//		List<Employee> allEmployee = employeeRepository.findAllTeamLeader(true);

		List<Employee> allEmployee = employeeRepository.findAllActiveHoD(true, true);

		for (Employee employee : allEmployee) {
			ShortEmployeeInfo info = new ShortEmployeeInfo();
			info.setEmployeeId(employee.getId());
			info.setFirstName(employee.getFirstName());
			info.setLastName(employee.getLastName());
			headOfDepts.add(info);
		}
		if (headOfDepts.isEmpty())
			throw new ResourceNotFoundException("Head Of Department not found");
		return ResponseEntity.ok().body(headOfDepts);

	}

	@Override
	public ResponseEntity<?> updateLineManager(Long employeeId, Employee employeeDetails)
			throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		System.out.println("New Line manager id : " + employeeDetails.getLineManagerId());

		// if new line manager is set to not null
		if (employeeDetails.getLineManagerId() != null) {
			System.out.println("in");
			System.out.println(employeeDetails.getLineManagerId());
			System.out.println(employee.getLineManagerId());

			// if employee has line manager
			if (employee.getLineManagerId() != null) {
				if (employeeDetails.getLineManagerId().longValue() != employee.getLineManagerId().longValue()) {
					System.out.println("employee line manager not null");
					// remove employee from previous line manager
					LineManager lineManager = lineManagerRepositoy.findById(employee.getLineManagerId())
							.orElseThrow(() -> new ResourceNotFoundException("Line Manager not found"));
					System.out.println("Previous Line Manager info ");
					System.out.println(
							"LM Id: " + employee.getLineManagerId() + " LM Name: " + employee.getLineManagerName());
					System.out.println("LM Childs:" + lineManager.getEmployeeId());
					lineManager.getEmployeeId().remove(employeeId); // remove from list
					System.out.println("After Delete LM Childs:" + lineManager.getEmployeeId());
					lineManagerRepositoy.save(lineManager);
					System.out.println("saved after removed child");

					Optional<LineManager> opLineManager = lineManagerRepositoy
							.findById(employeeDetails.getLineManagerId());
					if (opLineManager.isPresent()) {
						LineManager oldLineManager = opLineManager.get();
						oldLineManager.getEmployeeId().add(employeeId);
						lineManagerRepositoy.save(oldLineManager);
					} else { // create new line manager and then save
						LineManager newLineManager = new LineManager();
						newLineManager.setId(employeeDetails.getLineManagerId());
						List<Long> empId = new ArrayList<>();
						empId.add(employeeId);
						newLineManager.setEmployeeId(empId);
						lineManagerRepositoy.save(newLineManager);
					}

					employee.setLineManagerId(employeeDetails.getLineManagerId());
					Employee tempLineManager = employeeRepository.findById(employeeDetails.getLineManagerId())
							.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
					employee.setLineManagerName(tempLineManager.getFirstName());
				}

			} else { // if employee does not have any line manager before, then create
				Optional<LineManager> opLineManager = lineManagerRepositoy.findById(employeeDetails.getLineManagerId());
				if (opLineManager.isPresent()) {
					LineManager oldLineManager = opLineManager.get();
					oldLineManager.getEmployeeId().add(employeeId);
					lineManagerRepositoy.save(oldLineManager);
				} else { // create new line manager and then save
					LineManager newLineManager = new LineManager();
					newLineManager.setId(employeeDetails.getLineManagerId());
					List<Long> empId = new ArrayList<>();
					empId.add(employeeId);
					newLineManager.setEmployeeId(empId);
					lineManagerRepositoy.save(newLineManager);
				}

				employee.setLineManagerId(employeeDetails.getLineManagerId());
				Employee tempLineManager = employeeRepository.findById(employeeDetails.getLineManagerId())
						.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
				employee.setLineManagerName(tempLineManager.getFirstName());

			}

		} else {
			// line manager is set null
			//remove employee from previous line manager
			if(employee.getLineManagerId() != null) {
				System.out.println("Line Manager NOT NULL in Employe Object.");
				System.out.println(employee.getLineManagerId());
				
				LineManager lineManager = lineManagerRepositoy.findById(employee.getLineManagerId())
						.orElseThrow(() -> new ResourceNotFoundException("Line Manager not found"));
				lineManager.getEmployeeId().remove(employeeId); // remove from list
				lineManagerRepositoy.save(lineManager);
				
				System.out.println("SET Line Manager null to employee object");
				employee.setLineManagerId(null);
				employee.setLineManagerName(null);
			}else {
				System.out.println("Line Manager NULL in Employe Object.");
			}
			
		}

		// -----------------------------Team Leader Edit-----------------------------

		if (employeeDetails.getTeamLeaderId() != null) {
			System.out.println("Team Leader Id...............");

			if (employee.getTeamLeaderId() != null) {
				if (employeeDetails.getTeamLeaderId().longValue() != employee.getTeamLeaderId().longValue()) {
					// remove employee from previous team leader
					TeamLeader teamLeader = teamLeaderRepositoy.findById(employee.getTeamLeaderId())
							.orElseThrow(() -> new ResourceNotFoundException("Team Leader not found"));
					System.out.println("TL id: " + teamLeader.getId() + "TL child :" + teamLeader.getEmployeeId());
					teamLeader.getEmployeeId().remove(employeeId); // remove from list
					teamLeaderRepositoy.save(teamLeader);

					// add employee under new team leader
					Optional<TeamLeader> opTeamLeader = teamLeaderRepositoy.findById(employeeDetails.getTeamLeaderId());
					if (opTeamLeader.isPresent()) {
						TeamLeader oldTeamLeader = opTeamLeader.get();
						oldTeamLeader.getEmployeeId().add(employeeId);
						teamLeaderRepositoy.save(oldTeamLeader);
					} else {// create new team leader
						TeamLeader newTeamLeader = new TeamLeader();
						newTeamLeader.setId(employeeDetails.getTeamLeaderId());
						List<Long> empId = new ArrayList<>();
						empId.add(employeeId);
						newTeamLeader.setEmployeeId(empId);
						teamLeaderRepositoy.save(newTeamLeader);
					}

					employee.setTeamLeaderId(employeeDetails.getTeamLeaderId());
					Employee tempTeamLead = employeeRepository.findById(employeeDetails.getTeamLeaderId())
							.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
					employee.setTeamLeaderName(tempTeamLead.getFirstName());
				}

			} else {
				// add employee under new team leader
				Optional<TeamLeader> opTeamLeader = teamLeaderRepositoy.findById(employeeDetails.getTeamLeaderId());
				if (opTeamLeader.isPresent()) {
					TeamLeader oldTeamLeader = opTeamLeader.get();
					oldTeamLeader.getEmployeeId().add(employeeId);
					teamLeaderRepositoy.save(oldTeamLeader);
				} else {// create new team leader
					TeamLeader newTeamLeader = new TeamLeader();
					newTeamLeader.setId(employeeDetails.getTeamLeaderId());
					List<Long> empId = new ArrayList<>();
					empId.add(employeeId);
					newTeamLeader.setEmployeeId(empId);
					teamLeaderRepositoy.save(newTeamLeader);
				}
				employee.setTeamLeaderId(employeeDetails.getTeamLeaderId());
				Employee tempTeamLead = employeeRepository.findById(employeeDetails.getTeamLeaderId())
						.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
				employee.setTeamLeaderName(tempTeamLead.getFirstName());
			}

		} else {
			//if team leader is set to null
			// remove employee from previous team leader
			
			if(employee.getTeamLeaderId() != null) {
				System.out.println("Team Lead NOT NULL in Employe Object.");
				System.out.println(employee.getTeamLeaderId());
				
				TeamLeader teamLeader = teamLeaderRepositoy.findById(employee.getTeamLeaderId())
						.orElseThrow(() -> new ResourceNotFoundException("Team Leader not found"));
				System.out.println("TL id: " + teamLeader.getId() + " TL child : " + teamLeader.getEmployeeId());
				teamLeader.getEmployeeId().remove(employeeId); // remove from child employee list
				teamLeaderRepositoy.save(teamLeader);
				
				System.out.println("SET Team lead null to employee object");
				employee.setTeamLeaderId(null);
				employee.setTeamLeaderName(null);
				
			}else {
				System.out.println("Team Leader NULL in Employe Object");
			}
		}

		// --------------------------------HoD Edit ------------------------------------

		if (employeeDetails.getHeadOfDepartmentId() != null) {

			if (employee.getHeadOfDepartmentId() != null) {
				if (employeeDetails.getHeadOfDepartmentId().longValue() != employee.getHeadOfDepartmentId()
						.longValue()) {
					// remove employee from previous head of department
					HeadOfDepartment headOfDepartment = headOfDepartmentRepositoy
							.findById(employee.getHeadOfDepartmentId())
							.orElseThrow(() -> new ResourceNotFoundException("Head of Department not found"));
					System.out.println("Hod id : " + headOfDepartment.getId() + "HoD child : " + headOfDepartment.getEmployeeId());
					headOfDepartment.getEmployeeId().remove(employeeId);
					headOfDepartmentRepositoy.save(headOfDepartment);

					Optional<HeadOfDepartment> opHeadOfDepartment = headOfDepartmentRepositoy
							.findById(employeeDetails.getHeadOfDepartmentId());
					if (opHeadOfDepartment.isPresent()) {
						HeadOfDepartment oldHeadOfDepartment = opHeadOfDepartment.get();
						oldHeadOfDepartment.getEmployeeId().add(employeeId);
						headOfDepartmentRepositoy.save(oldHeadOfDepartment);
					} else {// create new HoD
						HeadOfDepartment newHeadOfDepartment = new HeadOfDepartment();
						newHeadOfDepartment.setId(employeeDetails.getHeadOfDepartmentId());
						List<Long> empId = new ArrayList<>();
						empId.add(employeeId);
						newHeadOfDepartment.setEmployeeId(empId);
						headOfDepartmentRepositoy.save(newHeadOfDepartment);
					}

					employee.setHeadOfDepartmentId(employeeDetails.getHeadOfDepartmentId());
					Employee headOfDept = employeeRepository.findById(employeeDetails.getHeadOfDepartmentId())
							.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
					employee.setHeadOfDepartmentName(headOfDept.getFirstName());

				}
			} else {
				// add employee under new head of department
				Optional<HeadOfDepartment> opHeadOfDepartment = headOfDepartmentRepositoy
						.findById(employeeDetails.getHeadOfDepartmentId());
				if (opHeadOfDepartment.isPresent()) {
					HeadOfDepartment oldHeadOfDepartment = opHeadOfDepartment.get();
					oldHeadOfDepartment.getEmployeeId().add(employeeId);
					headOfDepartmentRepositoy.save(oldHeadOfDepartment);
				} else {// create new HoD
					HeadOfDepartment newHeadOfDepartment = new HeadOfDepartment();
					newHeadOfDepartment.setId(employeeDetails.getHeadOfDepartmentId());
					List<Long> empId = new ArrayList<>();
					empId.add(employeeId);
					newHeadOfDepartment.setEmployeeId(empId);
					headOfDepartmentRepositoy.save(newHeadOfDepartment);
				}

				employee.setHeadOfDepartmentId(employeeDetails.getHeadOfDepartmentId());
				Employee headOfDept = employeeRepository.findById(employeeDetails.getHeadOfDepartmentId())
						.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
				employee.setHeadOfDepartmentName(headOfDept.getFirstName());
			}

		} else {
			//if HoD is set to null
			//remove employee from previous head of department
			if(employee.getHeadOfDepartmentId() != null) {
				System.out.println("HoD NOT NULL in Employe Object.");
				System.out.println(employee.getHeadOfDepartmentId());
				
				HeadOfDepartment headOfDepartment = headOfDepartmentRepositoy
						.findById(employee.getHeadOfDepartmentId())
						.orElseThrow(() -> new ResourceNotFoundException("Head of Department not found"));
				System.out.println("Hod id : " + headOfDepartment.getId() + "HoD child : " + headOfDepartment.getEmployeeId());
				headOfDepartment.getEmployeeId().remove(employeeId);
				headOfDepartmentRepositoy.save(headOfDepartment);
				
				System.out.println("SET HoD null to employee object");
				employee.setHeadOfDepartmentId(null);
				employee.setHeadOfDepartmentName(null);	
			}
			System.out.println("HoD NULL in Employe Object");
		}

		System.out.println("before save");
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);

	}

	@Override
	public ResponseEntity<?> updateEmployeeOfficialInfo(Long employeeId, Employee employeeDetails)
			throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		// employee.setActive(employeeDetails.getActive());
		// employee.setOfficialEmail(employeeDetails.getOfficialEmail());

		employee.setCompany(employeeDetails.getCompany());
		employee.setDepartment(employeeDetails.getDepartment());
		employee.setDesignation(employeeDetails.getDesignation());
		employee.setLocation(employeeDetails.getLocation());

		employee.setJoiningDate(employeeDetails.getJoiningDate());
		employee.setProbationStartDate(employeeDetails.getProbationStartDate());
		employee.setConfirmationDate(employeeDetails.getConfirmationDate());
		employee.setEmploymentStatus(employeeDetails.getEmploymentStatus());

		employee.setOfficialContact(employeeDetails.getOfficialContact());
		employee.setIsLineManager(employeeDetails.getIsLineManager());
		employee.setIsTeamLeader(employeeDetails.getIsTeamLeader());
		employee.setIsHeadOfDepartment(employeeDetails.getIsHeadOfDepartment());
		employee.setIsProjectManager(employeeDetails.getIsProjectManager());

//		employee.setLineManagerId(employeeDetails.getLineManagerId());
//		Employee tempLineManager = employeeRepository.findById(employeeDetails.getLineManagerId())
//				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
//		employee.setLineManagerName(tempLineManager.getFirstName());
//		
//		employee.setTeamLeaderId(employeeDetails.getTeamLeaderId());
//		Employee tempTeamLead = employeeRepository.findById(employeeDetails.getTeamLeaderId())
//				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
//		employee.setTeamLeaderName(tempTeamLead.getFirstName());
//		
//		employee.setHeadOfDepartmentId(employeeDetails.getHeadOfDepartmentId());
//		Employee headOfDept = employeeRepository.findById(employeeDetails.getHeadOfDepartmentId())
//				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
//		employee.setHeadOfDepartmentName(headOfDept.getFirstName());

		System.out.println("==============================================");

		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);

	}

	@Override
	public ResponseEntity<?> updatePersonalInformation(Long employeeId, PersonalInformation personalInformationDetails)
			throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		employee.setFatherName(personalInformationDetails.getFatherName());
		employee.setMotherName(personalInformationDetails.getMotherName());
		employee.setBirthDate(personalInformationDetails.getBirthDate());
		employee.setGender(personalInformationDetails.getGender());
		employee.setNationality(personalInformationDetails.getNationality());
		employee.setReligion(personalInformationDetails.getReligion());
		employee.setBloodGroup(personalInformationDetails.getBloodGroup());
		employee.setMaritalStatus(personalInformationDetails.getMaritalStatus());
		employee.setNidNumber(personalInformationDetails.getNidNumber());
		employee.setPermanentAddress(personalInformationDetails.getPermanentAddress());
		employee.setPresentAddress(personalInformationDetails.getPresentAddress());
		employee.setPersonalEmail(personalInformationDetails.getPersonalEmail());
		// employee.setEmploymentStatus(personalInformationDetails.getEmploymentStatus());

		final Employee updatedemployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedemployee);
	}

	@Override
	public ResponseEntity<?> updatePublicInformation(Long employeeId, PublicInformation publicInformation)
			throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> updateStatus(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		if (employee.getActive())
			employee.setActive(false);
		else
			employee.setActive(true);

		final Employee savedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok().body(savedEmployee);
	}

}
