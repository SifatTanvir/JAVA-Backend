package com.dbl.nsl.erp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	boolean existsByName(String name);

	@Query( value = "select company.name from company join employee_company on company.company_id = employee_company.company_company_id join employee on employee_company.employee_employee_id = ?1", nativeQuery = true)
	Set<String> findNameByEmployeeId(Long id);

	@Query( value = "select company.company_id from company join employee_company on company.company_id = employee_company.company_company_id join employee on employee_company.employee_employee_id = ?1", nativeQuery = true)
	Set<Long> findCompanyIdByEmployeeId(Long employeeId);
	
	@Query( value = "select company.name from company where company_id = ?1", nativeQuery = true)
	String findCompanyNameByCompanyId(Long companyId);

	@Query(value = "select company.company_id from company", nativeQuery = true)
	List<Long> findCompanyId();
	
	@Query( value = "select company.name from company where company_id = ?1", nativeQuery = true)
	String findNameById(long cmpId);

}
