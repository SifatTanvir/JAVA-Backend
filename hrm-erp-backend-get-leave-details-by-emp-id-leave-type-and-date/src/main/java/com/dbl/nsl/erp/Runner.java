package com.dbl.nsl.erp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.AnnualLeave;
import com.dbl.nsl.erp.models.ERole;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.HeadOfDepartment;
import com.dbl.nsl.erp.models.LeaveRequest;
import com.dbl.nsl.erp.models.LineManager;
import com.dbl.nsl.erp.models.Role;
import com.dbl.nsl.erp.models.SequenceStorer;
import com.dbl.nsl.erp.models.TeamLeader;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.HeadOfDepartmentRepository;
import com.dbl.nsl.erp.repository.LeaveRequestRepository;
import com.dbl.nsl.erp.repository.LineManagerRepository;
import com.dbl.nsl.erp.repository.RoleRepository;
import com.dbl.nsl.erp.repository.SequenceStorerRepository;
import com.dbl.nsl.erp.repository.TeamLeaderRepository;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SequenceStorerRepository sequenceStorerRepository;

	@Autowired
	LineManagerRepository lineManagerRepository;

	@Autowired
	TeamLeaderRepository teamleaderRepository;

	@Autowired
	HeadOfDepartmentRepository headOfDeptRepository;

	@Autowired
	LeaveRequestRepository leaveRequestRepository;

	@Override
	public void run(String... args) throws Exception {

		List<Role> storedRoles = roleRepository.findAll();

		if (storedRoles.size() == 0) {

			Role userRole = new Role();
			userRole.setName(ERole.ROLE_USER);
			roleRepository.save(userRole);

			Role assistantAdminRole = new Role();
			assistantAdminRole.setName(ERole.ROLE_ASSISTANT_ADMIN);
			roleRepository.save(assistantAdminRole);

			Role adminRole = new Role();
			adminRole.setName(ERole.ROLE_ADMIN);
			roleRepository.save(adminRole);

			Employee employee = new Employee();
			employee.setId(1l);
			employee.setUsername("nsl@nsl.com"); // need to change at line no 289 in attendanceServiceImpl file
			employee.setEmail("admin@nsl.com");
			employee.setFirstName("Admin");
			employee.setLastName("Admin");
			employee.setActive(true);

			ERole e = ERole.ROLE_ADMIN;
			Optional<Role> role = roleRepository.findByName(e);
			Role rol = role.get();
			Set<Role> roles = new HashSet<>();
			roles.add(rol);
			employee.setRoles(roles);

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode("12345678");
			employee.setPassword(hashedPassword);

			employeeRepository.save(employee);

			SequenceStorer seqStorer = new SequenceStorer();
			seqStorer.setSequenceNumber(1l);
			sequenceStorerRepository.save(seqStorer);

			System.out.println("intial data stored. ");
		} else {
			System.out.println("already stored initial data. ");
		}
		System.out.println("this is from runner.");
	}
}
