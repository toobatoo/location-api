package com.locationapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

	private TemplateEngine templateEngine;

	@Autowired
	public MailContentBuilder(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String build(String nom, String prenom, String mail, String msg) {
		Context context = new Context();
		context.setVariable("nom", nom);
		context.setVariable("prenom", prenom);
		context.setVariable("mail", mail);
		context.setVariable("msg", msg);
		return templateEngine.process("mail_contact", context);
	}
	
	public String buildToGuest(String msg) {
		Context context = new Context();
		context.setVariable("msg", msg);
		return templateEngine.process("mail_contact_copy", context);
	}
	
	public String buildCopyForBooking(String civilite, String nom, String mail, List<String> listDates, int price) {
		Context context = new Context();
		context.setVariable("civilite", civilite);
		context.setVariable("nom", nom);
		context.setVariable("mail", mail);
		context.setVariable("listDates", listDates);
		context.setVariable("price", price);
		return templateEngine.process("mail_information_copy", context);
	}
	
	public String buildToGuestForBooking(String civilite, String nom, String mail, List<String> listDates, int price) {

		Context context = new Context();
		context.setVariable("civilite", civilite);
		context.setVariable("nom", nom);
		context.setVariable("mail", mail);
		context.setVariable("listDates", listDates);
		context.setVariable("price", price);
		return templateEngine.process("mail_information", context);
	}
}
