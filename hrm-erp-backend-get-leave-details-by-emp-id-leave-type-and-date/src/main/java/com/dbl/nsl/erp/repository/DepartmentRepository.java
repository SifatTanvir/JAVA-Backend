package com.dbl.nsl.erp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	@Query( value = "select department.department_name from department join employee_department on department.department_id = employee_department.department_department_id join employee on employee_department.employee_employee_id = ?1", nativeQuery = true)
	Set<String> findDepartmentByEmployeeId(Long employeeId);

	boolean existsByDepartmentName(String departmentName);

	@Query( value = "select department from department where department_id = ?1 and company_id = ?2", nativeQuery = true)
	Company findByCompanyIdAndDepartmentId(Long companyId, Long departmentId);

	// List<Department> findByCompanyId(Long companyId);

	@Query( value = "select department.department_name from department where department_id = ?1", nativeQuery = true)
	String findNameById(long deptId);

//	List<CompanyDepartment> findAllByCompanyIdAndDepartmentId();
	
	
}
