package com.dbl.nsl.erp.models;

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
@Table(name = "seperation_information")
public class SeperationInformation {
	
	public SeperationInformation() {

	}

	public SeperationInformation(Date dateOfSeperation, Date noticePeriod, Long employeeId, String firstName,
			String lastName, String seperationType, String seperationReason, Date dateOfResignation, 
			Date dateOfSalaryEntitlement, String seperationPicPath) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfSeperation = dateOfSeperation;
		this.noticePeriod = noticePeriod;
		this.seperationType = seperationType;
		this.seperationReason = seperationReason; 
		this.dateOfResignation = dateOfResignation;
		this.dateOfSalaryEntitlement = dateOfSalaryEntitlement;
		this.seperationPicPath = seperationPicPath;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "employee_id")
	private Long employeeId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateOfSeperation;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date noticePeriod;
	
	@Column(name = "seperation_type")
	private String seperationType;
	
	@Column(name = "seperation_reason")
	private String seperationReason;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateOfResignation;
	
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateOfSalaryEntitlement;
	
	@Column(name="seperation_picture")
	private String seperationPicPath;

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
	
	public Date getDateOfSeperation() {
		return dateOfSeperation;
	}

	public void setDateOfSeperation(Date dateOfSeperation) {
		this.dateOfSeperation = dateOfSeperation;
	}
	
	public Date getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(Date noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	
	public String getSeperationType() {
		return seperationType;
	}

	public void setSeperationType(String seperationType) {
		this.seperationType = seperationType;
	}
	
	public String getSeperationReason() {
		return seperationReason;
	}

	public void setSeperationReason(String seperationReason) {
		this.seperationReason = seperationReason;
	}
	
	public Date getDateOfResignation() {
		return dateOfResignation;
	}

	public void setDateOfResignation(Date dateOfResignation) {
		this.dateOfResignation = dateOfResignation;
	}
	
	public Date getDateOfSalaryEntitlement() {
		return dateOfSalaryEntitlement;
	}

	public void setDateOfSalaryEntitlement(Date dateOfSalaryEntitlement) {
		this.dateOfSalaryEntitlement = dateOfSalaryEntitlement;
	}
	
	public String getSeperationPicPath() {
		return seperationPicPath;
	}

	public void setSeperationPicPath(String seperationPicPath) {
		this.seperationPicPath = seperationPicPath;
	}
	
}
