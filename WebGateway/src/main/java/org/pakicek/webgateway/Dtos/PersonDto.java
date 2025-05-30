package org.pakicek.webgateway.Dtos;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PersonDto {
    private long Id;
    private String Name;
    private LocalDate BirthDate;
    public PersonDto () {}
    public PersonDto (long id, String name, LocalDate birthDate) {
        Id = id;
        Name = name;
        BirthDate = birthDate;
    }
    public PersonDto (String name, LocalDate birthDate) {
        Name = name;
        BirthDate = birthDate;
    }
}