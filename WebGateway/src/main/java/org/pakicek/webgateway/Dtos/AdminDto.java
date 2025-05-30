package org.pakicek.webgateway.Dtos;

import lombok.Getter;
import org.pakicek.webgateway.Entities.Admin;

@Getter
public class AdminDto {
    private long Id;
    private String Username;
    private String Password;
    public AdminDto() {}
    public AdminDto(Admin admin) {
        Id = admin.getId();
        Username = admin.getUsername();
        Password = admin.getPassword();
    }
    public AdminDto(String username, String password) {
        Username = username;
        Password = password;
    }
}
