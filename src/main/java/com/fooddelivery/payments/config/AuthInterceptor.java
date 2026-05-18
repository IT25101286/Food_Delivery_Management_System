package com.fooddelivery.payments.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        if (path.startsWith("/login") || path.startsWith("/logout") || path.startsWith("/css") || path.startsWith("/js")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("/login");
            return false;
        }

        String role = String.valueOf(session.getAttribute("role"));

        if (path.startsWith("/admin") && !"ADMIN".equalsIgnoreCase(role)) {
            response.sendRedirect("/payment/pay");
            return false;
        }

        if (path.startsWith("/payment") && !"CUSTOMER".equalsIgnoreCase(role)) {
            response.sendRedirect("/admin/dashboard");
            return false;
        }

        return true;
    }
}
