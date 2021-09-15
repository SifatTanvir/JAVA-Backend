package com.dbl.nsl.erp.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "leave_requests")
public class LeaveRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="sender_id")
	private Long senderId;
	
	@Column(name="sender_first_name")
	private String senderFirstName;
	
	@Column(name="sender_last_name")
	private String senderLastName;
	
	@Column(name = "start_date")
	private String startDate;
	
	@Column(name = "end_date")
	private String endDate;
	
	@Column(name = "duration")
	private Long duration;
	
	@Column(name = "calendar_days")
	private Long calendarDays;
	
	@Column(name = "leave_type")
	private String leaveType;
	
	@Column(name = "is_accepted")
	private int isAccepted;
	
	@Column(name="accepted_by_line_manager")
	private int isAcceptedByLineManager;
	
	@Column(name="accepted_by_team_leader")
	private int isAcceptedByTeamLeader;
	
	@Column(name="accepted_by_Dept_Head")
	private int isAcceptedByHeadOfDept;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date sendingDate;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date actionDateByLineManager;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date actionDateByTeamLeader;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date actionDateByHeadOfDept;
	
	@Column(name="message")
	private String message;
	
	@Column(name="attachment")
	private String attachmentPath;
	
	@Column(name="address")
	private String address;
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSenderFirstName() {
		return senderFirstName;
	}

	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}
	
	public String getSrnderLastName() {
		return senderLastName;
	}

	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public Long getCalendarDays() {
		return calendarDays;
	}

	public void setCalendarDays(Long calendarDays) {
		this.calendarDays = calendarDays;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	
	public int getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(int isAccepted) {
		this.isAccepted = isAccepted;
	}

	public int getIsAcceptedByLineManager() {
		return isAcceptedByLineManager;
	}

	public void setIsAcceptedByLineManager(int isAcceptedByLineManager) {
		this.isAcceptedByLineManager = isAcceptedByLineManager;
	}

	public int getIsAcceptedByTeamLeader() {
		return isAcceptedByTeamLeader;
	}

	public void setIsAcceptedByTeamLeader(int isAcceptedByTeamLeader) {
		this.isAcceptedByTeamLeader = isAcceptedByTeamLeader;
	}

	public int getIsAcceptedByHeadOfDept() {
		return isAcceptedByHeadOfDept;
	}

	public void setIsAcceptedByHeadOfDept(int isAcceptedByHeadOfDept) {
		this.isAcceptedByHeadOfDept = isAcceptedByHeadOfDept;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}

	public Date getActionDateByLineManager() {
		return actionDateByLineManager;
	}

	public void setActionDateByLineManager(Date actionDateByLineManager) {
		this.actionDateByLineManager = actionDateByLineManager;
	}

	public Date getActionDateByTeamLeader() {
		return actionDateByTeamLeader;
	}

	public void setActionDateByTeamLeader(Date actionDateByTeamLeader) {
		this.actionDateByTeamLeader = actionDateByTeamLeader;
	}

	public Date getActionDateByHeadOfDept() {
		return actionDateByHeadOfDept;
	}

	public void setActionDateByHeadOfDept(Date actionDateByHeadOfDept) {
		this.actionDateByHeadOfDept = actionDateByHeadOfDept;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}