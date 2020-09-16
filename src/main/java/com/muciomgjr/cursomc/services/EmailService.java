package com.muciomgjr.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.muciomgjr.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
}
