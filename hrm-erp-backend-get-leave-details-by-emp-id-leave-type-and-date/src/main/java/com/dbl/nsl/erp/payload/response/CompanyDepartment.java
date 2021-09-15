package com.dbl.nsl.erp.payload.response;

public class CompanyDepartment {
	public CompanyDepartment() {
		
	}
	
	public CompanyDepartment(String companyName, String departmentName ) {
		this.companyName = companyName;
		this.departmentName = departmentName;
		
	}
	private String companyName;
	private String departmentName;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
