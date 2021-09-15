package com.dbl.nsl.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.AttendanceRoaster;

@Repository
public interface AttendanceRoasterRepository extends JpaRepository<AttendanceRoaster, Long> {

//	@Query( value = "select * from attendance_roaster where id in ( select employees.attendance_roaster_id from employees where employee_id = ?1", nativeQuery = true)
//	@Query( value = "select attendance_roaster from attendance_roaster inner join employees on attendance_roaster.id = employees.attendance_roaster_id where employees.employee_id = ?1", nativeQuery = true)
	@Query( value = "select attendance_roaster.id from attendance_roaster inner join employee on attendance_roaster.id = employee.attendance_roaster_id where employee.employee_id = ?1", nativeQuery = true)
	Long findByEmployeeId(Long employeeId);

	@Query( value = "select attendance_roaster.late_time from attendance_roaster join employee on attendance_roaster.id = employee.attendance_roaster_id where employee.employee_id = ?1", nativeQuery = true)
	String findInTimeByEmployeeId(Long id);
	

}
