package com.rymtsou.recipes_book.controller;

import com.rymtsou.recipes_book.model.request.CreateRecipeRequestDto;
import com.rymtsou.recipes_book.model.response.CreateRecipeResponseDto;
import com.rymtsou.recipes_book.model.response.GetRecipeResponseDto;
import com.rymtsou.recipes_book.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<CreateRecipeResponseDto> addRecipe(@RequestBody CreateRecipeRequestDto requestDto) {
        Optional<CreateRecipeResponseDto> createdRecipe = recipeService.createRecipe(requestDto);
        if (createdRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(createdRecipe.get(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetRecipeResponseDto> getRecipeById(@PathVariable Long id) {
        Optional<GetRecipeResponseDto> recipe = recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
    }

    @GetMapping
    public List<CreateRecipeResponseDto> getAll() {
        return recipeService.getAllRecipes();
    }
}
