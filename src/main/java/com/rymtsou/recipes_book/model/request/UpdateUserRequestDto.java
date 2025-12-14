package com.rymtsou.recipes_book.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private Long id;

    @Size(min = 3, max = 30, message = "Username must be from 3 to 30 symbols!")
    private String username;

    @Email(message = "Invalid email format!")
    private String email;
}
