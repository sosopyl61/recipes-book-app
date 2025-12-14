package com.rymtsou.recipes_book.exception;

public class UsernameExistsException extends Exception {
    String username;

    public UsernameExistsException(String username) {
        super("Username already exists: " + username);
    }

    @Override
    public String toString() {
        return "Username already exists: " + username;
    }
}
