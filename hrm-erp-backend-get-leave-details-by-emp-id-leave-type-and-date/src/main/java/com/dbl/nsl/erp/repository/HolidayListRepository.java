package com.dbl.nsl.erp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Holiday;
import com.dbl.nsl.erp.models.HolidayList;

public interface HolidayListRepository extends JpaRepository<HolidayList, Long> {
	
	Boolean existsByDate(Date date);

	void deleteByDate(Date currentDate);

	@Query( value =  "select holiday_list.date from holiday_list where date >= ?1 and date <= ?2", nativeQuery = true)
	List<Date> findAllDateByEmpIdAndDateRange(Date startdate, Date enddate);
}
