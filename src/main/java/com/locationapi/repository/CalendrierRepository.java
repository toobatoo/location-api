package com.locationapi.repository;

import java.util.Collection;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.locationapi.entity.Calendrier;

public interface CalendrierRepository extends CrudRepository<Calendrier, Integer> {

	@Query( value = "SELECT * FROM calendrier WHERE date_totale BETWEEN :arrivee AND :depart ", nativeQuery = true )
	Collection<Calendrier> getDatesBetweenTwo( 
					 @Param("arrivee") String dateArrivee, 
					 @Param("depart") String dateDepart 
			);
	
	@Query(value = "SELECT `type` FROM calendrier WHERE day= :day AND month= :month AND year= :year", nativeQuery = true)
	String getDaysFree( 
			@Param ("day") String day, 
			@Param ("month") String month, 
			@Param ("year" ) String year
			);
	
	@Query(value = "SELECT price, month, year FROM calendrier GROUP BY month", nativeQuery = true)
	Collection< Map<String, Object> > getPricesAndMonths();
	
	@Query( value="SELECT CONCAT(day , '/' , month , '/' , year ) FROM calendrier WHERE id = :id", nativeQuery = true )
	String getDatesById( @Param("id") Integer id );
	
	@Query( value="SELECT price FROM calendrier WHERE id = :id", nativeQuery = true )
	int getPrice( @Param("id") Integer id );
	
}
