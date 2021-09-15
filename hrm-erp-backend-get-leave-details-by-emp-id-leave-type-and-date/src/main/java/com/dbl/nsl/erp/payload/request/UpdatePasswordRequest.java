package com.dbl.nsl.erp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePasswordRequest {
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	@NotBlank
	@Size(min = 6, max = 40)
	@JsonProperty("new_password")
	private String NewPassword;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return NewPassword;
	}

	public void setNewPassword(String NewPassword) {
		this.NewPassword = NewPassword;
	}
}
