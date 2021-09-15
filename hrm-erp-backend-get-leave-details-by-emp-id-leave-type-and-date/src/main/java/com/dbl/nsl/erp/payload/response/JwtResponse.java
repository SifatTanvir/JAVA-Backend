package com.dbl.nsl.erp.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;
	private Boolean IsLineManager;
	private Boolean IsProjectManager;
	private Boolean IsHeadOfDepartment;
	private Boolean IsTeamLeader;
	private String profilePicPath;

	public JwtResponse(String accessToken, Long id, String username, String firstName, String lastName, Boolean IsLineManager,
			Boolean IsProjectManager, Boolean IsHeadOfDepartment, Boolean IsTeamLeader, String profilePicPath, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.IsLineManager = IsLineManager;
		this.email = email;
		this.roles = roles;
		this.IsProjectManager = IsProjectManager;
		this.IsHeadOfDepartment = IsHeadOfDepartment;
		this.IsTeamLeader = IsTeamLeader;
		this.profilePicPath = profilePicPath;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getRoles() {
		return roles;
	}
	
	public Boolean getIsLineManager() {
		return IsLineManager;
	}

	public void setIsLineManager(Boolean IsLineManager) {
		this.IsLineManager = IsLineManager;
	}
	
	public Boolean getIsProjectManager() {
		return IsProjectManager;
	}

	public void setIsProjectManager(Boolean IsProjectManager) {
		this.IsProjectManager = IsProjectManager;
	}
	
	public Boolean getIsTeamLeader() {
		return IsTeamLeader;
	}

	public void setIsTeamLeader(Boolean IsTeamLeader) {
		this.IsTeamLeader = IsTeamLeader;
	}
	
	public Boolean getIsHeadOfDepartment() {
		return IsHeadOfDepartment;
	}

	public void setIsHeadOfDepartment(Boolean IsHeadOfDepartment) {
		this.IsHeadOfDepartment = IsHeadOfDepartment;
	}
	
	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}
}
