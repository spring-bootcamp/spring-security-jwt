package com.thoughtworks.grad.configuration.security;

import com.thoughtworks.grad.exception.InvalidCredentialException;
import com.thoughtworks.grad.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!authenticationService.hasJWTToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        handlerRequestAttachedJWTToken(request, response, filterChain);
    }

    private void handlerRequestAttachedJWTToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            JWTUser jwtUser = authenticationService.getAuthorizedJWTUser(request);

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            jwtUser, null, jwtUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(token);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | SignatureException | InvalidCredentialException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired.");
        }
    }
}