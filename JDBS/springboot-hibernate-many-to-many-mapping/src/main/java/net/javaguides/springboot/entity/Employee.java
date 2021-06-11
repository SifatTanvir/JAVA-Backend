package net.javaguides.springboot.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table( name = "employee")
public class Employee {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tags")
	private Set<Project> projects = new HashSet<>();
	
	public Employee() {
		
	}
	
	public Employee(String name) {
		super();
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long employee_id) {
		this.id = employee_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Project> getProjects() {
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
}
