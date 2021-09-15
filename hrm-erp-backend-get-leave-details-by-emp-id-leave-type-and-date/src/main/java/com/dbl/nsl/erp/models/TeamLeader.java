package com.dbl.nsl.erp.models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TeamLeader {
	
	@Id
	private Long id;
	
	@ElementCollection
	private List<Long> employeeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(List<Long> employeeId) {
		this.employeeId = employeeId;
	}
	
	

}
