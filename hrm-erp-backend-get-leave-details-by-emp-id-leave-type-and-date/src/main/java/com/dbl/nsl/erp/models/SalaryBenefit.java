package com.dbl.nsl.erp.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "salary_benefit")
public class SalaryBenefit {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name = "salary_benefit_id")
	private long id;
	
	@Column(name = "joining_salary")
	private long joiningSalary;
	
	@Column(name = "last_increment_amount")
	private long lastIncrementAmount;
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date date;
	
	@Column(name = "present_salary")
	private long presentSalary;
	
	@Column(name = "promotion_status")
	private boolean promotionStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getJoiningSalary() {
		return joiningSalary;
	}

	public void setJoiningSalary(long joiningSalary) {
		this.joiningSalary = joiningSalary;
	}

	public long getLastIncrementAmount() {
		return lastIncrementAmount;
	}

	public void setLastIncrementAmount(long lastIncrementAmount) {
		this.lastIncrementAmount = lastIncrementAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getPresentSalary() {
		return presentSalary;
	}

	public void setPresentSalary(long presentSalary) {
		this.presentSalary = presentSalary;
	}

	public boolean isPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(boolean promotionStatus) {
		this.promotionStatus = promotionStatus;
	}

}




