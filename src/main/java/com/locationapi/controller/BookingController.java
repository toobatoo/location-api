package com.locationapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationapi.entity.Calendrier;
import com.locationapi.entity.Voyageur;
import com.locationapi.service.CalendrierService;
import com.locationapi.service.MailClient;
import com.locationapi.service.MailClientBooking;

@CrossOrigin(origins = "*")
@RequestMapping("/api/booking")
@RestController
public class BookingController {
	
	private static Logger logger = LogManager.getLogger(BookingController.class);
	
	@Autowired
	private CalendrierService calendrierService;
	
	@Autowired
	private MailClient mailer;
	
	@Autowired
	private MailClientBooking mailerBooking;

	@PostMapping("/get-infos-dates")
    public List<Map> getInfosDates(@RequestBody Map<String, String> date_param) {
		
        String date_arrivee = date_param.get("date_arrivee");
        String date_depart = date_param.get("date_depart");
        
		List<Calendrier> days = (List<Calendrier>)this.calendrierService.getDatesBetweenTwo(date_arrivee, date_depart);
		
		List<Map> list_bookings = new ArrayList<Map>();
		
		for (int i = 0; i < days.size(); i++) {
			
			Map<String, Object> booking = new HashMap<String, Object>();
			
			booking.put("id", days.get(i).getId());
			booking.put("date", days.get(i).getDay()+'/'+days.get(i).getMonth()+'/'+days.get(i).getYear());
			booking.put("price", days.get(i).getPrice().toString());
			booking.put("type", days.get(i).getType());
			
			list_bookings.add( booking );
		}
		
		return list_bookings;
    }

	@RequestMapping(value = "/get-days-free", method = RequestMethod.POST,
			   consumes = "application/json")
	public ArrayList<String> getDaysFree(@RequestBody Map<String, Object> listDates) {
		
		ArrayList<String> dates = (ArrayList<String>) listDates.get("list_days");
		ArrayList<String> list_listDaysReturn = new ArrayList<String>();
		
		for(int i=0; i<dates.size(); i++) {
			String dayReturn = this.calendrierService.getDaysFree(dates.get(i), listDates.get("month").toString(), listDates.get("year").toString());
			
			list_listDaysReturn.add( dayReturn );
		}
		return list_listDaysReturn;
    }

	@RequestMapping(value = "/get-prices-&-months", method = RequestMethod.POST)
	Collection< Map<String, Object> > getPricesAndMonths(){
		return this.calendrierService.getPricesAndMonths();
	}
	
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public boolean setTemporaryBooking(@RequestParam String form, @RequestParam ArrayList<Integer> dates) throws JsonParseException, JsonMappingException, IOException {
		
		boolean returnValue = false;
		
		//Add and convert form identity to type Identity
		ObjectMapper mapper = new ObjectMapper();
		Voyageur formMapper;
		
			formMapper = mapper.readValue(form, Voyageur.class);
			try {
				returnValue = this.calendrierService.setTemporaryBooking( formMapper, dates );
				this.sendMailForBooking( formMapper, dates );
			}
			catch(Exception e) {
				e.getMessage();
			}
		
		return returnValue;
	}
 
	private boolean sendMailForBooking( Voyageur formMapper, ArrayList<Integer> dates ) {
		
		List<String> listDates = new ArrayList<String>();
		int price = 0;
		
		for(int i=0; i<dates.size(); i++) {
			listDates.add( this.calendrierService.getDatesById( dates.get(i) ) );
			price += this.calendrierService.getPrice( dates.get(i) );
		}
		
		
		boolean returnValue = this.mailerBooking.prepareAndSendForBooking(formMapper,listDates, price );
		
		return returnValue;
	}
}