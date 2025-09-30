package ru.job4j.cars.exception;

public class UserActionException extends RuntimeException {
    public UserActionException(String message) {
        super(message);
    }

    public UserActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
