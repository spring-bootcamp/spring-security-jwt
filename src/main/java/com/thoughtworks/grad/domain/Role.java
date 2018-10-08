package com.thoughtworks.grad.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_role")
public class Role implements Serializable {

    @Id
    private String symbol;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "t_role_privilege",
            joinColumns = @JoinColumn(name = "role_symbol", referencedColumnName = "symbol"),
            inverseJoinColumns = @JoinColumn(name = "privilege_symbol", referencedColumnName = "symbol"))
    private List<Privilege> privileges = new ArrayList<>();
}
