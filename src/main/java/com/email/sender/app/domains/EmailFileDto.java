package com.email.sender.app.domains;

import org.springframework.web.multipart.MultipartFile;

public record EmailFileDto(
    String[] users,
    String subject,
    String message,
    MultipartFile file
) {

}
