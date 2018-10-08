package com.thoughtworks.grad.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.grad.configuration.security.JWTIssuer;
import com.thoughtworks.grad.configuration.security.JWTUser;
import com.thoughtworks.grad.configuration.security.LoginRequestUser;
import com.thoughtworks.grad.exception.InvalidCredentialException;
import com.thoughtworks.grad.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class AuthenticationService {
    @Value("${security.jwt.token-prefix:Bearer}")
    private String tokenPrefix;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.expiration-in-seconds}")
    private long expirationInSeconds;

    @Autowired
    private JWTIssuer jwtIssuer;

    @Autowired
    private AuthenticationManager authenticationManager;


    public JWTUser login(HttpServletResponse response, LoginRequestUser loginRequestUser) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestUser.getUsername(), loginRequestUser.getPassword()));

        JWTUser principal = (JWTUser) authenticate.getPrincipal();

        ObjectMapper objectMapper = new ObjectMapper();

        Map payload = StringUtils.readJsonStringAsObject(StringUtils.writeObjectAsJsonString(principal), Map.class);

        response.addHeader(header, String.join(" ", tokenPrefix,
                jwtIssuer.generateToken(payload)));
        return principal;
    }

    public JWTUser getAuthorizedJWTUser(HttpServletRequest request) {
        String payload = jwtIssuer.extractAuthorizedPayload(extractToken(request));
        return StringUtils.readJsonStringAsObject(payload, JWTUser.class);
    }

    public boolean hasJWTToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(header);
        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(tokenPrefix);
    }

    private String extractToken(HttpServletRequest request) {
        if (!hasJWTToken(request)) {
            throw new InvalidCredentialException();
        }
        return request.getHeader(header).substring(tokenPrefix.length() + 1);
    }
}
