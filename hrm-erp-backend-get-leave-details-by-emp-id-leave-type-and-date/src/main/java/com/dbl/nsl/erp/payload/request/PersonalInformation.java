package com.dbl.nsl.erp.payload.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PersonalInformation {

	private String fatherName;
	private String motherName;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date birthDate;
	private String gender;
	private String nationality;
	private String religion;
	private String bloodGroup;
	private String maritalStatus;
	private String employmentStatus;
	private Long nidNumber;
	private String permanentAddress;
	private String presentAddress;
	private String personalEmail;
	
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender( String gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}	
	public void setNationality( String nationality) {
		this.nationality = nationality;
	}	
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	
	public Long getNidNumber() {
		return nidNumber;
	}
	
	public void setNidNumber(Long nidNumber) {
		this.nidNumber = nidNumber;
	}
	
	public String getPermanentAddress() {
		return permanentAddress;
	}
	
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	
	public String getPresentAddress() {
		return presentAddress;
	}
	
	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}
	
	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
}
