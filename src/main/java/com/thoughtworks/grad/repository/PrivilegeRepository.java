package com.thoughtworks.grad.repository;

import com.thoughtworks.grad.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, String> {

}
