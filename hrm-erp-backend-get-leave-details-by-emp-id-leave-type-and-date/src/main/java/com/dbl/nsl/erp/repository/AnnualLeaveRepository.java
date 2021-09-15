package com.dbl.nsl.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.AnnualLeave;

@Repository
public interface AnnualLeaveRepository extends JpaRepository<AnnualLeave, Long>{

}
