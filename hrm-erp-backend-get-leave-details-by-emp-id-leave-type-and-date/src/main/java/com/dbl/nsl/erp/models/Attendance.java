package com.dbl.nsl.erp.models;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "attendances")
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@Column(name = "employee_id")
	private Long employeeId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "designation_name")
	private String designationName;

	@Column(name = "department_name")
	private String departmentName;

	@Column
	@JsonFormat(pattern = "HH:mm:ss")
	private Time inTime;

	@Column
	@JsonFormat(pattern = "HH:mm:ss")
	private Time outTime;

	@Column(name = "status")
	private String status;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "company_id")
	private Long companyId;
	
	@Column(name = "is_edited")
	private Boolean isEdited;
	
	@Column(name = "is_in_time_edited")
	private Boolean isInTimeEdited;
	
	@Column(name = "is_out_time_edited")
	private Boolean isOutTimeEdited;
	
	@Column(name = "employment_status")
	private String employmentStatus;
	
	@Column
	@JsonFormat(pattern = "HH:mm:ss")
	private Time updatedInTime;
	
	@Column
	@JsonFormat(pattern = "HH:mm:ss")
	private Time updatedOutTime;
	
	@Column(name = "send_edit_request")
	private Boolean sendEditRequest;
	
	@Column(name= "edit_reason")
	private String editReason;
	
	@Column(name = "updated_status")
	private String updatedStatus;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date sendingDate;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date acceptedDate;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rejectedDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Time getInTime() {
		return inTime;
	}

	public void setInTime(Time inTime) {
		this.inTime = inTime;
	}

	public Time getOutTime() {
		return outTime;
	}

	public void setOutTime(Time outTime) {
		this.outTime = outTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}
	
	public Boolean getIsInTimeEdited() {
		return isInTimeEdited;
	}

	public void setIsInTimeEdited(Boolean isInTimeEdited) {
		this.isInTimeEdited = isInTimeEdited;
	}
	
	public Boolean getIsOutTimeEdited() {
		return isOutTimeEdited;
	}

	public void setIsOutTimeEdited(Boolean isOutTimeEdited) {
		this.isOutTimeEdited = isOutTimeEdited;
	}
	
	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public Time getUpdatedInTime() {
		return updatedInTime;
	}

	public void setUpdatedInTime(Time updatedInTime) {
		this.updatedInTime = updatedInTime;
	}

	public Time getUpdatedOutTime() {
		return updatedOutTime;
	}

	public void setUpdatedOutTime(Time updatedOutTime) {
		this.updatedOutTime = updatedOutTime;
	}

	public Boolean isSendEditRequest() {
		return sendEditRequest;
	}

	public void setSendEditRequest(Boolean sendEditRequest) {
		this.sendEditRequest = sendEditRequest;
	}

	public void setEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}

	public String getEditReason() {
		return editReason;
	}

	public void setEditReason(String editReason) {
		this.editReason = editReason;
	}

	public String getUpdatedStatus() {
		return updatedStatus;
	}

	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}
	
	public Date getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}

	public Date getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	
	public Date getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(Date rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	
	
	
}
