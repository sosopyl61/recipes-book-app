package com.rymtsou.recipes_book.service;

import com.rymtsou.recipes_book.dto.CreateRecipeDto;
import com.rymtsou.recipes_book.dto.response.RecipeResponseDto;
import com.rymtsou.recipes_book.entity.Recipe;
import com.rymtsou.recipes_book.entity.User;
import com.rymtsou.recipes_book.repository.RecipeRepository;
import com.rymtsou.recipes_book.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Recipe createRecipe(CreateRecipeDto recipeDto, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setInstructions(recipeDto.getInstructions());
        recipe.setAuthor(author);

        return recipeRepository.save(recipe);
    }

    public List<RecipeResponseDto> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipe -> new RecipeResponseDto(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getInstructions(),
                        recipe.getAuthor().getUsername() // Вот тут Hibernate сходит в базу за автором
                ))
                .toList();
    }
}
