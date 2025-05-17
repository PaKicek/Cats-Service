package org.pakicek.lab3.Dtos.Requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AdminRequest {
    private String Username;
    private String Password;
    private String Name;
    private LocalDate Birthdate;
    public AdminRequest() {}
}
