package com.dbl.nsl.erp.payload.request;

import javax.validation.constraints.NotBlank;

public class UpdatePasswordByUsername {
	@NotBlank
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
