package com.rymtsou.recipes_book.controller;

import com.rymtsou.recipes_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/favorites/{recipeId}")
    public ResponseEntity<String> addToFavorites(Principal principal,
                                                 @PathVariable Long recipeId) {
        try {
            userService.addToFavorites(principal.getName(), recipeId);
            return ResponseEntity.ok("Recipe added to Favorites!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
