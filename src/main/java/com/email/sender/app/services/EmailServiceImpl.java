package com.email.sender.app.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${email.sender}")
    private String sender;

    @Override
    public void sendEmail(String[] toUsers, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage(); // Construyendo un objecto, para configurar el envío de un gmail, solo con texto
        mailMessage.setFrom(sender);
        mailMessage.setTo(toUsers);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage); // Agregamos el objeto, que tiene la estructura del envío
    }

    @Override
    public void sendEmailWithFile(String[] toUsers, String subject, String message, File file) {
        // TODO Auto-generated method stub
    }

}
