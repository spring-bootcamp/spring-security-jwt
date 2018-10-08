package com.thoughtworks.grad.controller;

import com.thoughtworks.grad.domain.User;
import com.thoughtworks.grad.exception.UserNotExistException;
import com.thoughtworks.grad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_RETRIEVE_USERS")
    @GetMapping("/api/users")
    public Collection<User> queryUsers() {
        return userRepository.findAll();
    }

    @Secured("ROLE_RETRIEVE_USER")
    @GetMapping("/api/users/{id}")
    public User queryUser(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotExistException(id));
    }

    @Secured("ROLE_CREATE_USER")
    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
    }

    @Secured("ROLE_DELETE_USER")
    @DeleteMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

}

