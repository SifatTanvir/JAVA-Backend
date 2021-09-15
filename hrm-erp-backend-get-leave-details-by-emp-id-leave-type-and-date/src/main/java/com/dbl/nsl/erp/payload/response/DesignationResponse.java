package com.dbl.nsl.erp.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DesignationResponse {
	
	private String designationName;
	private Long totalEmployee;
	
	public DesignationResponse() {
		
	}
	public DesignationResponse(String designationName, Long totalEmployee) {
		this.designationName = designationName;
		this.totalEmployee = totalEmployee;
	}
	
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public Long getTotalEmployee() {
		return totalEmployee;
	}
	public void setTotalEmployee(Long totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
}
