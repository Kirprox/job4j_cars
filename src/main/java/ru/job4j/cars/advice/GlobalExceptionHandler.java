package ru.job4j.cars.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.cars.exception.ActionException;
import ru.job4j.cars.exception.UserActionException;
import ru.job4j.cars.exception.UserLoginException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserLoginException.class)
    public String handleUserLoginException(UserLoginException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "users/login";
    }

    @ExceptionHandler(UserActionException.class)
    public String handleUserActionException(UserActionException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(ActionException.class)
    public String handleActionException(ActionException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(SecurityException.class)
    public String handleSecurityException(SecurityException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/404";
    }

}
