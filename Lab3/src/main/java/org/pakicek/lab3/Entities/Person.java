package org.pakicek.lab3.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long Id;
    @Setter
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Setter
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    public Person () {}
    public Person (String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}