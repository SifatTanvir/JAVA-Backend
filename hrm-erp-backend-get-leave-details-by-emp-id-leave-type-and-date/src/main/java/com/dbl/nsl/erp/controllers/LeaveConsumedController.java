package com.dbl.nsl.erp.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.AnnualLeave;
import com.dbl.nsl.erp.models.LeaveConsumed;
import com.dbl.nsl.erp.repository.AnnualLeaveRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.repository.LeaveConsumedRepository;
import com.dbl.nsl.erp.services.LeaveConsumedService;

@RestController
public class LeaveConsumedController {

	@Autowired
	LeaveConsumedService leaveConsumedService;
	
	@Autowired
	LeaveConsumedRepository leaveConsumedRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	AnnualLeaveRepository annualLeaveRepository;
	
	@GetMapping("/employee/{id}/leave_consumed")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> getLeaveInfo(@PathVariable(value = "id") Long employeeId ) 
			throws ResourceNotFoundException{

		return leaveConsumedService.getLeaveInfo(employeeId);
	
	}
	
	@GetMapping("/leave_report/employee/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or hasRole('USER') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> leaveReport(@PathVariable(value = "id") Long employeeId,
			@RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
			@RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) throws ResourceNotFoundException {

		return leaveConsumedService.leaveReport(employeeId, startdate, enddate);
	}
	
	
	@GetMapping("/leave_report/employee/{id}/type/{type}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ASSISTANT_ADMIN') or hasRole('USER') or @securityService.hasEntry(#employeeId)")
	public ResponseEntity<?> leaveReportByEmpIdAndDate(@PathVariable(value = "id") Long employeeId,
			@PathVariable(value = "type") String type,
			@RequestParam("Date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws ResourceNotFoundException {

		return leaveConsumedService.leaveReportByEmpIdAndDate(employeeId, type, date);
	}
	
	
//  This scheduler sets all consumed leaves to zero at the begining of the year. Also save the 
//  previous year leave to annual leave table and current year annual leave to zero.
//	@Scheduled(cron = "0/50 * * * * ?")
//	@Scheduled(cron = "0 0 3 31 12 ?") // every 31 December at 3 am
	@Scheduled(cron = "0 0 3 11 1 ?") // every 5 January at 3 am
	public void setZeroLeaveConsume() {
		
		System.out.println("----------- set all consumed leaves to zero---------------");

		//find all consumed leaves
		List<LeaveConsumed> leavedConsumeds = leaveConsumedRepository.findAll();
		
		//find all annual leaves
		List<AnnualLeave> allAnnualLeaves = annualLeaveRepository.findAll();
		
		//set each annual leave to zero
		for(AnnualLeave annualLeave : allAnnualLeaves) {
			Long count = annualLeave.getTotalLeave();
			annualLeave.setPreviousTotalLeave(count);
			annualLeave.setTotalLeave(0L);
			annualLeaveRepository.save(annualLeave);
		}
		
		//set each leaveConsumed to zero
		for(LeaveConsumed leaveConsumed : leavedConsumeds) {
			leaveConsumed.setAnnual(0l);
			leaveConsumed.setCasual(0l);
			leaveConsumed.setSick(0l);
			leaveConsumed.setMaternity(0l);
			leaveConsumed.setOther(0l);
			
			leaveConsumedRepository.save(leaveConsumed);
		}	
	}
}