package com.locationapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.locationapi.entity.Calendrier;
import com.locationapi.repository.CalendrierRepository;

class CalendrierServiceTest {

	@InjectMocks
	CalendrierService calendrierService;
	
	@Mock
	CalendrierRepository calendrierRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetDatesBetweenTwo() {
		Calendrier calendrierA = new Calendrier();
		calendrierA.setDay("02");
		calendrierA.setMonth("05");
		calendrierA.setYear("2019");
		calendrierA.setDateTotale(new Date(02-05-2019));
		calendrierA.setType("ON");
		calendrierA.setPrice(50);
		
		Calendrier calendrierB = new Calendrier();
		calendrierB.setDay("03");
		calendrierB.setMonth("05");
		calendrierB.setYear("2019");
		calendrierB.setDateTotale(new Date(03-05-2019));
		calendrierB.setType("ON");
		calendrierB.setPrice(50);
		
		List<Calendrier> collCalendars = new ArrayList<Calendrier>();
		collCalendars.add(calendrierA);
		collCalendars.add(calendrierB);
		
		when( calendrierRepository.getDatesBetweenTwo( anyString(),anyString() ) ).thenReturn(collCalendars);
	
		List<Calendrier> result = calendrierService.getDatesBetweenTwo("02/05/2019", "03/05/2019");
		assertNotNull(result);
		assertEquals("02", result.get(0).getDay());
		assertEquals("03", result.get(1).getDay());
	}
	

}
