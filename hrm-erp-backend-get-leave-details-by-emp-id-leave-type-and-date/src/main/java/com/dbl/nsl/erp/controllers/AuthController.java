package com.dbl.nsl.erp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.payload.request.LoginRequest;
import com.dbl.nsl.erp.payload.request.SignupRequest;
import com.dbl.nsl.erp.payload.request.UpdatePasswordByHr;
import com.dbl.nsl.erp.payload.request.UpdatePasswordByUsername;
import com.dbl.nsl.erp.payload.request.UpdatePasswordRequest;
import com.dbl.nsl.erp.payload.request.UpdateSignupRequest;
import com.dbl.nsl.erp.services.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	
	@PostMapping("/account/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/account/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		return authService.registerUser(signUpRequest);
	}

	@PutMapping("/account/{username}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updateUser(@PathVariable(value = "username") String username,
			@Valid @RequestBody UpdateSignupRequest updateUser) throws ResourceNotFoundException {

		return authService.updateUser(username, updateUser);
	}

	@DeleteMapping("/account/{username}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> deleteUserAccount(@PathVariable(value = "username") String username)
			throws ResourceNotFoundException {
		
		return authService.deleteUserAccount(username);
	}

	@PutMapping("/account/{id}/updatepassword")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> updatePassword(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) throws ResourceNotFoundException {

		return authService.updatePassword(employeeId, updatePasswordRequest);
	}

	@PutMapping("/account/{id}/hr/updatepassword")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN')")
	public ResponseEntity<?> updatePasswordByHr(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody UpdatePasswordByHr updatePasswordByHr) throws ResourceNotFoundException {

		return authService.updatePasswordByHr(employeeId, updatePasswordByHr);
	}

	@PutMapping("/account/auto/updatepassword")
	public ResponseEntity<?> updatePasswordByAuto(@Valid @RequestBody UpdatePasswordByUsername updatePasswordByUsername)
			throws ResourceNotFoundException {
		
		return authService.updatePasswordByAuto(updatePasswordByUsername);

	}
}
