package com.dbl.nsl.erp.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Department {
	
	public Department() {
	}

	public Department(String departmentName, String description) {
		this.departmentName = departmentName;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id")
	private Long id;
	
	@Column(name = "department_name")
	private String departmentName;
	
	@Column(name = "description")
	private String description;
    
	@OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Designation> designation;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Designation> getDesignation() {
		return designation;
	}

	public void setDesignation(List<Designation> designation) {
		this.designation = designation;
	}
	
	
	
	
}
