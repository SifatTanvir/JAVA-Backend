package com.dbl.nsl.erp.payload.response;

public class ShortEmployeeInfo {
	
	private Long employeeId;
	private String firstName;
	private String lastName;
	
	public ShortEmployeeInfo() {
		
	}
	
	public ShortEmployeeInfo (Long employeeId, String firstName, String lastName) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
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
}
