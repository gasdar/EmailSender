package com.email.sender.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.email.sender.app.domains.EmailDto;
import com.email.sender.app.domains.EmailFileDto;
import com.email.sender.app.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDto emailDto) { // Deserialización de objeto JSON a objeto JAVA => jackson.serializable
        emailService.sendEmail(emailDto.getUsers(), emailDto.getSubject(), emailDto.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("status", "Sent");
        body.put("message", "The mail was sent successfully");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PostMapping(value = "/send/file")
    public ResponseEntity<?> sendEmailFile(@ModelAttribute EmailFileDto emailFileDto) {
        MultipartFile requestFile = emailFileDto.file();
        if(requestFile != null) {
            String fileName = requestFile.getOriginalFilename();
            Path path = Paths.get("src/main/resources/files/" + fileName);
            try {
                Files.createDirectories(path.getParent());
                Files.copy(requestFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                File file = path.toFile();
                emailService.sendEmailWithFile(emailFileDto.users(), emailFileDto.subject(), emailFileDto.message(), file);
                Map<String, Object> body = new HashMap<>();
                body.put("status", "Sent");
                body.put("message", "The mail was sent successfully");
                body.put("fileName", fileName);
                return ResponseEntity.status(HttpStatus.OK).body(body);
            } catch (IOException e) {
                ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
