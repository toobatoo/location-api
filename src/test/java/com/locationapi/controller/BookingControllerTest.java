package com.locationapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookingControllerTest {
	
	BookingController bookingController;
	
	final Map<String, String> date_param = new HashMap<>();
	

	@BeforeEach
	void setUp() throws Exception {
		this.date_param.put("date_arrivee", "2019-05-02");
		this.date_param.put("date_depart", "2019-05-05");
	}

	@Test
	void testGetInfosDates() {
		
		//System.out.println(this.date_param.get("date_arrivee"));
		List<Map> list_bookings = new ArrayList<Map>();
		//given( this.bookingController.getInfosDates( this.date_param ) ).willReturn(list_bookings);
		
		
	}

}
