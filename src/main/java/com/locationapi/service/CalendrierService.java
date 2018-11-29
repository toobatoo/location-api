package com.locationapi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locationapi.entity.Calendrier;
import com.locationapi.entity.Voyageur;
import com.locationapi.repository.CalendrierRepository;
import com.locationapi.repository.VoyageurRepository;

@Service
public class CalendrierService {

	@Autowired
	private CalendrierRepository calendrierRepository;
	
	@Autowired
	private VoyageurRepository voyageurRepository;

	public List<Calendrier> getDatesBetweenTwo(String dateArrivee, String dateDepart) {
		List<Calendrier> d = (List<Calendrier>) this.calendrierRepository.getDatesBetweenTwo(dateArrivee, dateDepart);
		return d;
	}

	public String getDaysFree(String day, String month, String year) {
		String d = this.calendrierRepository.getDaysFree(day, month, year);
		return d;
	}

	public Collection<Map<String, Object>> getPricesAndMonths() {
		Collection<Map<String, Object>> r = this.calendrierRepository.getPricesAndMonths();
		return r;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean setTemporaryBooking(Voyageur form, ArrayList<Integer> listDatesId) {

		boolean returnValue = false;
		try {
			this.voyageurRepository.saveVoyageurInformation(
					form.getCivilite(), form.getName(), form.getFirstname(), form.getMail(),
					form.getAddress(), form.getCp(), form.getPhone()
			);
			
			int lastInsertIdVoyageur = this.voyageurRepository.getLastInsertId();
			
			for(Integer idDate : listDatesId) {
				this.voyageurRepository.saveDatesTemporary( lastInsertIdVoyageur, idDate);
			}
			returnValue = true;
		}
		catch(Exception e) {
			e.getMessage();
		}
		return returnValue;
	}

	public String getDatesById(Integer dateId) {
		
		return this.calendrierRepository.getDatesById( dateId );
	}
	
	public int getPrice(Integer dateId) {
		return this.calendrierRepository.getPrice(dateId);
	}
}
