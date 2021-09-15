package com.dbl.nsl.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.TeamLeader;

@Repository
public interface TeamLeaderRepository extends JpaRepository< TeamLeader, Long >{

}
