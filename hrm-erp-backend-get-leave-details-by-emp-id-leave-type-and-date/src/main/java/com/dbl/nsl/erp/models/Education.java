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
@Table(name = "educations")
public class Education {
	public Education() {
		
	}
	public Education(String degree, String institute, String passingYear, String major,
			Long grade, String result, String educationCertificateUrl) {
		this.degree = degree;
		this.institute = institute;
		this.passingYear = passingYear;
		this.major = major;
		this.grade = grade;
		this.result = result;
		this.educationCertificateUrl = educationCertificateUrl;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "degree")
	private String degree;
	
	@Column(name = "institute")
	private String institute;
	
	@Column(name = "passing_year")
	private String passingYear;
	
	@Column(name = "major")
	private String major;
	
	@Column(name = "grade")
	private Long grade;
	
	@Column(name = "result")
	private String result;
	
	@Column(name = "certificate_url")
	private String educationCertificateUrl;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
	
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getDegree() {
    	return degree;
    }
    public void setDegree(String degree) {
    	this.degree = degree;
    }
    
    public String getInstitute() {
    	return institute;
    }
    public void setInstitute(String institute) {
    	this.institute = institute;
    }
	
    public String getPassingYear() {
    	return passingYear;
    }
    public void setPassingYear(String passingYear) {
    	this.passingYear = passingYear;
    }
    
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getEducationCertificateUrl() {
		return educationCertificateUrl;
	}
	public void setEducationCertificateUrl(String educationCertificateUrl) {
		this.educationCertificateUrl = educationCertificateUrl;
	}
	
	@JsonBackReference
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee; 
	}
}
