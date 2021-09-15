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
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.payload.response.DesignationResponse;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.DepartmentRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;

@Service
public class DesignationServiceImpl implements DesignationService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	DesignationRepository designationRepository;

	@Override
	public ResponseEntity<?> addCompanyLocation(Long companyId, Long departmentId, Designation designationDetails) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		department.getDesignation().add(designationDetails);
		departmentRepository.save(department);
		return ResponseEntity.ok().body(company);
	}

	@Override
	public ResponseEntity<?> updatedDesignation(Long companyId, Long departmentId, Long designationId,
			Designation designationDetails) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		Designation designation = designationRepository.findById(designationId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		designation.setDesignationName(designationDetails.getDesignationName());
		designation.setDescription(designationDetails.getDescription());
		designation.setGrade(designationDetails.getGrade());

		final Designation updatedDesignation = designationRepository.save(designation);
		return ResponseEntity.ok(updatedDesignation);
	}

	@Override
	public ResponseEntity<?> deleteDesignation(Long companyId, Long departmentId, Long designationId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
		departmentRepository.delete(department);

		Designation designation = designationRepository.findById(designationId)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

		designationRepository.delete(designation);
		return ResponseEntity.ok().body("Designation deleted successfully");
	}

	@Override
	public ResponseEntity<?> getDepartment(Long companyId, Long departmentId, Long designationId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		Designation designation = designationRepository.findById(designationId)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

		return ResponseEntity.ok().body(designation);
	}

	@Override
	public ResponseEntity<?> getAllDesignation(Long companyId, Long departmentId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
		
		List<Designation> designations = department.getDesignation();
		if( designations.isEmpty() ) throw new ResourceNotFoundException("Designation not found");

		return ResponseEntity.ok().body(designations);
	}

	@Override
	public ResponseEntity<?> getAllDesignationInformation() {
		
		List<Designation> designations = designationRepository.findAll();

		List<DesignationResponse> designationResponseList = new ArrayList<DesignationResponse>();

		for (Designation designation : designations) {
			DesignationResponse designationResponse = new DesignationResponse();
			designationResponse.setDesignationName(designation.getDesignationName());
			Set<Long> queryEmployee = employeeRepository.findByDesignationId(designation.getId());
			long totalEmployee = queryEmployee.size();
			designationResponse.setTotalEmployee(totalEmployee);

			designationResponseList.add(designationResponse);
		}
		return ResponseEntity.ok().body(designationResponseList);
	}
	
	

}
