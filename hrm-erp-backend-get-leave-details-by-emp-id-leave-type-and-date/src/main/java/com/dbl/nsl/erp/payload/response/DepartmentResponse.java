package com.dbl.nsl.erp.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentResponse {
	
	private String departmentName;
	private String departmentLeadName;
	private Long totalEmployee;
	
	public DepartmentResponse() {
		
	}
	public DepartmentResponse(String departmentName, String departmentLeadName, Long totalEmployee ) {
		
		this.departmentName = departmentName;
		this.departmentLeadName = departmentLeadName;
		this.totalEmployee = totalEmployee;
		
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentLeadName() {
		return departmentLeadName;
	}
	public void setDepartmentLeadName(String departmentLeadName) {
		this.departmentLeadName = departmentLeadName;
	}
	public Long getTotalEmployee() {
		return totalEmployee;
	}
	public void setTotalEmployee(Long totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
}
