package com.rymtsou.recipes_book.controller;

import com.rymtsou.recipes_book.model.request.CreateRecipeRequestDto;
import com.rymtsou.recipes_book.model.request.UpdateRecipeRequestDto;
import com.rymtsou.recipes_book.model.response.GetRecipeResponseDto;
import com.rymtsou.recipes_book.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<GetRecipeResponseDto> addRecipe(@Valid @RequestBody CreateRecipeRequestDto requestDto) {
        Optional<GetRecipeResponseDto> createdRecipe = recipeService.createRecipe(requestDto);
        if (createdRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(createdRecipe.get(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<GetRecipeResponseDto> updateRecipe(@Valid @RequestBody UpdateRecipeRequestDto requestDto) {
        Optional<GetRecipeResponseDto> updatedRecipe = recipeService.updateRecipe(requestDto);
        if (updatedRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updatedRecipe.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable Long id) {
        Boolean result = recipeService.deleteRecipe(id);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find")
    public ResponseEntity<Page<GetRecipeResponseDto>> searchRecipeByTitle(
            @RequestParam String title,
            @PageableDefault(size = 10, sort = "created") Pageable pageable
    ) {
        Page<GetRecipeResponseDto> recipes = recipeService.getRecipeByTitle(title, pageable);
        if (recipes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
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
    public ResponseEntity<Page<GetRecipeResponseDto>> getAll(
            @PageableDefault(size = 10,sort = "created", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetRecipeResponseDto> recipes = recipeService.getAllRecipes(pageable);
        if (recipes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
}
