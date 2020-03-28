package com.example.gewerbeanmeldung.generator;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	//Sending an email to a destination via smtp
	public static void sendmail(String DEST) {
		 final String username = "server.transport.mail@gmail.com";
	        final String password = "Seilbahn";

	        Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //encrypted data via TLS 
	        
	        //Authenticating in session to log into mail
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

	        //creating a message
	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("server.transport.mail@gmail.com"));
	            message.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse("gethackingyourlife@gmail.com")
	            );
	            
	            message.setSubject("Antrag auf ...");
	            
	            // Create the message part
	            BodyPart messageBodyPart = new MimeBodyPart();
	            
	            // Now set the actual message
	            messageBodyPart.setText("Hi guys its works");
	            
	            // Create a multipar message
	            Multipart multipart = new MimeMultipart();
	            
	         // Set text message part
	            multipart.addBodyPart(messageBodyPart);

	            messageBodyPart = new MimeBodyPart();
	            // add atachment
	            String filename = DEST;
	            DataSource source = new FileDataSource(filename);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(filename);
	            multipart.addBodyPart(messageBodyPart);

	            // Send the complete message parts
	            message.setContent(multipart);  
	            
	            Transport.send(message);

	            System.out.println("sending Mail sucessfull");

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	}
}
