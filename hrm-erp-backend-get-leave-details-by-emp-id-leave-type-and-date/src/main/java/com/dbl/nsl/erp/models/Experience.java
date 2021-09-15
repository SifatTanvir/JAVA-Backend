package com.dbl.nsl.erp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "experiences")
public class Experience {
	public Experience() {
		
	}
	public Experience(String company, String designation, String startDate, String endDate, String duration,
			String experienceCertificateUrl) {
		this.company = company;
		this.designation = designation;
		this.duration = duration;
		this.startDate = startDate;
		this.endDate = endDate;
		this.experienceCertificateUrl = experienceCertificateUrl;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "company")
	private String company;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "duration")
	private String duration;
	
	@Column(name = "start_date")
	private String startDate;
	 
	@Column(name = "end_date")
	private String endDate;
	
	@Column(name = "certificate_url")
	private String experienceCertificateUrl;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
	
	public String getExperienceCertificateUrl() {
		return experienceCertificateUrl;
	}
	public void setExperienceCertificateUrl(String experienceCertificateUrl) {
		this.experienceCertificateUrl = experienceCertificateUrl;
	}
	
	@JsonBackReference
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee; 
	}
}
