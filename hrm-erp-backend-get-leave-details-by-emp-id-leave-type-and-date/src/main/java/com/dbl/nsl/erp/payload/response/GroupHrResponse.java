package com.dbl.nsl.erp.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupHrResponse {
	
	private String groupName;
	private Long totalEmployee;
//	private Long totalSickLeaveEmployee;
//	private Long totalCasualLeaveEmployee;
//	private Long totalAnnualLeaveEmployee;
    private Long totalPresentEmployee;
    private Long totalLateEmployee;
    private Long totalAbsentEmployee;
    private Date date;
	private Long totalLeaveEmployee;
    
    
    public GroupHrResponse() {
    	
    }
    
//    public GroupHrResponse(String groupName, Long totalPresentEmployee, Long totalLateEmployee,
//    		Long totalAbsentEmployee, Long totalEmployee, Long totalSickLeaveEmployee, Long totalCasualLeaveEmployee,
//    		Long totalAnnualLeaveEmployee, Long totalLeaveEmployee, Date date) {
    
    public GroupHrResponse(String groupName, Long totalPresentEmployee, Long totalLateEmployee,
    		Long totalAbsentEmployee, Long totalEmployee, Long totalLeaveEmployee, Date date) {
    	this.groupName = groupName;
    	this.totalEmployee = totalEmployee;
    	this.totalPresentEmployee = totalPresentEmployee;
    	this.totalLateEmployee = totalLateEmployee;
    	this.totalAbsentEmployee = totalAbsentEmployee;
//    	this.totalSickLeaveEmployee = totalSickLeaveEmployee;
//    	this.totalCasualLeaveEmployee = totalCasualLeaveEmployee;
//    	this.totalAnnualLeaveEmployee = totalAnnualLeaveEmployee;
    	this.totalLeaveEmployee = totalLeaveEmployee;
    	this.date = date;
    }
    
    public String getGroupName() {
    	return groupName;
    }
    public void setGroupName(String groupName) {
    	this.groupName = groupName;
    }
    public Long getTotalEmployee() {
    	return totalEmployee;
    }
    public void setTotalEmployee(Long totalEmployee) {
    	this.totalEmployee = totalEmployee;
    }
    public Long getTotalPresentEmployee() {
    	return totalPresentEmployee;
    }
    public void setTotalPresentEmployee(Long totalPresentEmployee) {
    	this.totalPresentEmployee = totalPresentEmployee;
    }
    public Long getTotalLateEmployee() {
    	return totalLateEmployee;
    }
    public void setTotalLateEmployee(Long totalLateEmployee) {
    	this.totalLateEmployee = totalLateEmployee;
    }
    public Long getTotalAbsentEmployee() {
    	return totalAbsentEmployee;
    }
    public void setTotalAbsentEmployee(long totalAbsentEmployee) {
    	this.totalAbsentEmployee = totalAbsentEmployee;
    }
//    public Long getTotalSickLeaveEmployee() {
//    	return totalSickLeaveEmployee;
//    }
//    public void setTotalSickLeaveEmployee(Long totalSickLeaveEmployee) {
//    	this.totalSickLeaveEmployee = totalSickLeaveEmployee;
//    }
//    public Long getTotalCasualLeaveEmployee() {
//    	return totalCasualLeaveEmployee;
//    }
//    public void setTotalCasualLeaveEmployee(Long totalCasualLeaveEmployee) {
//    	this.totalCasualLeaveEmployee = totalCasualLeaveEmployee;
//    }
//    public Long getTotalAnnualLeaveEmployee() {
//    	return totalAnnualLeaveEmployee;
//    }
//    public void setTotalAnnualLeaveEmployee(Long totalAnnualLeaveEmployee) {
//    	this.totalAnnualLeaveEmployee = totalAnnualLeaveEmployee;
//    }
    public Long getTotalLeaveEmployee() {
    	return totalLeaveEmployee;
    }
    public void setTotalLeaveEmployee(Long totalLeaveEmployee) {
    	this.totalLeaveEmployee = totalLeaveEmployee;
    }
    
    public Date getDate() {
    	return date;
    }
    public void setDate(Date date) {
    	this.date = date;
    }
}
