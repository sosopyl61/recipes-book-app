package com.rymtsou.recipes_book.repository;

import com.rymtsou.recipes_book.model.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Security, Long> {
    boolean existsByLogin(String login);
    Optional<Security> findByLogin(String login);
}
