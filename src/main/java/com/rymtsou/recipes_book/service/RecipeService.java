package com.rymtsou.recipes_book.service;

import com.rymtsou.recipes_book.model.entity.Security;
import com.rymtsou.recipes_book.model.request.CreateRecipeRequestDto;
import com.rymtsou.recipes_book.model.response.CreateRecipeResponseDto;
import com.rymtsou.recipes_book.model.entity.Recipe;
import com.rymtsou.recipes_book.model.entity.User;
import com.rymtsou.recipes_book.model.response.GetRecipeResponseDto;
import com.rymtsou.recipes_book.repository.RecipeRepository;
import com.rymtsou.recipes_book.repository.UserRepository;
import com.rymtsou.recipes_book.util.AuthUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, AuthUtil authUtil) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.authUtil = authUtil;
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<CreateRecipeResponseDto> createRecipe(CreateRecipeRequestDto requestDto) {
        Security security = authUtil.getCurrentSecurity();
        User user = userRepository.findById(security.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + security.getUserId()));

        Recipe recipe = Recipe.builder()
                .title(requestDto.getTitle())
                .instructions(requestDto.getInstructions())
                .author(user)
                .build();

        Recipe createdRecipe = recipeRepository.save(recipe);

        return Optional.of(CreateRecipeResponseDto.builder()
                .title(createdRecipe.getTitle())
                .instructions(createdRecipe.getInstructions())
                .authorName(createdRecipe.getAuthor().getUsername())
                .build());
    }

    public Optional<GetRecipeResponseDto> getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(recipe -> GetRecipeResponseDto.builder()
                        .title(recipe.getTitle())
                        .instructions(recipe.getInstructions())
                        .author(recipe.getAuthor().getUsername())
                        .created(recipe.getCreated())
                        .updated(recipe.getUpdated())
                        .build());
    }

    public List<CreateRecipeResponseDto> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipe -> new CreateRecipeResponseDto(
                        recipe.getTitle(),
                        recipe.getInstructions(),
                        recipe.getAuthor().getUsername()
                ))
                .toList();
    }
}
