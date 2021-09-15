package com.dbl.nsl.erp.services;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.ERole;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.Role;
import com.dbl.nsl.erp.payload.request.LoginRequest;
import com.dbl.nsl.erp.payload.request.SignupRequest;
import com.dbl.nsl.erp.payload.request.UpdatePasswordByHr;
import com.dbl.nsl.erp.payload.request.UpdatePasswordByUsername;
import com.dbl.nsl.erp.payload.request.UpdatePasswordRequest;
import com.dbl.nsl.erp.payload.request.UpdateSignupRequest;
import com.dbl.nsl.erp.payload.response.JwtResponse;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.RoleRepository;
import com.dbl.nsl.erp.security.jwt.JwtUtils;
import com.dbl.nsl.erp.security.services.UserDetailsImpl;


@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailSenderService mailService;

	public Set<Role> getRolesSet(Set<String> strRoles) {
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Role is not found"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "Admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(adminRole);

					break;
				case "Assistant Admin":
					Role assistantAdminRole = roleRepository.findByName(ERole.ROLE_ASSISTANT_ADMIN)
					.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(assistantAdminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
					.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(userRole);
				}
			});
		}
		return roles;
	}

	// Function to generate random alpha-numeric password of specific length
	public static String generateRandomPassword() {
		int len = 8;
		// ASCII range - alphanumeric (0-9, a-z, A-Z)
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();

		// each iteration of loop choose a character randomly from the given ASCII range
		// and append it to StringBuilder instance

		for (int i = 0; i < len; i++) {
			int randomIndex = random.nextInt(chars.length());
			sb.append(chars.charAt(randomIndex));
		}

		return sb.toString();
	}

	@Override
	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
		
		Boolean isActive = employeeRepository.findEmployeeStatusByUserName(loginRequest.getUsername());
		System.out.println("================");
		System.out.println(isActive);
		
		if(isActive) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getFirstName(), userDetails.getLastName(), userDetails.getIsLineManager(), userDetails.getIsProjectManager(),
					userDetails.getIsHeadOfDepartment(), userDetails.getIsTeamLeader(), userDetails.getProfilePicPath(),
					userDetails.getEmail(), roles));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Inactive Employee"));
		}
	}

	@Override
	public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {

		if (employeeRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Username already used"));
		}

		if (employeeRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Email already used"));
		}

		Employee employee = new Employee();
		employee.setId(signUpRequest.getId());
		employee.setUsername(signUpRequest.getUsername());
		employee.setEmail(signUpRequest.getEmail());
		employee.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		roles = getRolesSet(strRoles);

		employee.setRoles(roles);
		// employeeRepository.save(employee);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@Override
	public ResponseEntity<?> updateUser(String username, UpdateSignupRequest updateUser) throws ResourceNotFoundException {

		Employee user = employeeRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		if (!employeeRepository.existsByEmail(updateUser.getEmail())) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new MessageResponse("Email not found"));
		}

		if (!employeeRepository.existsByUsernameAndEmail(username, updateUser.getEmail())) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new MessageResponse("Username and Email mismatch"));
		}

		Set<String> strRoles = updateUser.getNewRole();
		Set<Role> roles = new HashSet<>();
		roles = getRolesSet(strRoles);

		boolean isPasswordMatch = passwordEncoder.matches(updateUser.getPassword(), user.getPassword());
		if (!isPasswordMatch) {
			return ResponseEntity.badRequest().body(new MessageResponse("Old password mismatch"));
		}

		user.setEmail(updateUser.getNewEmail());
		user.setPassword(encoder.encode(updateUser.getNewPassword()));
		user.setRoles(roles);

		final Employee updatedUser = employeeRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@Override
	public ResponseEntity<?> deleteUserAccount(String username) throws ResourceNotFoundException {

		Employee user = employeeRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		employeeRepository.delete(user);
		return ResponseEntity.ok().body("Account Deleted Successfully");
	}

	@Override
	public ResponseEntity<?> updatePassword(Long employeeId, UpdatePasswordRequest updatePasswordRequest) 
			throws ResourceNotFoundException {

		Employee user = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		boolean isPasswordMatch = passwordEncoder.matches(updatePasswordRequest.getPassword(), user.getPassword());
		if (!isPasswordMatch) {
			return ResponseEntity.badRequest().body(new MessageResponse("Old password mismatch"));
		}

		user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
		final Employee updatedUser = employeeRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@Override
	public ResponseEntity<?> updatePasswordByHr(Long employeeId, UpdatePasswordByHr updatePasswordByHr) throws ResourceNotFoundException {

		Employee user = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		user.setPassword(encoder.encode(updatePasswordByHr.getNewPassword()));
		final Employee updatedUser = employeeRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@Override
	public ResponseEntity<?> updatePasswordByAuto(UpdatePasswordByUsername updatePasswordByUsername) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findByUsername(updatePasswordByUsername.getUserName())
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		String autoGeneratedRandomPassword = generateRandomPassword();

		employee.setPassword(encoder.encode(autoGeneratedRandomPassword));

		String sendTo = employee.getUsername(); // employee official email

		String subject = "Password Reset Successfully";

		String message = "CONGRATULATIONS ! \n\n"
				+ "Your password has been changed successfully.\n"
				+ "Your New Password : " + autoGeneratedRandomPassword + "  "
				+ "Please Reset your password after the first login.\n\n"
				+ "Best Regards,\nTafuri Technoogies Ltd.";

		try {
			mailService.sendEmail(sendTo, subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		employeeRepository.save(employee);
		return ResponseEntity.ok(new MessageResponse("Password generated successfully and sent to mail"));
	}

}
