package org.pakicek.lab3.Dtos;

import lombok.Getter;
import org.pakicek.lab3.Entities.Cat;
import org.pakicek.lab3.Entities.Person;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PersonDto {
    private long Id;
    private String Name;
    private LocalDate BirthDate;
    public PersonDto () {}
    public PersonDto (Person person) {
        Id = person.getId();
        Name = person.getName();
        BirthDate = person.getBirthDate();
    }
    public PersonDto (String name, LocalDate birthDate) {
        Name = name;
        BirthDate = birthDate;
    }
}