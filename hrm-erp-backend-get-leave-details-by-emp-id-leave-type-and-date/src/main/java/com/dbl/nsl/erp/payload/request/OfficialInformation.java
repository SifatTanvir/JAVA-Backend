package com.dbl.nsl.erp.payload.request;

import java.util.Date;
import java.util.List;

import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Designation;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OfficialInformation {

	private Long employeeId;
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private List<Company> company;
	
	private List<Department> department;
	
	private List<Designation> designation;
	
	private Long projectManagerId;
	
	private String projectManagerName;
	
	private Long lineManagerId;
	
	private String lineManagerName;
	
	private Long teamLeaderId;
	
	private String teamLeaderName;
	
	private Long headOfDeptId;
	
	private String headOfDeptName;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date confirmationDate;
	
	private String officialContact;
	
	private String employmentStatus;

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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<Company> getCompany() {
		return company;
	}
	
	public void setCompany(List<Company> company) {
		this.company = company;
	}
	
	public List<Department> getDepartment() {
		return department;
	}
	
	public void setDepartment(List<Department> department) {
		this.department = department;
	}
	
	public List<Designation> getDesignation() {
		return designation;
	}
	
	public void setDesignation(List<Designation> designation) {
		this.designation = designation;
	}
	
	public Long getProjectManagerId() {
		return projectManagerId;
	}
	
	public void setProjectManagerId(Long projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
	
	public String getProjectManagerName() {
		return projectManagerName;
	}
	
	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}
	
	public Long getLineManagerId() {
		return lineManagerId;
	}
	
	public void setLineManagerId(Long lineManagerId) {
		this.lineManagerId = lineManagerId;
	}
	
	public String getLineManagerName() {
		return lineManagerName;
	}
	
	public void setLineManagerName(String lineManagerName) {
		this.lineManagerName = lineManagerName;
	}
	
	public Long getTeamLeaderId() {
		return teamLeaderId;
	}
	
	public void setTeamLeaderId(Long teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}
	
	public String getTeamLeaderName() {
		return teamLeaderName;
	}
	
	public void setTeamLeaderName(String teamLeaderName) {
		this.teamLeaderName = teamLeaderName;
	}
	
	public Long getHeadOfDeptId() {
		return headOfDeptId;
	}
	
	public void setHeadOfDeptId(Long headOfDeptId) {
		this.headOfDeptId = headOfDeptId;
	}
	
	public String getHeadOfDeptName() {
		return headOfDeptName;
	}
	
	public void setHeadOfDeptName(String headOfDeptName) {
		this.headOfDeptName = headOfDeptName;
	}
	
	public Date getConfirmationDate() {
		return confirmationDate;
	}
	
	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	
	public String getOfficialContact() {
		return officialContact;
	}
	
	public void setOfficialContact(String officialContact) {
		this.officialContact = officialContact;
	}
	
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}


}
