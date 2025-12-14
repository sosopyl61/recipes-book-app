package com.rymtsou.recipes_book.exception;

public class LoginExistsException extends Exception {
    String login;

    public LoginExistsException(String login) {
        super("Login already exists: " + login);
    }

    @Override
    public String toString() {
        return "Login already exists: " + login;
    }
}
