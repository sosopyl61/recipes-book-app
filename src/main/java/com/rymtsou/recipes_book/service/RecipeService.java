package com.rymtsou.recipes_book.service;

import com.rymtsou.recipes_book.model.entity.Security;
import com.rymtsou.recipes_book.model.request.CreateRecipeRequestDto;
import com.rymtsou.recipes_book.model.request.UpdateRecipeRequestDto;
import com.rymtsou.recipes_book.model.entity.Recipe;
import com.rymtsou.recipes_book.model.entity.User;
import com.rymtsou.recipes_book.model.response.GetRecipeResponseDto;
import com.rymtsou.recipes_book.repository.RecipeRepository;
import com.rymtsou.recipes_book.repository.UserRepository;
import com.rymtsou.recipes_book.util.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
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
    public Optional<GetRecipeResponseDto> createRecipe(CreateRecipeRequestDto requestDto) {
        Security security = authUtil.getCurrentSecurity();
        User user = userRepository.findById(security.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + security.getUserId()));

        Recipe recipe = Recipe.builder()
                .title(requestDto.getTitle())
                .instructions(requestDto.getInstructions())
                .author(user)
                .build();

        Recipe createdRecipe = recipeRepository.save(recipe);

        return Optional.of(GetRecipeResponseDto.builder()
                .title(createdRecipe.getTitle())
                .instructions(createdRecipe.getInstructions())
                .author(createdRecipe.getAuthor().getUsername())
                .created(createdRecipe.getCreated())
                .updated(createdRecipe.getUpdated())
                .build());
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<GetRecipeResponseDto> updateRecipe(UpdateRecipeRequestDto requestDto) {
        if (!authUtil.canAccessRecipe(requestDto.getId())) {
            throw new AccessDeniedException("You do not have permission to update this recipe.");
        }

        Optional<Recipe> recipe = recipeRepository.findById(requestDto.getId());

        if (recipe.isEmpty()) {
            throw new EntityNotFoundException("Recipe not found with id " + requestDto.getId());
        }

        if (requestDto.getTitle() != null && !requestDto.getTitle().equals(recipe.get().getTitle()))
            recipe.get().setTitle(requestDto.getTitle());
        if (requestDto.getInstructions() != null && !requestDto.getInstructions().equals(recipe.get().getInstructions()))
            recipe.get().setInstructions(requestDto.getInstructions());

        Recipe updatedRecipe = recipeRepository.save(recipe.get());

        return Optional.of(GetRecipeResponseDto.builder()
                .title(updatedRecipe.getTitle())
                .instructions(updatedRecipe.getInstructions())
                .author(updatedRecipe.getAuthor().getUsername())
                .created(updatedRecipe.getCreated())
                .updated(updatedRecipe.getUpdated())
                .build());
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRecipe(Long recipeId) {
        if (authUtil.canAccessRecipe(recipeId)) {
            recipeRepository.deleteById(recipeId);
            return !recipeRepository.existsById(recipeId);
        }
        throw new AccessDeniedException("You do not have permission to delete this recipe.");
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

    public List<GetRecipeResponseDto> getRecipeByTitle(String title) {
        return recipeRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(recipe -> GetRecipeResponseDto.builder()
                        .title(recipe.getTitle())
                        .instructions(recipe.getInstructions())
                        .author(recipe.getAuthor().getUsername())
                        .created(recipe.getCreated())
                        .updated(recipe.getUpdated())
                        .build())
                .toList();
    }

    public List<GetRecipeResponseDto> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipe -> GetRecipeResponseDto.builder()
                        .title(recipe.getTitle())
                        .instructions(recipe.getInstructions())
                        .author(recipe.getAuthor().getUsername())
                        .created(recipe.getCreated())
                        .updated(recipe.getUpdated())
                        .build()
                ).toList();
    }
}
