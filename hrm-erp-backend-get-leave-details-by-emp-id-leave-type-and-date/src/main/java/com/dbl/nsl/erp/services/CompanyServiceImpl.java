package com.dbl.nsl.erp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.Location;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.payload.response.ShortEmployeeInfo;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.DepartmentRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.EmployeeRepository;


@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DesignationRepository designationRepository;

	@Override
	public ResponseEntity<?> addCompany(Company company) {

		if (companyRepository.existsByName(company.getName())) 
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Company already exists"));

		final Company savedCompany = companyRepository.save(company);
		return ResponseEntity.ok().body(savedCompany);
	}

	@Override
	public ResponseEntity<?> getCompany(Long companyId) throws ResourceNotFoundException {

		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));
		return ResponseEntity.ok().body(company);
	}

	@Override
	public ResponseEntity<?> getAllCompany() throws ResourceNotFoundException {

		List<Company> allCompany = companyRepository.findAll();
		if( allCompany.isEmpty() ) throw new ResourceNotFoundException("Company Not Found");
		return ResponseEntity.ok().body(allCompany);
	}

	@Override
	public ResponseEntity<?> getAllDepartment() throws ResourceNotFoundException {
		
		List<Company> allCompany = companyRepository.findAll();
		List<Pair< Company, Department>> comp_dept = new ArrayList<>();
		for(Company comp: allCompany) {
			List< Department > allDepartment = comp.getDepartment();
			for( Department dept: allDepartment ) 
				comp_dept.add(new Pair<Company, Department>(comp, dept));
		}
		if( comp_dept.isEmpty() ) throw new ResourceNotFoundException("Departments not found");
		return ResponseEntity.ok().body(comp_dept);	
		
	}

	@Override
	public ResponseEntity<?> getAllLocation() throws ResourceNotFoundException {
		
		List< Company > allCompany = companyRepository.findAll();
		List< Pair< Company, Location> > comp_loc = new ArrayList<Pair<Company, Location>>();
		for( Company comp: allCompany ) {
			List< Location > allLocation = comp.getLocation();
			for( Location loc: allLocation ) 
				comp_loc.add(new Pair<Company, Location> ( comp, loc) );
		}
		if( comp_loc.isEmpty() ) throw new ResourceNotFoundException("Location not found");
		return ResponseEntity.ok().body(comp_loc);
		
	}

	@Override
	public ResponseEntity<?> getAllDesignation() throws ResourceNotFoundException {
		
		List<Company> allCompany = companyRepository.findAll();
		List<Triplet< Company, Department, Designation>> comp_dept_des = new ArrayList<>();
		for( Company comp: allCompany ) {
			List< Department> allDepartment = departmentRepository.findAll();
			for( Department dept: allDepartment ) {
				List< Designation > allDesignation = designationRepository.findAll();
				for( Designation des:allDesignation ) 
					comp_dept_des.add( new Triplet< Company, Department, Designation> ( comp, dept, des));
			}		
		}
		
		if( comp_dept_des.isEmpty() ) throw new ResourceNotFoundException("Designation not found");
		return ResponseEntity.ok().body(comp_dept_des);	
		
	}

	@Override
	public ResponseEntity<?> updateCompany(Long companyId, Company companyDetails) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		company.setName(companyDetails.getName());
		company.setDescription(companyDetails.getDescription());
		final Company updatedCompany = companyRepository.save(company);
		return ResponseEntity.ok(updatedCompany);
	}

	@Override
	public ResponseEntity<?> deleteCompany(Long companyId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		companyRepository.delete(company);
		return ResponseEntity.ok().body("Company Deleted Successfully");
	}

	@Override
	public ResponseEntity<?> getAllEmployeeBasedOnCompany(Long companyId) throws ResourceNotFoundException {
		
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Comapany not found"));

		Set<Long> empId = employeeRepository.findEmployeeBasedOnCompany(companyId);
		List<ShortEmployeeInfo> employees = new ArrayList<>();
		
		for( Long id: empId) {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

			ShortEmployeeInfo shortEmployee = new ShortEmployeeInfo();
			shortEmployee.setEmployeeId(employee.getId());
			shortEmployee.setFirstName(employee.getFirstName());
			shortEmployee.setLastName(employee.getLastName());

			employees.add(shortEmployee);
		}

		// if( employees.isEmpty() ) return ResponseEntity.ok().body(new MessageResponse("Employee not found"));
		return ResponseEntity.ok().body(employees);
	}

	@Override
	public ResponseEntity<?> countEmployeeBasedOnDepartment(Long companyId) throws ResourceNotFoundException {

		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Comapany not found"));

		List< Pair<String,Long> > DeptName_totalEmploye = new ArrayList<>();

		List< Department > allDepartment = company.getDepartment();

		for( Department department : allDepartment ) {
			java.util.Set<Long> ids = employeeRepository.findEmployeeBasedOnDept(department.getId());
			DeptName_totalEmploye.add( new Pair<String, Long> ( department.getDepartmentName(), (long) ids.size() ) );
		}

		if( DeptName_totalEmploye.isEmpty() ) throw new ResourceNotFoundException("No department found");
		return ResponseEntity.ok().body(DeptName_totalEmploye);
	}
	
	

}
