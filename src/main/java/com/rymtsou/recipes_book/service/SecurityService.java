package com.rymtsou.recipes_book.service;

import com.rymtsou.recipes_book.model.request.LoginRequestDto;
import com.rymtsou.recipes_book.model.request.RegistrationRequestDto;
import com.rymtsou.recipes_book.model.entity.Security;
import com.rymtsou.recipes_book.model.entity.User;
import com.rymtsou.recipes_book.model.response.RegistrationResponseDto;
import com.rymtsou.recipes_book.repository.SecurityRepository;
import com.rymtsou.recipes_book.repository.UserRepository;
import com.rymtsou.recipes_book.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public SecurityService(UserRepository userRepository, PasswordEncoder passwordEncoder, SecurityRepository securityRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityRepository = securityRepository;
        this.jwtUtil = jwtUtil;
    }

    public Optional<RegistrationResponseDto> registration(RegistrationRequestDto requestDto) {
        if (securityRepository.existsByLogin(requestDto.getLogin())) {
            return Optional.empty(); //EXCEPTION
        }

        if (userRepository.existsByUsername(requestDto.getUsername())) {
            return Optional.empty(); //EXCEPTION
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .build();

        User registratedUser = userRepository.save(user);

        Security security = Security.builder()
                .login(requestDto.getLogin())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .userId(registratedUser.getId())
                .build();
        securityRepository.save(security);

        RegistrationResponseDto responseDto = RegistrationResponseDto.builder()
                .username(registratedUser.getUsername())
                .email(registratedUser.getEmail())
                .build();

        return Optional.of(responseDto);
    }

    public Optional<String> generateToken(LoginRequestDto requestDto) {
        Optional<Security> securityOptional = securityRepository.findByLogin(requestDto.getLogin());
        if (securityOptional.isPresent() && passwordEncoder.matches(requestDto.getPassword(), securityOptional.get().getPassword())) {
            return jwtUtil.generateJwtToken(requestDto.getLogin());
        }
        return Optional.empty();
    }
}
