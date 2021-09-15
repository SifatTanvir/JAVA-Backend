package com.dbl.nsl.erp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePasswordByHr {
	@NotBlank
	@Size(min = 6, max = 40)
	@JsonProperty("new_password")
	private String NewPassword;
	
	public String getNewPassword() {
		return NewPassword;
	}

	public void setNewPassword(String NewPassword) {
		this.NewPassword = NewPassword;
	}

}
