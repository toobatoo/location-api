package com.locationapi.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.locationapi.entity.Voyageur;

public interface VoyageurRepository extends CrudRepository<Voyageur, Integer> {
	
	@Transactional(rollbackFor = Exception.class)
	@Modifying
	@Query(value = "INSERT INTO voyageur_information ( civilite, name, firstname, mail, address, zip_code, phone)"
			+ " VALUES ( :civilite, :name, :firstname, :mail, :address, :zip_code, :phone)"
	, nativeQuery = true)
    public void saveVoyageurInformation(
    		@Param ("civilite") String civilite,
    		@Param ("name") String name,
    		@Param ("firstname") String firstname,
    		@Param ("mail") String mail,
    		@Param ("address") String address,
    		@Param ("zip_code") String zip_code,
    		@Param ("phone") String phone);
	
	@Transactional(rollbackFor = Exception.class)
	@Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
	public int getLastInsertId();
	
	@Transactional(rollbackFor = Exception.class)
	@Modifying
	@Query( value = "INSERT INTO temporary_booking (id_voyageur_information, calendrier_id)"
			+ " VALUES (:id_voyageur, :id_date)" , nativeQuery = true)
	public void saveDatesTemporary(
			@Param ("id_voyageur") Integer id_voyageur,
			@Param ("id_date") Integer id_date);
	
}
