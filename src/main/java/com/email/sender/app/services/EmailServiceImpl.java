package com.email.sender.app.services;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

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
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(toUsers);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            mimeMessageHelper.addAttachment(file.getName(), file);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
