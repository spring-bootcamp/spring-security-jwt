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
@Table(name = "t_privilege")
public class Privilege implements Serializable {

    @Id
    private String symbol;
}
