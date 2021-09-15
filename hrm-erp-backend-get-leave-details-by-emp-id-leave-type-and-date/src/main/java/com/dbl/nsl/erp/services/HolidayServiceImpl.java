package com.dbl.nsl.erp.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.FileUploadUtil;
import com.dbl.nsl.erp.models.Holiday;
import com.dbl.nsl.erp.models.HolidayList;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.HolidayListRepository;
import com.dbl.nsl.erp.repository.HolidayRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class HolidayServiceImpl implements HolidayService {
	
	
	@Autowired
	private HolidayRepository holidayRepository;
	
	@Autowired
	private HolidayListRepository holidayListRepository;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	
	void savedHoliday(String start_date, String end_date) throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(start_date);
		Date endDate = formatter.parse(end_date);

		Date currentDate = startDate;
		while (currentDate.equals(endDate) || currentDate.before(endDate)) {

			HolidayList day = new HolidayList();
			day.setDate(currentDate);
			holidayListRepository.save(day);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 1);
			currentDate = calendar.getTime();
		}	
	}
	

	@Override
	public ResponseEntity<?> addHoliday( String holidayJson, MultipartFile file ) throws ParseException {
		
		Holiday holiday = new Holiday();
		try {
			holiday = objectMapper.readValue(holidayJson, Holiday.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to process the data"));
		}
		
	    // saving the attachment in DB
	    String fileName = null;
		try {
			 String uploadDir = "src/main/resources/static/file/";
			 fileName = "file/" + FileUploadUtil.saveFile( 1602047L, uploadDir, file );  ///  1602047 is random full fill the saveFile() requirement
		} catch( Exception e ) {
			
		}
		    
		holiday.setAttachmentUrl(fileName);
		final Holiday savedHoliday = holidayRepository.save(holiday);
		    
		// save the each holiday into holiday list
		savedHoliday(savedHoliday.getHolidayStartDate(), savedHoliday.getHolidayEndDate());
		
		return ResponseEntity.ok(new MessageResponse("Holiday registered successfully"));
		
	}

	@Override
	public ResponseEntity<?> updateHoliday( String holidayJson, MultipartFile file, Long holidayId) throws ResourceNotFoundException, ParseException {
		
		Holiday holidayDetails = new Holiday();
		try {
			holidayDetails = objectMapper.readValue(holidayJson, Holiday.class);
		} catch (JsonMappingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to parse the data"));

		} catch (JsonProcessingException e1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to process the data"));
		}
		
		
		Holiday holiday = holidayRepository.findById(holidayId)
				.orElseThrow(() -> new ResourceNotFoundException("Holiday not found"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date oldStartDate = formatter.parse(holiday.getHolidayStartDate());  // previous value
		Date oldEndDate = formatter.parse(holiday.getHolidayEndDate());
		
		Date newStartDate = formatter.parse(holidayDetails.getHolidayStartDate());  // new value
		Date newEndDate = formatter.parse(holidayDetails.getHolidayStartDate());
		
		// If new start date and end date are not matched with previous then delete the previous saved holiday and save the new holidays into holiday list
		if( oldStartDate != newStartDate || oldEndDate != newEndDate ) {
			// erase all the previous holiday date from holiday list
			Date currentDate = oldStartDate;
			while (currentDate.equals(oldEndDate) || currentDate.before(oldEndDate)) {
				holidayListRepository.deleteByDate(currentDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DATE, 1);
				currentDate = calendar.getTime();
			}
			
			// save the each new holiday into holiday list
		    savedHoliday(holidayDetails.getHolidayStartDate(), holidayDetails.getHolidayEndDate());
		}
		
		holiday.setHolidayTitle(holidayDetails.getHolidayTitle());
		holiday.setHolidayStartDate(holidayDetails.getHolidayStartDate());
		holiday.setHolidayEndDate(holidayDetails.getHolidayEndDate());
		holiday.setHolidayDuration(holidayDetails.getHolidayDuration());
		holiday.setHolidayDescription(holidayDetails.getHolidayDescription());

	    // saving the attachment in DB
	    String fileName = null;
		try {
			 String uploadDir = "src/main/resources/static/file/";
			 fileName = "file/" + FileUploadUtil.saveFile( 1602047L, uploadDir, file );  ///  1602047 is random full fill the saveFile() requirement
		} catch( Exception e ) {
			
		}
		    
		holiday.setAttachmentUrl(fileName);
		final Holiday updatedHoliday = holidayRepository.save(holiday);
		return ResponseEntity.ok(updatedHoliday);
	}

	@Override
	public ResponseEntity<?> deleteHoliday(Long holidayId) throws ResourceNotFoundException, ParseException {
		
		Holiday holiday = holidayRepository.findById(holidayId)
				.orElseThrow(() -> new ResourceNotFoundException("Holiday not found"));
		
		// holiday removed from holiday list 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(holiday.getHolidayStartDate());
		Date endDate = formatter.parse(holiday.getHolidayEndDate());

		Date currentDate = startDate;
		while (currentDate.equals(endDate) || currentDate.before(endDate)) {
			holidayListRepository.deleteByDate(currentDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, 1);
			currentDate = calendar.getTime();
		}	
		
		holidayRepository.delete(holiday);
		return ResponseEntity.ok().body("Holiday Deleted Successfully");
	}

	@Override
	public ResponseEntity<?> getHolidayById(Long holidayId) throws ResourceNotFoundException {
		
		Holiday holiday = holidayRepository.findById(holidayId)
				.orElseThrow(() -> new ResourceNotFoundException("Holiday not found"));
		return ResponseEntity.ok().body(holiday);
	}

	@Override
	public ResponseEntity<?> getAllHoliday() throws ResourceNotFoundException {
		
		List<Holiday> holidays = new ArrayList<Holiday>();
		holidays = this.holidayRepository.findAll();
		if (holidays.isEmpty()) throw new ResourceNotFoundException("Holiday not found");
		
		return ResponseEntity.ok().body(holidays);
	}
	
	
	
	
	
}
