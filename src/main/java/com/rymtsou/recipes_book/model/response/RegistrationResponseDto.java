package com.rymtsou.recipes_book.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponseDto {
    private String username;
    private String email;
}
