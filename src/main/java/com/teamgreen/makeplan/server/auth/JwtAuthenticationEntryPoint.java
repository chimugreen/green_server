package com.teamgreen.makeplan.server.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String json = String.format(
                "{\"timestamp\":\"%s\",\"error\":\"Unauthorized\",\"message\":\"%s\"}",
                LocalDateTime.now().toString(),
                authException.getMessage() != null ? authException.getMessage() : "인증이 필요합니다."
        );
        response.getWriter().write(json);
    }
}