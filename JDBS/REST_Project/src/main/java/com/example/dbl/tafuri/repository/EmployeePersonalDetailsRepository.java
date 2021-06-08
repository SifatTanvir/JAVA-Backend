package com.example.dbl.tafuri.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dbl.tafuri.model.EmployeeModel;
import com.example.dbl.tafuri.model.EmployeePersonalDetails;

@Repository
public interface EmployeePersonalDetailsRepository extends JpaRepository<EmployeePersonalDetails, Long> {
	

}
