package com.dbl.nsl.erp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "holidays")
public class Holiday {
	
	public Holiday() {
		
	}
	public Holiday(String holidayTitle, String holidayStartDate, String holidayEndDate,
			Long holidayDuration, String holidayDescription) {
		this.holidayTitle = holidayTitle;
		this.holidayStartDate = holidayStartDate;
		this.holidayEndDate = holidayEndDate;
		this.holidayDuration = holidayDuration;
		this.holidayDescription = holidayDescription;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "holiday_title")
	private String holidayTitle;
	
	@Column(name = "holiday_start_date")
	private String holidayStartDate;
	
	@Column(name = "holiday_end_date")
	private String holidayEndDate;
	
	@Column(name = "holiday_duration")
	private Long holidayDuration;
	
	@Column(name = "holiday_description")
	private String holidayDescription;
	
	@Column(name = "attachmentUrl")
	private String attachmentUrl;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getHolidayTitle() {
		return holidayTitle;
	}
	
	public void setHolidayTitle(String holidayTitle) {
		this.holidayTitle = holidayTitle;
	}
	
	public String getHolidayStartDate() {
		return holidayStartDate;
	}
	
	public void setHolidayStartDate(String holidayStartDate) {
		this.holidayStartDate = holidayStartDate;
	}
	
	public String getHolidayEndDate() {
		return holidayEndDate;
	}
	
	public void setHolidayEndDate(String holidayEndDate) {
		this.holidayEndDate = holidayEndDate;
	}
	
	public Long getHolidayDuration() {
		return holidayDuration;
	}
	
	public void setHolidayDuration(Long holidayDuration) {
		this.holidayDuration = holidayDuration;
	}
	
	public String getHolidayDescription() {
		return holidayDescription;
	}
	
	public void setHolidayDescription(String holidayDescription) {
		this.holidayDescription = holidayDescription;
	}
	
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	
	
	
}
