package org.pakicek.catsservice.Dtos;

import lombok.Getter;
import org.pakicek.catsservice.Entities.Cat;
import org.pakicek.catsservice.Enums.CatColor;

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
    private long OwnerId;
    private List<Long> Friends;
    public CatDto() {}
    public CatDto (Cat cat) {
        Id = cat.getId();
        Name = cat.getName();
        BirthDate = cat.getBirthDate();
        Breed = cat.getBreed();
        Color = cat.getColor();
        OwnerId = cat.getOwnerId();
        Iterable<Cat> list = cat.getFriends();
        Friends = new ArrayList<>();
        if (list != null) {
            for (Cat foundcat : list) {
                Friends.add(foundcat.getId());
            }
        }
    }
    public CatDto (String name, LocalDate birthDate, String breed, CatColor color, long ownerid) {
        Name = name;
        BirthDate = birthDate;
        Breed = breed;
        Color = color;
        OwnerId = ownerid;
    }
}