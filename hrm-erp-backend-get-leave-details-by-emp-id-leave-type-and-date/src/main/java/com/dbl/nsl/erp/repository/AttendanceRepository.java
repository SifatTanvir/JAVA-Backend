package com.dbl.nsl.erp.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByEmployeeIdAndDateBetween(Long employeeId, Date dateTimeStart, Date dateTimeEnd);
	
	List<Attendance> findByCompanyNameAndDateBetween(String companyName, Date startdate, Date enddate);

	List<Attendance> findAllByDate(Date date);

//	List<Attendance> findByGroupNameAndDate(String groupName, Date date);

	Long countByStatus(String present);

	Long countByStatusAndDateAndCompanyId(String status, Date date, Long companyId);

	@Query( value = "select attendances.employee_id from attendances where group_name = ?1 and date = ?2", nativeQuery = true)
	List<Long> findEmployeeIdByGroupNameAndDate(String groupName, Date date);

	Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, Date date);

	Optional<Attendance> findByEmployeeId(Long employeeId);

	List<Attendance> findByCompanyNameAndDate(String companyName, Date date);

	List<Attendance> findByCompanyIdAndDate(Long companyId, Date date);

	List<Attendance> findByDepartmentNameAndDate(String departmentName, Date date);

	@Query( value = "select count(*) from attendances where attendances.date > ?1 and attendances.date <= ?2 and attendances.status = ?3 and attendances.employee_id = ?4", nativeQuery = true)
	Long countAllPresentDateTillToday(Date confirmationDate, Date currentDate, String status, Long employeeId);

	@Query( value = "select * from attendances where send_edit_request = ?1 ", nativeQuery = true)
	List<Attendance> findAllEditAttendanceRequest(boolean t);

	@Query( value = "select attendances.date from attendances where employee_id = ?1 and status = ?2 and date > ?3 and date < ?4", nativeQuery = true)
	List<Date> findAnnualDatesByEmpIdAndStatusAndDateBetween(Long employeeId, String string, Date startdate, Date enddate);

	@Query( value = "select attendances.date from attendances where employee_id = ?1 and status = ?2 and date > ?3 and date < ?4", nativeQuery = true)
	List<Date> findCasualDatesByEmpIdAndStatusAndDateBetween(Long employeeId, String string, Date startdate,
			Date enddate);

	@Query( value = "select attendances.date from attendances where employee_id = ?1 and status = ?2 and date > ?3 and date < ?4", nativeQuery = true)
	List<Date> findSickDatesByEmpIdAndStatusAndDateBetween(Long employeeId, String string, Date startdate,
			Date enddate);

	
}
