package net.javaguides.springboot.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import controller.ResourceNotFoundException;
import net.javaguides.springboot.entity.Education;
import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.repository.EducationRepository;
import net.javaguides.springboot.repository.EmployeeRepository;






@RestController
@RequestMapping("/erp")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EducationRepository educationRepository;
	
	
    @PostMapping("/employee") 
    public ResponseEntity<Employee> createOrUpdateEmployee(@RequestBody Employee employee){
    	    	
        Employee savedEmployee = employeeRepository.save(employee);
        
        return ResponseEntity.ok().body(savedEmployee);
       
    }
    
    

    @GetMapping("/AllEmployees")
	public List<Employee> getAllEmployees() {
    	System.out.print("All Employees");
		return employeeRepository.findAll();
	}
    
    @GetMapping("/AllEducation")
	public List<Education> getEducationDetails() {
    	System.out.print("All Employees");
		return educationRepository.findAll();
	}
	

    
    @GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId){
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		
		Employee emp = null;
		
		if(employee.isPresent())
			emp = employee.get();
		
		return ResponseEntity.ok().body(emp);	
	}
    
    
    @PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));


		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setId(employeeDetails.getId());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
    
    
    @DeleteMapping("/employee/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
