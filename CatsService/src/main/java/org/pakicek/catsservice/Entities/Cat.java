package org.pakicek.catsservice.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.pakicek.catsservice.Enums.CatColor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "Cat")
public class Cat {
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
    @Setter
    @Column(name = "breed", nullable = false, length = 50)
    private String breed;
    @Setter
    @Column(name = "cat_color", nullable = false)
    @Enumerated(EnumType.STRING)
    private CatColor color;
    @Setter
    @Column(name = "owner_id", nullable = false)
    private long ownerId;
    @Setter
    @OneToMany
    @JoinTable(
            name = "CatFriends",
            joinColumns = @JoinColumn(name = "cat1_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cat2_id", referencedColumnName = "id")
    )
    private List<Cat> friends;
    public Cat () {}
    public Cat (String name, LocalDate birthDate, String breed, CatColor color, long ownerId) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.ownerId = ownerId;
    }

    public void addFriend (Cat cat) {
        this.friends.add(cat);
        cat.friends.add(this);
    }
    public void removeFriend (Cat cat) {
        this.friends.remove(cat);
        cat.friends.remove(this);
    }
}