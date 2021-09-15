package com.dbl.nsl.erp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "annual_leave")
public class AnnualLeave {
	
	public AnnualLeave() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "leave_id")
	private Long id;
	
	@Column(name = "total_leave")
	private Long totalLeave;
	
	@Column(name = "previous_total_leave")
	private Long previousTotalLeave;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTotalLeave() {
		return totalLeave;
	}
	public void setTotalLeave(Long totalLeave) {
		this.totalLeave = totalLeave;
	}
	public Long getPreviousTotalLeave() {
		return previousTotalLeave;
	}
	public void setPreviousTotalLeave(Long previousTotalLeave) {
		this.previousTotalLeave = previousTotalLeave;
	}
}
