package com.dbl.nsl.erp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long>{

	@Query (value = "select designation.designation_name from designation join employee_designation on designation.designation_id = employee_designation.designation_designation_id join employee on employee_designation.employee_employee_id = ?1", nativeQuery = true)
	Set<String> findDesignationByEmployeeId(Long employeeId);

	boolean existsByDesignationName(String designationName);

	@Query(value = "select designation from designation where department_id = ?1", nativeQuery = true)
	List<Designation> findByDepartmentId(Long departmentId);

	@Query(value = "select designation.designation_name from designation where designation_id = ?1", nativeQuery = true)
	String findNameById(long desigId);
}
