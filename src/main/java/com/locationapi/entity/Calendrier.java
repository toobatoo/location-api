package com.locationapi.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="calendrier")
public class Calendrier {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String day;
	private String month;
	private String year;
	private Date dateTotale;
	private String type;
	private Integer price;
	

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Date getDateTotale() {
		return dateTotale;
	}

	public void setDateTotale(Date dateTotale) {
		this.dateTotale = dateTotale;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
