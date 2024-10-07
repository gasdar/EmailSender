package com.email.sender.app.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private String[] users;
    private String subject;
    private String message;

}
