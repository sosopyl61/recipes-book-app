package com.rymtsou.recipes_book.controller;

import com.rymtsou.recipes_book.exception.LoginExistsException;
import com.rymtsou.recipes_book.exception.UsernameExistsException;
import com.rymtsou.recipes_book.model.request.LoginRequestDto;
import com.rymtsou.recipes_book.model.request.RegistrationRequestDto;
import com.rymtsou.recipes_book.model.response.LoginResponseDto;
import com.rymtsou.recipes_book.model.response.RegistrationResponseDto;
import com.rymtsou.recipes_book.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SecurityService securityService;

    @Autowired
    public AuthController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDto> registration(@RequestBody RegistrationRequestDto requestDto)
            throws LoginExistsException, UsernameExistsException {
        Optional<RegistrationResponseDto> registrationUser = securityService.registration(requestDto);
        if (registrationUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(registrationUser.get(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        Optional<String> token = securityService.generateToken(requestDto);
        if (token.isPresent()) {
            return new ResponseEntity<>(new LoginResponseDto(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
