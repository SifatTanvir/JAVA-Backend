package com.example.dbl.tafuri.controller;

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

import com.example.dbl.tafuri.model.EmployeeModel;
import com.example.dbl.tafuri.model.EmployeePersonalDetails;
import com.example.dbl.tafuri.repository.EmployeePersonalDetailsRepository;
import com.example.dbl.tafuri.repository.EmployeeRepository;
import Exception.ResourceNotFoundException;




@RestController
@RequestMapping("/erp")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeePersonalDetailsRepository employeePersonalDetailsRepository;
	
	
    @PostMapping("/employee") 
    public ResponseEntity<EmployeeModel> createOrUpdateEmployee(@RequestBody EmployeeModel employee){
    	    	
        EmployeeModel savedEmployee = employeeRepository.save(employee);
        
        return ResponseEntity.ok().body(savedEmployee);
       
    }
    
    

    @GetMapping("/AllEmployees")
	public List<EmployeeModel> getAllEmployees() {
    	System.out.print("All Employees");
		return employeeRepository.findAll();
	}
	

    
    @GetMapping("/employee/{id}")
	public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable(value = "id") Long employeeId){
		Optional<EmployeeModel> employee = employeeRepository.findById(employeeId);
		
		EmployeeModel emp = null;
		
		if(employee.isPresent())
			emp = employee.get();
		
		return ResponseEntity.ok().body(emp);	
	}
    
    
    @PutMapping("/employee/{id}")
	public ResponseEntity<EmployeeModel> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Validated @RequestBody EmployeeModel employeeDetails) throws ResourceNotFoundException {
		EmployeeModel employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));


		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setEmployeeID(employeeDetails.getEmployeeID());
		final EmployeeModel updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
    
    
    @DeleteMapping("/employee/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		EmployeeModel employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
