package ru.job4j.cars.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void whenLoginUserReturnSuccessLogin() {
        var user = new User();
        user.setId(1L);
        user.setLogin("login");
        user.setPassword("password");

        var request = mock(HttpServletRequest.class);
        var session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(userService.findByLoginAndPassword(user.getLogin(), user.getPassword()))
                .thenReturn(Optional.of(user).get());
        var view = userController.loginUser(user, request);

        assertThat(view).isEqualTo("redirect:/index");
        verify(session).setAttribute("user", user);
    }

    @Test
    void whenRegisterThenRegisterSuccess() {
        var user = new User();
        user.setId(1L);
        user.setLogin("login");
        user.setPassword("password");

        when(userService.create(user)).thenReturn(user);
        var view = userController.saveUser(user);

        assertThat(view).isEqualTo("redirect:/users/login");
    }

}