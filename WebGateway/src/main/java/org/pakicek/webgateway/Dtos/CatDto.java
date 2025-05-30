package org.pakicek.webgateway.Dtos;

import lombok.Getter;
import org.pakicek.webgateway.Enums.CatColor;

import java.time.LocalDate;
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
    public CatDto (long id, String name, LocalDate birthDate, String breed, CatColor color, PersonDto owner, List<Long> friends) {
        Id = id;
        Name = name;
        BirthDate = birthDate;
        Breed = breed;
        Color = color;
        Owner = owner;
        Friends = friends;
    }
    public CatDto (String name, LocalDate birthDate, String breed, CatColor color, PersonDto owner) {
        Name = name;
        BirthDate = birthDate;
        Breed = breed;
        Color = color;
        Owner = owner;
    }
}