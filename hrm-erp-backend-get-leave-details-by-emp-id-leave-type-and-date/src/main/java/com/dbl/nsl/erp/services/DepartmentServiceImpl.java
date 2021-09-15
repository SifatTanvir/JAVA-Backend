package com.dbl.nsl.erp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.payload.response.DepartmentResponse;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.DepartmentRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public ResponseEntity<?> addCompanyLocation(Long companyId, Department departmentDetails) 
			throws ResourceNotFoundException {

		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		company.getDepartment().add(departmentDetails);
		companyRepository.save(company);
		return ResponseEntity.ok().body(company);
	}

	@Override
	public ResponseEntity<?> getAllDepartment() throws ResourceNotFoundException {

		List<Department> allDepartments = departmentRepository.findAll();
		if( allDepartments.isEmpty() ) throw new ResourceNotFoundException("Department Not Found");
		return ResponseEntity.ok().body(allDepartments);
	}

	@Override
	public ResponseEntity<?> updatedDepartment(Long companyId, Long departmentId, Department departmentDetails) 
			throws ResourceNotFoundException {

		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		department.setDepartmentName(departmentDetails.getDepartmentName());
		department.setDescription(departmentDetails.getDescription());

		final Department updatedDepartment = departmentRepository.save(department);
		return ResponseEntity.ok(updatedDepartment);

	}

	@Override
	public ResponseEntity<?> deleteDepartment(Long companyId, Long departmentId) throws ResourceNotFoundException {

		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
		departmentRepository.delete(department);
		return ResponseEntity.ok().body("Department Deleted Successfully");

	}

	@Override
	public ResponseEntity<?> getDepartment(Long companyId, Long departmentId) throws ResourceNotFoundException {

		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
		return ResponseEntity.ok().body(department);

	}

	@Override
	public ResponseEntity<?> getAllDepartment(Long companyId) throws ResourceNotFoundException {

		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		List<Department> department = company.getDepartment();
		if( department.isEmpty() ) throw new ResourceNotFoundException("Department not found");

		return ResponseEntity.ok().body(department);

	}

	@Override
	public ResponseEntity<?> getAllDepartmentInformation() throws ResourceNotFoundException {

		List<Department> departments = departmentRepository.findAll();

		List<DepartmentResponse> departmentResponseList = new ArrayList<DepartmentResponse>();

		for (Department department : departments) {
			DepartmentResponse departmentResponse = new DepartmentResponse();
			departmentResponse.setDepartmentName(department.getDepartmentName());
			Set<Long> queryEmployee = employeeRepository.findByDepartmenId(department.getId());
			long totalEmployee = queryEmployee.size();
			departmentResponse.setTotalEmployee(totalEmployee);
			// departmentResponse.setDepartmentLeadName(department.getDepartmentLeadName());

			departmentResponseList.add(departmentResponse);	
		}
		if( departmentResponseList.isEmpty() ) throw new ResourceNotFoundException("Department not found");
		return ResponseEntity.ok().body(departmentResponseList);
		
	}

	@Override
	public ResponseEntity<?> getAllEmployee(Long departmentId) throws ResourceNotFoundException {
		
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		Set<Long> empId = employeeRepository.findEmployeeBasedOnDept(departmentId);

		List<Employee> employees = new ArrayList<>();
		for( Long id: empId) {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
			employees.add(employee);
		}

		if( employees.isEmpty() ) throw new ResourceNotFoundException("Employee not found");
		return ResponseEntity.ok().body(employees);
	}
	
	
	@Override
	public ResponseEntity<?> getAllEmployeeByCompanyIdAndDepartmentId(Long companyId, Long departmentId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Comapany not found"));
		
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		Set<Long> empId = employeeRepository.findEmployeeBasedOnDept(departmentId);

		List<Employee> employees = new ArrayList<>();
		for( Long id: empId) {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
			employees.add(employee);
		}

		return ResponseEntity.ok().body(employees);
	}

}
