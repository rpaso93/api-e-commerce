package uces.edu.ar.shoppingCart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import uces.edu.ar.shoppingCart.model.dto.UserRecoveryDTO;
import uces.edu.ar.shoppingCart.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
		 
	    @Autowired
	    private JavaMailSender emailSender;
	 
	    @Override
	    public void sendRecoveryMessage(UserRecoveryDTO recovery) {
	        SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setFrom("shoppingCart@test.com");
	        message.setTo(recovery.getEmail()); 
	        message.setSubject(recovery.getSubject()); 
	        message.setText(recovery.getMessage());
	        emailSender.send(message);
	    }
	    
}

