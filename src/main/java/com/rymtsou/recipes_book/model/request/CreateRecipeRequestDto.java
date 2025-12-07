package com.rymtsou.recipes_book.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecipeRequestDto {
    private String title;
    private String instructions;
}
