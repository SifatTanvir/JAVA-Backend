package com.example.dbl.tafuri.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "employee_model2")
@Entity
public class EmployeeModel {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;
    
    @Column(name="employee_id")
    private long employeeID;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeePersonalDetails")
    private EmployeePersonalDetails employeePersonalDetails;
       
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public long getEmployeeID() {
		return employeeID;
	}
	
	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}
	
	public EmployeePersonalDetails getEmployeePersonalDetails() {
		return employeePersonalDetails;
	}
	
	public void setEmployeePersonalDetails(EmployeePersonalDetails employeePersonalDetails) {
		this.employeePersonalDetails = employeePersonalDetails;
	}

    @Override
    public String toString() {
        return "EmployeeEntity [id=" + id + ", firstName=" + firstName + 
                ", lastName=" + lastName + ", Employee ID=" + employeeID + ", employeePersonalDetails=" + employeePersonalDetails + "]";
    }

}
