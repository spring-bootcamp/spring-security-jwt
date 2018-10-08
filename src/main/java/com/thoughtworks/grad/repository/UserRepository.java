package com.thoughtworks.grad.repository;

import com.thoughtworks.grad.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
}
