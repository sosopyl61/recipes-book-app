package com.rymtsou.recipes_book.model.response;

import com.rymtsou.recipes_book.model.entity.Recipe;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetUserResponseDto {

    private String username;
    private String email;
    private List<Recipe> recipes;
}
