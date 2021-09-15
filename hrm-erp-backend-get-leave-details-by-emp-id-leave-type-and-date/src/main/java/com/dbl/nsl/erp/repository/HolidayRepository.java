package com.dbl.nsl.erp.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbl.nsl.erp.models.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
	@Query( value =  "select holidays.* from holidays where ?1 >= holiday_start_date and ?2 <= holiday_end_date", nativeQuery = true)
	Holiday findByDate(Date date, Date date2);
	
}
