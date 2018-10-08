package com.thoughtworks.grad.service;

import com.thoughtworks.grad.configuration.security.JWTUser;
import com.thoughtworks.grad.domain.Privilege;
import com.thoughtworks.grad.domain.Role;
import com.thoughtworks.grad.domain.User;
import com.thoughtworks.grad.exception.UserExistedException;
import com.thoughtworks.grad.exception.UserNotExistException;
import com.thoughtworks.grad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username does not exist.");
        }
        Role role = user.getRole();
        return JWTUser.builder()
                .username(user.getName())
                .password(user.getPassword())
                .role(user.getRole().getSymbol())
                .privileges(role.getPrivileges().stream().map(Privilege::getSymbol).collect(toList()))
                .build();
    }

    @Transactional
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByName(user.getName()) != null) {
            throw new UserExistedException("User already exists.");
        }
        return userRepository.save(user);
    }
}
