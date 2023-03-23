package com.awiya.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    @ManyToMany(fetch = EAGER, cascade = ALL)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            })
    private Set<Role> roles;
}
