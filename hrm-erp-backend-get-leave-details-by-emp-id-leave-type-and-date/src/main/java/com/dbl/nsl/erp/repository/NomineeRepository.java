package com.dbl.nsl.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.Nominee;

@Repository
public interface NomineeRepository extends JpaRepository<Nominee, Long> {
	
	@Query( value = "delete from nominee where nominee.employee_employee_id = ?1", nativeQuery = true)
	void deleteByEmployeeId(Long employeeId);

}
