package com.thoughtworks.grad.exception;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String s) {
        super(s);
    }
}
