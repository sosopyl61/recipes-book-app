package com.rymtsou.recipes_book.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecipeResponseDto {
    private String title;
    private String instructions;
    private String authorName;
}
