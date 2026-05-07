package com.fooddelivery.tracking.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.startsWith("/login") || uri.startsWith("/track") || uri.startsWith("/logout")) {
            return true;
        }
        if (request.getSession().getAttribute("adminEmail") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
