package com.rymtsou.recipes_book.security;

import com.rymtsou.recipes_book.model.entity.Security;
import com.rymtsou.recipes_book.repository.SecurityRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public final SecurityRepository securityRepository;

    public CustomUserDetailsService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Security> securityOptional = securityRepository.findByLogin(username);
        if (securityOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        Security security = securityOptional.get();

        return User
                .withUsername(security.getLogin())
                .password(security.getPassword())
                .build();
    }
}
