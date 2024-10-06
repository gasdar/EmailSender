package com.email.sender.app.services;

import java.io.File;

public interface EmailService {

    void sendEmail(String[] toUsers, String subject, String message);
    void sendEmailWithFile(String[] toUsers, String subject, String message, File file);

}
