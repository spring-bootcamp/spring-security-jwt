package com.thoughtworks.grad.repository;

import com.thoughtworks.grad.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
