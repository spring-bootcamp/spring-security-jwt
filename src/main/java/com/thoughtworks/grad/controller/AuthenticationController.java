package com.thoughtworks.grad.controller;

import com.thoughtworks.grad.configuration.security.JWTUser;
import com.thoughtworks.grad.configuration.security.LoginRequestUser;
import com.thoughtworks.grad.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/authentications")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public JWTUser login(@RequestBody LoginRequestUser loginRequestUser, HttpServletResponse response) throws Exception {
        return authService.login(response, loginRequestUser);
    }
}
