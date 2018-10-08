package com.thoughtworks.grad.controller;

import com.thoughtworks.grad.configuration.security.JWTUser;
import com.thoughtworks.grad.domain.Privilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BaseControllerTest {

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    protected void loginWithUser(String username, Privilege... privileges) {
        JWTUser jwtUser = JWTUser.builder().username(username)
                .privileges(Arrays.stream(privileges).map(Privilege::getSymbol).collect(Collectors.toList()))
                .build();

        SecurityContext securityContext = new SecurityContextImpl();

        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser.getUsername(), jwtUser.getPassword(), jwtUser.getAuthorities());
        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);
    }
}