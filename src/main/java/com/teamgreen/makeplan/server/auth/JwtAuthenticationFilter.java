package com.teamgreen.makeplan.server.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final TokenStore tokenStore;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if (jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.extractEmail(token);
            // 인증 정보 객체를 생성(email이라는 사용자가 인증된 요청이다)
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

            // 사용자의 요청에 대한 추가 정보(IP, 세션, ID등을 추가하는 과정 으로 보안로그를 남길 때나 세션/요청 기반 감사 기능에 사용 예) 어느 IP에서 접근, 어느
            // 세션으로 접근 이런 메타데이터를 활용해 추후에 로그를 남기거나 할 수 있음 이 메타데이터는 Authentication.getDetails에 저장됨.
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 이 요청의 auth인증정보를 SecurityContextHolder에 등록한다. 이 사용자는 현재 인증된 사용자다라고 알려주는 단계
            SecurityContextHolder.getContext()
                                 .setAuthentication(auth);
        }

        filterChain.doFilter(request, response);

    }
}
