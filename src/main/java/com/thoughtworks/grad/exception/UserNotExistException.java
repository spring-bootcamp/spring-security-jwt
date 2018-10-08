package com.thoughtworks.grad.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(int id) {
        super(id + " does not exist.");
    }
}
