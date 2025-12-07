package com.rymtsou.recipes_book.model.request;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String username;
    private String email;
    private String login;
    private String password;
}
