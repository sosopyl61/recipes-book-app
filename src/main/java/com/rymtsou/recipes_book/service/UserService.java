package com.rymtsou.recipes_book.service;

import com.rymtsou.recipes_book.model.entity.Recipe;
import com.rymtsou.recipes_book.model.entity.User;
import com.rymtsou.recipes_book.repository.RecipeRepository;
import com.rymtsou.recipes_book.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public UserService(UserRepository userRepository,
                       RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    public void addToFavorites(String username, Long recipeId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username +" not found!"));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found!"));

        user.getFavoriteRecipes().add(recipe);

        userRepository.save(user);
    }
}
