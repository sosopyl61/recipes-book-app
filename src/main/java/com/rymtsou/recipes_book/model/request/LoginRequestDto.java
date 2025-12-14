package com.rymtsou.recipes_book.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Login field cannot be blank!")
    private String login;

    @NotBlank(message = "Password field cannot be blank!")
    private String password;
}
