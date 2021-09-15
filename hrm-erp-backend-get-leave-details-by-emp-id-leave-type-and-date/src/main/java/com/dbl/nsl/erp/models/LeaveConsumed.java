package com.dbl.nsl.erp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "leave_consumed")
public class LeaveConsumed {

	public LeaveConsumed() {
		this.leaveConsumedId = 0L;
		this.sick = 0L;
		this.casual = 0L;
		this.annual = 0L;
		this.maternity = 0L;
		this.other = 0L;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leave_consumed_id")
	private Long leaveConsumedId;

	@Column(name="sick")
	private Long sick;
	
	@Column(name="casual")
	private Long casual;
	
	@Column(name="annual")
	private Long annual;
	
	@Column(name="maternity")
	private Long maternity;
	
	@Column(name="other")
	private Long other;

	public Long getLeaveConsumedId() {
		return leaveConsumedId;
	}

	public void setLeaveConsumedId(Long leaveConsumedId) {
		this.leaveConsumedId = leaveConsumedId;
	}

	public Long getSick() {
		return sick;
	}

	public void setSick(Long sick) {
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
	
	
}
