package com.rymtsou.recipes_book.repository;

import com.rymtsou.recipes_book.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
