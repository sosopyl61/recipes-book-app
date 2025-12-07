package com.rymtsou.recipes_book.util;

import com.rymtsou.recipes_book.model.entity.Security;
import com.rymtsou.recipes_book.repository.SecurityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final SecurityRepository securityRepository;

    @Autowired
    public AuthUtil(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public Security getCurrentSecurity() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return securityRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Security not found with login: " + login));
    }
}
