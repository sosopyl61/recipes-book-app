package com.rymtsou.recipes_book.model.request;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String login;
    private String password;
}
