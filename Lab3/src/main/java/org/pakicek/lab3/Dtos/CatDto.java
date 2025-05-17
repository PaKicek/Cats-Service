package org.pakicek.lab3.Dtos;

import lombok.Getter;
import org.pakicek.lab3.Entities.Cat;
import org.pakicek.lab3.Enums.CatColor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CatDto {
    private long Id;
    private String Name;
    private LocalDate BirthDate;
    private String Breed;
    private CatColor Color;
    private PersonDto Owner;
    private List<Long> Friends;
    public CatDto() {}
    public CatDto (Cat cat) {
        Id = cat.getId();
        Name = cat.getName();
        BirthDate = cat.getBirthDate();
        Breed = cat.getBreed();
        Color = cat.getColor();
        Owner = new PersonDto(cat.getOwner());
        Iterable<Cat> list = cat.getFriends();
        Friends = new ArrayList<>();
        if (list != null) {
            for (Cat foundcat : list) {
                Friends.add(foundcat.getId());
            }
        }
    }
    public CatDto (String name, LocalDate birthDate, String breed, CatColor color, PersonDto owner) {
        Name = name;
        BirthDate = birthDate;
        Breed = breed;
        Color = color;
        Owner = owner;
    }
}