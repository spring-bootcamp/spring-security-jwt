package com.thoughtworks.grad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.grad.configuration.security.LoginRequestUser;
import com.thoughtworks.grad.domain.Privilege;
import com.thoughtworks.grad.domain.Role;
import com.thoughtworks.grad.domain.User;
import com.thoughtworks.grad.repository.RoleRepository;
import com.thoughtworks.grad.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthenticationControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @BeforeEach
    void setup() {
        super.setup();
        Privilege privilegeCreateUser = new Privilege("CREATE_USER");

        Role systemAdmin = Role.builder().symbol("SYSTEM_ADMIN").privileges(new ArrayList<>()).build();
        systemAdmin.getPrivileges().add(privilegeCreateUser);

        roleRepository.save(systemAdmin);

        userRepository.save(User.builder().role(systemAdmin)
                .telephoneNumber("18192235667")
                .name("future_star").password(passwordEncoder.encode("123")).build());
    }

    @AfterEach
    void teardown() {
        userRepository.deleteAllInBatch();
    }


    @Test
    void should_login_successfully() throws Exception {
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username("future_star").password("123").build();

        mockMvc.perform(post("/api/authentications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("future_star"));
    }

    @Test
    void should_login_failed_when_login_with_bad_credential() throws Exception {
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username("future_sta").password("wrong_password").build();

        mockMvc.perform(post("/api/authentications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().isUnauthorized());
    }

}