package com.rymtsou.recipes_book.model.request;

import lombok.Data;

@Data
public class UpdateRecipeRequestDto {
    private Long id;
    private String title;
    private String instructions;
}
