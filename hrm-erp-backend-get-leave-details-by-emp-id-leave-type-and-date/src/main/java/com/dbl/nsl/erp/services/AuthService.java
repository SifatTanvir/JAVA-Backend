package com.dbl.nsl.erp.services;

import org.springframework.http.ResponseEntity;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.payload.request.LoginRequest;
import com.dbl.nsl.erp.payload.request.SignupRequest;
import com.dbl.nsl.erp.payload.request.UpdatePasswordByHr;
import com.dbl.nsl.erp.payload.request.UpdatePasswordByUsername;
import com.dbl.nsl.erp.payload.request.UpdatePasswordRequest;
import com.dbl.nsl.erp.payload.request.UpdateSignupRequest;

public interface AuthService {
	
	ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
	
	ResponseEntity<?> registerUser(SignupRequest signUpRequest);
	
	ResponseEntity<?> updateUser(String username, UpdateSignupRequest updateUser) throws ResourceNotFoundException;
	
	ResponseEntity<?> deleteUserAccount(String username) throws ResourceNotFoundException;
	
	ResponseEntity<?> updatePassword(Long employeeId, UpdatePasswordRequest updatePasswordRequest) throws ResourceNotFoundException;
	
	ResponseEntity<?> updatePasswordByHr( Long employeeId, UpdatePasswordByHr updatePasswordByHr) throws ResourceNotFoundException;
	
	ResponseEntity<?> updatePasswordByAuto(UpdatePasswordByUsername updatePasswordByUsername) throws ResourceNotFoundException;
	
	

}
