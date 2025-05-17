package org.pakicek.lab3.Dtos.Requests;

import lombok.Getter;
import lombok.Setter;
import org.pakicek.lab3.Enums.CatColor;

import java.time.LocalDate;

@Setter
@Getter
public class CatRequest {
    private String Name;
    private LocalDate Birthdate;
    private String Breed;
    private CatColor Color;
    private long OwnerId;
    public CatRequest() {}
}
