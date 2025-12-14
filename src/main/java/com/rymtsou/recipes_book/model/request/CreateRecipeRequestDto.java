package com.rymtsou.recipes_book.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateRecipeRequestDto {

    @NotBlank(message = "Recipe's title cannot be blank!")
    @Size(max = 100, message = "Recipe's title cannot be more than 100 symbols!")
    private String title;

    @NotBlank(message = "Instructions field cannot be blank!")
    private String instructions;
}
