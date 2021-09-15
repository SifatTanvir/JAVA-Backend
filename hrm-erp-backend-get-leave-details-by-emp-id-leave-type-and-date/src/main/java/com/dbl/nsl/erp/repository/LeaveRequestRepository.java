package com.dbl.nsl.erp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

	//	@Query("SELECT * FROM leave_request ORDER BY leaveRequest.sending_date DESC")
	//	List<LeaveRequest> findAllOrderBySendingDateDesc();
	
	@Query( value = "select leave_requests.* from leave_requests where leave_requests.sender_id = ?1 and leave_requests.is_accepted = ?2", nativeQuery = true)
	List<LeaveRequest> findBySenderIdAndisAccepted(Long senderId, int isAccepted);

	@Query( value = "select leave_requests.* from leave_requests where leave_requests.sender_id = ?1 and leave_requests.is_accepted = ?2", nativeQuery = true)
	LeaveRequest findByEmpIdAndTypeAndDate(Long employeeId, String type, Date date);


}
