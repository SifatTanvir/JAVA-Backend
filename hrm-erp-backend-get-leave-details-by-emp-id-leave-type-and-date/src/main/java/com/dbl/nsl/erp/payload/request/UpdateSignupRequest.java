package com.dbl.nsl.erp.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateSignupRequest {
//	@NotBlank
//    @Size(min = 3, max = 20)
//    private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 50)
	@Email
	@JsonProperty("new_email")
	private String NewEmail;

	private Set<String> role;

	@JsonProperty("new_role")
	private Set<String> newRole;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	@NotBlank
	@Size(min = 6, max = 40)
	@JsonProperty("new_password")
	private String NewPassword;

//	private List<Department> departments;

//    @NotBlank
//    @Size(min = 6, max = 40)
//    private String oldpassword;

//    public String getUsername() {
//        return username;
//    }
// 
//    public void setUsername(String username) {
//        this.username = username;
//    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewEmail() {
		return NewEmail;
	}

	public void setNewEmail(String NewEmail) {
		this.NewEmail = NewEmail;
	}

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

//    public String getOldPassword() {
//        return oldpassword;
//    }
// 
//    public void setOldPassword(String oldpassword) {
//        this.oldpassword = oldpassword;
//    }

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public Set<String> getNewRole() {
		return this.newRole;
	}

	public void setNewRole(Set<String> newRole) {
		this.newRole = newRole;
	}

//	public List<Department> getDepartment() {
//		return departments;
//	}
//	public void setDepartment(List<Department> departments) {
//		this.departments = departments;
//	}

}
