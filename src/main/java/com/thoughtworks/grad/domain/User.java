package com.thoughtworks.grad.domain;

import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String password;

    @Column(unique = true)
    private String telephoneNumber;

    public User() {
    }

    @OneToOne
    private Role role;
}
