package com.rymtsou.recipes_book.service;

import com.rymtsou.recipes_book.model.entity.Recipe;
import com.rymtsou.recipes_book.model.entity.Security;
import com.rymtsou.recipes_book.model.entity.User;
import com.rymtsou.recipes_book.model.request.UpdateUserRequestDto;
import com.rymtsou.recipes_book.model.response.GetRecipeResponseDto;
import com.rymtsou.recipes_book.model.response.GetUserResponseDto;
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
public class UserService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final AuthUtil authUtil;

    public UserService(UserRepository userRepository,
                       RecipeRepository recipeRepository,
                       AuthUtil authUtil) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.authUtil = authUtil;
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<GetUserResponseDto> getUserByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username)
                .map(user -> GetUserResponseDto.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .recipes(user.getRecipes())
                        .build());
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<GetUserResponseDto> updateUser(UpdateUserRequestDto requestDto) {
        if (!authUtil.canAccessUser(requestDto.getId())) {
            throw new AccessDeniedException("You do not have permission to update this user.");
        }

        Optional<User> user = userRepository.findById(requestDto.getId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id " + requestDto.getId());
        }

        if (requestDto.getUsername() != null && !requestDto.getUsername().equals(user.get().getUsername()))
            user.get().setUsername(requestDto.getUsername());
        if (requestDto.getEmail() != null && !requestDto.getEmail().equals(user.get().getEmail()))
            user.get().setEmail(requestDto.getEmail());

        User updatedUser = userRepository.save(user.get());

        return Optional.of(GetUserResponseDto.builder()
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .recipes(updatedUser.getRecipes())
                .build());
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUser(Long userId) {
        if (authUtil.canAccessUser(userId)) {
            userRepository.deleteById(userId);
            return !userRepository.existsById(userId);
        }
        throw new AccessDeniedException("You do not have permission to delete this user.");
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean addRecipeToFavorites(Long recipeId) {
        Security currentSecurity = authUtil.getCurrentSecurity();

        User user = userRepository.findById(currentSecurity.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + currentSecurity.getUserId()));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + recipeId));

        if (user.getFavoriteRecipes().contains(recipe)) {
            user.getFavoriteRecipes().remove(recipe);
            userRepository.save(user);
            return false;
        } else {
            user.getFavoriteRecipes().add(recipe);
            userRepository.save(user);
            return true;
        }
    }

    public List<GetRecipeResponseDto> getFavoriteRecipes() {
        Security currentSecurity = authUtil.getCurrentSecurity();
        User user = userRepository.findById(currentSecurity.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + currentSecurity.getUserId()));
        return user.getFavoriteRecipes().stream()
                .map(recipe -> GetRecipeResponseDto.builder()
                        .title(recipe.getTitle())
                        .instructions(recipe.getInstructions())
                        .author(recipe.getAuthor().getUsername())
                        .created(recipe.getCreated())
                        .updated(recipe.getUpdated())
                        .build())
                .toList();
    }
}
