package com.locationapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locationapi.service.MailClient;

@CrossOrigin(origins = "*")
@RequestMapping("/api/mail")
@RestController
public class MailControlleur {
	
	@Autowired
	private MailClient mailer;
	
	@PostMapping("/send-mail-information")
	public boolean sendMailForInformation( @RequestBody Map<String, String> params ) {
		
		String recipient = "sebastien.chappert@hotmail.fr";
		String nom = params.get( "nom" );
		String prenom = params.get( "prenom" );
		String mail = params.get( "mail" );
		String msg = params.get( "msg" );
		
		return this.mailer.prepareAndSendForInformation(recipient, nom, prenom, mail, msg);
	}

}
