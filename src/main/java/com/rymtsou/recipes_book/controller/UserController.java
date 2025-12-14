package com.rymtsou.recipes_book.controller;

import com.rymtsou.recipes_book.model.entity.User;
import com.rymtsou.recipes_book.model.request.UpdateUserRequestDto;
import com.rymtsou.recipes_book.model.response.GetRecipeResponseDto;
import com.rymtsou.recipes_book.model.response.GetUserResponseDto;
import com.rymtsou.recipes_book.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<GetUserResponseDto> getUserByUsername(@PathVariable String username) {
        Optional<GetUserResponseDto> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GetUserResponseDto> updateUser(@Valid @RequestBody UpdateUserRequestDto requestDto) {
        Optional<GetUserResponseDto> responseDto = userService.updateUser(requestDto);
        if (responseDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(responseDto.get(), HttpStatus.OK);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<GetRecipeResponseDto>> getFavoritesRecipes() {
        List<GetRecipeResponseDto> favoriteRecipes = userService.getFavoriteRecipes();
        if (favoriteRecipes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(favoriteRecipes, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) {
        Boolean result = userService.deleteUser(userId);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/favorites/{recipeId}")
    public ResponseEntity<String> toggleFavorite(@PathVariable Long recipeId) {
        boolean isAdded = userService.addRecipeToFavorites(recipeId);
        if (isAdded) {
            return new ResponseEntity<>("Recipe is added to favorites", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipe is deleted from favorites", HttpStatus.OK);
        }
    }
}
