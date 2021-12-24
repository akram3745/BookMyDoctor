package com.te.bookmydoctor.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JavaMailService {

	@Value("${mail.email}")
	private String fromAddress;
	@Value("${mail.password}")
	private String password ;

	private void sendMailMultipart(String toEmail, String subject, String message) throws MessagingException {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");

			Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromAddress, password);
				}
			});

			Message msg = new MimeMessage(mailSession);

			msg.setFrom(new InternetAddress(fromAddress));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			msg.setSubject(subject);
			msg.setText(message);
			msg.setSentDate(new Date());
			Transport.send(msg);
	}

	public void sendMail(String toEmail, String subject, String message) throws MessagingException {
		sendMailMultipart(toEmail, subject, message);
	}
}
