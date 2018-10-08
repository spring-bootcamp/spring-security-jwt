package com.thoughtworks.grad.domain;

import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "t_user")
public class User {

    @Id
    private String id;

    @Column(unique = true)
    private String name;

    private String password;


    public User() {
        id = UUID.randomUUID().toString();
    }

    @OneToOne
    private Role role;
}
