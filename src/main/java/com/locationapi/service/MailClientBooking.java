package com.locationapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.locationapi.entity.Voyageur;

@Service
public class MailClientBooking {

	private JavaMailSender mailSender;

	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Autowired
	public MailClientBooking(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	
	public boolean prepareAndSendForBooking(Voyageur formMapper, List<String> listDates, int price) {

		boolean returnResult = false;

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setTo(formMapper.getMail());
			messageHelper.setFrom("sebastien.chappert@hotmail.fr", "Beach's Cosy Home");
			messageHelper.setSubject("Votre demande de réservation studio à Lacanau.");
			String content = this.mailContentBuilder.buildToGuestForBooking(formMapper.getCivilite(), formMapper.getName(), formMapper.getMail(), listDates, price);
			messageHelper.setText(content, true);
		};
		try {
			this.mailSender.send(messagePreparator);
			returnResult = true;
			// Envois du mail au visiteur
			MimeMessagePreparator messagePreparatorToGuest = mimeMessage -> {
				MimeMessageHelper messageHelperToGuest = new MimeMessageHelper(mimeMessage);
				messageHelperToGuest.setFrom("sebastien.chappert@hotmail.fr", "Beach's Cosy Home");
				messageHelperToGuest.setTo( "sebastien.chappert@hotmail.fr" );
				messageHelperToGuest.setSubject("Location studio, demande de réservation!");


				String content = this.mailContentBuilder.buildCopyForBooking( formMapper.getCivilite(), formMapper.getName(), formMapper.getMail(), listDates, price);
				messageHelperToGuest.setText(content, true);
			};
			try {
				this.mailSender.send(messagePreparatorToGuest);
			} catch (MailException e) {
				System.out.println(e.getMessage());
			}

		} catch (MailException e) {
			System.out.println(e.getMessage());
			returnResult = false;
		}
		return returnResult;
	}

}
