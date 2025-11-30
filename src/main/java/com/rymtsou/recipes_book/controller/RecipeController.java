package com.rymtsou.recipes_book.controller;

import com.rymtsou.recipes_book.dto.CreateRecipeDto;
import com.rymtsou.recipes_book.dto.response.RecipeResponseDto;
import com.rymtsou.recipes_book.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<String> addRecipe(@RequestBody CreateRecipeDto recipeDto,
                                            Principal principal) {
        try {
            recipeService.createRecipe(recipeDto, principal.getName());
            return ResponseEntity.ok("Recipe created successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<RecipeResponseDto> getAll() {
        return recipeService.getAllRecipes();
    }
}
