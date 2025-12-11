package com.rymtsou.recipes_book.model.request;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private Long id;
    private String username;
    private String email;
}
