package com.email.sender.app.configs;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Value(value = "${email.sender}")
    private String sender;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com"); // Host donde se realizará la comunicación, para enviar un email
        mailSender.setPort(587);
        mailSender.setUsername(sender);
        mailSender.setPassword("randomusern01"); // Para producción, hay que agregar mayor seguridad

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true"); // por medio del protocolo smtp, el usuario se autentica con las credenciales, y si son incorrectas, no se puede realizar el proceso
        props.put("mail.smtp.starttls", "true"); // La comunicación por smtp, en todo momento esta cifrado
        props.put("mail.debug", "true"); // Mostrar mensajes

        return mailSender;
    }

}
