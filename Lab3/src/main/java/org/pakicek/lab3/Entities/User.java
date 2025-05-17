package org.pakicek.lab3.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table(name = "UserTable")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long Id;
    @Setter
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    @Setter
    @Column(name = "password")
    private String password;
    @Setter
    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;
    public User() {}
    public User(String username, String password, Person owner) {
        this.username = username;
        this.password = password;
        this.owner = owner;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
