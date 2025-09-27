package ru.job4j.cars.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.job4j.cars.model.User;

import java.io.IOException;

@Component
@Order(1)
public class AuthorizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var uri = request.getRequestURI();
        if (!isLoginRequired(uri)) {
            chain.doFilter(request, response);
            return;
        }
        var user = (User) request.getSession().getAttribute("user");
        var userLoggedIn = user != null && !"Гость".equals(user.getLogin());

        if (!userLoggedIn) {
            request.getSession().setAttribute("redirect_uri", uri);
            var redirectUrl = request.getContextPath() + "/users/login";
            response.sendRedirect(redirectUrl);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isLoginRequired(String uri) {
        return uri.startsWith("/posts/create");
    }
}
