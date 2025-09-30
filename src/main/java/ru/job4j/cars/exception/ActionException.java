package ru.job4j.cars.exception;

public class ActionException extends RuntimeException {
    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
