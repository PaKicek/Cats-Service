package org.pakicek.personsservice.Dtos.Requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PersonRequest {
    private String Name;
    private LocalDate Birthdate;
    public PersonRequest () {}
}
