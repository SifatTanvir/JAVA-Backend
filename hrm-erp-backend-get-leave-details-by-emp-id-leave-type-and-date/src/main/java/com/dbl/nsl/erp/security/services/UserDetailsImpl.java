package com.dbl.nsl.erp.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dbl.nsl.erp.models.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;
	
	private String firstName;
	
	private String lastName;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	private Boolean IsLineManager;
	
	private Boolean IsProjectManager;
	
	private Boolean IsTeamLeader;
	
	private Boolean IsHeadOfDepartment;
	
	private String profilePicPath;

	public UserDetailsImpl(Long id, String username, String firstName, String lastName, Boolean IsLineManager, 
			Boolean IsProjectManager, Boolean IsTeamLeader, Boolean IsHeadOfDepartment, String profilePicPath,
			String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.IsLineManager = IsLineManager;
		this.IsProjectManager = IsProjectManager;
		this.IsTeamLeader = IsTeamLeader;
		this.IsHeadOfDepartment = IsHeadOfDepartment;
		this.profilePicPath = profilePicPath;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

//	public static UserDetailsImpl build(User user) {
	public static UserDetailsImpl build(Employee user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
//				user.getEmployeeId(),
				user.getId(),
				user.getUsername(), 
				user.getFirstName(),
				user.getLastName(),
				user.getIsLineManager(),
				user.getIsHeadOfDepartment(),
				user.getIsProjectManager(),
				user.getIsTeamLeader(),
				user.getProfilePicPath(),
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public Boolean getIsLineManager() {
		return IsLineManager;
	}
	
	public Boolean getIsProjectManager() {
		return IsProjectManager;
	}
	
	public Boolean getIsTeamLeader() {
		return IsTeamLeader;
	}
	
	public Boolean getIsHeadOfDepartment() {
		return IsHeadOfDepartment;
	}
	
	public String getProfilePicPath() {
		return profilePicPath;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public String toString() {
		return "UserDetailsImpl [id=" + id + ", username=" + username + ", firstName=" + firstName + ", email=" + email
				+ ", password=" + password + ", authorities=" + authorities + ", IsLineManager=" + IsLineManager + "]";
	}
	
	
	
	
}
