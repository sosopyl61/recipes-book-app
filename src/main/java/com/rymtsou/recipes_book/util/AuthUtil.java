package com.rymtsou.recipes_book.util;

import com.rymtsou.recipes_book.model.entity.Recipe;
import com.rymtsou.recipes_book.model.entity.Role;
import com.rymtsou.recipes_book.model.entity.Security;
import com.rymtsou.recipes_book.repository.RecipeRepository;
import com.rymtsou.recipes_book.repository.SecurityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final SecurityRepository securityRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public AuthUtil(SecurityRepository securityRepository, RecipeRepository recipeRepository) {
        this.securityRepository = securityRepository;
        this.recipeRepository = recipeRepository;
    }

    public Security getCurrentSecurity() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return securityRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Security not found with login: " + login));
    }

    public Boolean canAccessUser(Long userId) {
        Security currentSecurity = getCurrentSecurity();
        return currentSecurity.getRole().equals(Role.ADMIN) ||
                currentSecurity.getUserId().equals(userId);
    }

    public Boolean canAccessRecipe(Long recipeId) {
        Security currentSecurity = getCurrentSecurity();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + recipeId));

        return currentSecurity.getRole().equals(Role.ADMIN) ||
                currentSecurity.getUserId().equals(recipe.getAuthor().getId());
    }
}
