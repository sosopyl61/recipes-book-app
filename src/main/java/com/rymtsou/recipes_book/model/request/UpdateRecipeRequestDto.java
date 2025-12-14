package com.rymtsou.recipes_book.model.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateRecipeRequestDto {
    private Long id;

    @Size(max = 100, message = "Recipe's title cannot be more than 100 symbols!")
    private String title;
    private String instructions;
}
