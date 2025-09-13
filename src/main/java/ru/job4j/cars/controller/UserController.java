package ru.job4j.cars.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "/users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpServletRequest request) {
        User resultUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword()).get();
        var session = request.getSession();
        session.setAttribute("user", resultUser);
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:/index"; //todo поменять на login
    }

}
