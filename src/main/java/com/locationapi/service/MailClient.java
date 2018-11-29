package com.locationapi.service;

import java.util.ArrayList;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.locationapi.entity.Voyageur;

@Service
public class MailClient {

	private JavaMailSender mailSender;

	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Autowired
	public MailClient(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public boolean prepareAndSendForInformation(String recipient, String nom, String prenom, String mail, String msg) {

		boolean returnResult;

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			// messageHelper.setFrom("tooba@live.fr");
			// messageHelper.setFrom(new InternetAddress("tooba@live.fr", "tooba@live.fr"));
			messageHelper.setTo(recipient);
			messageHelper.setSubject("Location studio via appli perso");

			String content = this.mailContentBuilder.build(nom, prenom, mail, msg);
			messageHelper.setText(content, true);
		};
		try {
			this.mailSender.send(messagePreparator);
			returnResult = true;
			// Envois une copie du mail au visiteur
			MimeMessagePreparator messagePreparatorToGuest = mimeMessage -> {
				MimeMessageHelper messageHelperToGuest = new MimeMessageHelper(mimeMessage);
				messageHelperToGuest.setTo(recipient);
				messageHelperToGuest.setSubject("Copie de votre mail envoyé à Sébastien CHAPPERT");
				messageHelperToGuest.setTo(mail);

				String content = this.mailContentBuilder.buildToGuest(msg);
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
