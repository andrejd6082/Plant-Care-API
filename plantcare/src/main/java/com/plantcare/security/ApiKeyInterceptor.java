package com.plantcare.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${api.key}")
    private String apiKey;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // Browsers send a CORS preflight (OPTIONS) before cross-origin requests
        // and never attach custom headers like X-API-Key to it. Let it through;
        // the real request that follows is still checked.
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String key = request.getHeader("X-API-Key");
        if (key != null && apiKey.equals(key)) {
            return true;
        }

        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized: missing or invalid API key\"}");
        return false;
    }
}
