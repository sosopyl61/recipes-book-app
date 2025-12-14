package com.rymtsou.recipes_book.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDto {

    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 3, max = 30, message = "Username must be from 3 to 30 symbols!")
    private String username;

    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "Login cannot be blank!")
    @Size(min = 3, max = 30, message = "Login must be from 3 to 30 symbols!")
    private String login;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 8, message = "Password must contain minimum 8 symbols!")
    private String password;
}
