package com.example.dbl.tafuri.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "employeePD")
@Entity
public class EmployeePersonalDetails {

		
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(name="age")
    private int age;
    
    @Column(name="Blood_Group")
    private String bloodGroup;
    

	public int getAge() {
		return age;
	}
	
	
	public void setAge(int age) {
		this.age = age;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	

}
