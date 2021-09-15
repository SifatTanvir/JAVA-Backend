package com.dbl.nsl.erp.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {

	List<Employee> findByEmail(String email);
	List<Employee> findByActiveTrue();
	//	Long countByGroupName(String groupName);
	//	Set<Employee> findDistinctByGroupName(String groupName);

	@Query( value = "select employee.employee_id from employee join employee_department on employee.employee_id = employee_department.employee_employee_id join department on employee_department.department_department_id = ?1", nativeQuery = true)
	Set<Long> findByDepartmenId(Long departmentId);

	@Query( value = "select employee.employee_id from employee join employee_designation on employee.employee_id = employee_designation.employee_employee_id join designation on employee_designation.designation_designation_id = ?1", nativeQuery = true)
	Set<Long> findByDesignationId(Long designationId);

	//  @Query(value = "select employees.employee_id from employees where group_name = ?1", nativeQuery = true)
	//	List<Long> findByGroupName(String groupName);

	@Query( value = "select employee.first_name from employee where employee_id = ?1", nativeQuery = true)
	String findFirstNameById(Long employeeId);

	@Query( value = "select employee.last_name from employee where employee_id = ?1", nativeQuery = true)
	String findLastNameById(Long employeeId);

	@Query( value = "select employee.employee_id from employee", nativeQuery = true)
	List<Long> findEmployeeId();

	//  @Query( value = "select employees.employee_id from employees join employees_groups on employees.employee_id = employees_groups.group_id join groups on employees_groups.group_id = ?1", nativeQuery = true )
	//	Set<Long> totalEmployeeByGroupId(Long groupName);

	Optional<Employee> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	boolean existsByUsernameAndEmail(String username, String email);

	List<Employee> findByIdGreaterThan(Long id);

	// Return employee based on department
	@Query( value =  "select employee.employee_id from employee join employee_department on employee.employee_id = employee_department.employee_employee_id join department on employee_department.department_department_id = ?1", nativeQuery = true)
	Set<Long> findEmployeeBasedOnDept(Long deptId);

	// Return employee based on Company
	@Query( value =  "select employee.employee_id from employee join employee_company on employee.employee_id = employee_company.employee_employee_id join company on employee_company.company_company_id = ?1", nativeQuery = true)
	Set<Long> findEmployeeBasedOnCompany(Long companyId);

	@Query( value =  "select employee.employee_id from employee where employee_id > ?1", nativeQuery = true)
	List<Long> findEmployeeIdsGreaterThanOne(Long id);

	@Query( value =  "select employee.employee_id from employee where employee.status= ?1", nativeQuery = true)
	List<Long> findPermanentEmployeeByStatus(String string);

	@Query( value =  "select employee.status from employee where employee_id = ?1", nativeQuery = true)
	String findEmployeementStatus(Long id);

	//	@Query( value =  "select employee.employee_id from employee where employee.is_line_manager = ?1", nativeQuery = true)
	//	List<Long> findByIsLineManagerTrue(Boolean status);
	//	
	//	@Query( value =  "select employee.employee_id from employee where employee.is_team_leader = ?1", nativeQuery = true)
	//	List<Long> findByIsTeamLeaderTrue(boolean status);
	//	
	//	@Query( value =  "select employee.employee_id from employee where employee.is_head_of_dept = ?1", nativeQuery = true)
	//	List<Long> findByIsHoDTrue(boolean status);

//	@Query( value =  "select employee.* from employee where employee.is_line_manager = ?1", nativeQuery = true)
//	List<Employee> findAllLineManager(boolean status);
//
//	@Query( value =  "select employee.* from employee where employee.is_team_leader = ?1", nativeQuery = true)
//	List<Employee> findAllTeamLeader(boolean status);
//
//	@Query( value =  "select employee.* from employee where employee.is_head_of_dept = ?1", nativeQuery = true)
//	List<Employee> findAllHoD(boolean status);

	@Query( value =  "select employee.* from employee where employee.employee_id > ?1", nativeQuery = true)
	List<Employee> findAllEmployeeIdGreaterThanOne(Long superAdminId);
	
	@Query( value =  "select employee.* from employee where employee.employee_id > ?1 and employee.active = ?2", nativeQuery = true)
	List<Employee> findAllEmployeeIdGreaterThanOneAndActiveStatusTrue(Long superAdminId, Boolean status);
	
	@Query( value =  "select employee.employee_id from employee where employee.employee_id > ?1 and employee.active = ?2", nativeQuery = true)
	List<Long> findEmployeeIdsGreaterThanOneAndActiveTrue(Long id, Boolean status);
	
	@Query( value =  "select employee.active from employee where employee.username = ?1", nativeQuery = true)
	Boolean findEmployeeStatusByUserName(String username);
	
	@Query( value =  "select employee.* from employee where employee.active = ?1 and employee.is_line_manager = ?2", nativeQuery = true)
	List<Employee> findAllActiveLineManager(boolean b, boolean c);
	
	@Query( value =  "select employee.* from employee where employee.active = ?1 and employee.is_team_leader = ?2", nativeQuery = true)
	List<Employee> findAllActiveTeamLeader(boolean b, boolean c);
	
	@Query( value =  "select employee.* from employee where employee.active = ?1 and employee.is_head_of_dept = ?2", nativeQuery = true)
	List<Employee> findAllActiveHoD(boolean b, boolean c);
	
	@Query( value =  "select employee.username from employee where employee_id > ?1 and employee.active = ?2 and employee.role = ?3", nativeQuery = true)
	String findAdminEmailAddressGreaterThanOneAndActiveTrue(long l, boolean b, String string);


}
