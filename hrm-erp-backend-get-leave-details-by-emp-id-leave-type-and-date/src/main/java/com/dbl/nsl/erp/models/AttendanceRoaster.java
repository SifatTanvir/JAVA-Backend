package com.dbl.nsl.erp.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class AttendanceRoaster {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private long id;
	private String officeStartTime;
	private String officeEndTime;
	private String shiftName;
	private String lateTime;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> weekends;
	
	@JsonIgnoreProperties( {"attendanceRoaster"} )
	@OneToMany( mappedBy = "attendanceRoaster", fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL )
	private List<Employee> employee;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOfficeStartTime() {
		return officeStartTime;
	}

	public void setOfficeStartTime(String officeStartTime) {
		this.officeStartTime = officeStartTime;
	}

	public String getOfficeEndTime() {
		return officeEndTime;
	}

	public void setOfficeEndTime(String officeEndTime) {
		this.officeEndTime = officeEndTime;
	}
	
	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	
	public String getLateTime() {
		return lateTime;
	}

	public void setLateTime(String lateTime) {
		this.lateTime = lateTime;
	}

	public List<String> getWeekends() {
		return weekends;
	}

	public void setWeekends(List<String> weekends) {
		this.weekends = weekends;
	}

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "AttendanceRoaster [id=" + id + ", officeStartTime=" + officeStartTime + ", officeEndTime="
				+ officeEndTime + "]";
	}

	
	
	
}
