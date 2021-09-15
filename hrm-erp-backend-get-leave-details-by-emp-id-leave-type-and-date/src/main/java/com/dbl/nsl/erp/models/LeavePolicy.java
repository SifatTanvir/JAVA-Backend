package com.dbl.nsl.erp.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "leaves_policies")
public class LeavePolicy {
	public LeavePolicy() {

	}

	public LeavePolicy(Long sick, Long casual, Long annual, Long maternity, Long other) {
		this.sick = sick;
		this.casual = casual;
		this.annual = annual;
		this.maternity = maternity;
		this.other = other;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "policy_name")
	private String policyName;
	
	@Column(name = "sick")
	private Long sick;

	@Column(name = "casual")
	private Long casual;

	@Column(name = "annual")
	private Long annual;
	
	@Column(name = "maternity")
	private Long maternity;

	@Column(name = "other")
	private Long other;
	
	@JsonIgnoreProperties( {"leavePolicy"} )
	@OneToMany( mappedBy = "leavePolicy", fetch = FetchType.LAZY,
				cascade = CascadeType.ALL)
	private List<Employee> employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public void setSick(Long sick) {
		this.sick = sick;
	}

	public Long getSick() {
		return sick;
	}

	public void setSict(Long sick) {
		this.sick = sick;
	}

	public Long getCasual() {
		return casual;
	}

	public void setCasual(Long casual) {
		this.casual = casual;
	}

	public Long getAnnual() {
		return annual;
	}

	public void setAnnual(Long annual) {
		this.annual = annual;
	}
	
	public Long getMaternity() {
		return maternity;
	}

	public void setMaternity(Long maternity) {
		this.maternity = maternity;
	}

	public Long getOther() {
		return other;
	}

	public void setOther(Long other) {
		this.other = other;
	}
	
	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}
}
