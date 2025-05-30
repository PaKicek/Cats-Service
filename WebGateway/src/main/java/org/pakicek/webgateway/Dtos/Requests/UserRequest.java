package org.pakicek.webgateway.Dtos.Requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserRequest {
    private String Username;
    private String Password;
    private String Name;
    private LocalDate Birthdate;
    public UserRequest() {}
}
