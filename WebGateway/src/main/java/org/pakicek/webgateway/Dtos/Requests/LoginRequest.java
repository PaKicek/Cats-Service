package org.pakicek.webgateway.Dtos.Requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String Username;
    private String Password;
    public LoginRequest() {}
}
