package org.pakicek.webgateway.Dtos;

import lombok.Getter;
import org.pakicek.webgateway.Entities.User;

@Getter
public class UserDto {
    private long Id;
    private String Username;
    private String Password;
    public UserDto() {}
    public UserDto(User user) {
        Id = user.getId();
        Username = user.getUsername();
        Password = user.getPassword();
    }
    public UserDto(String username, String password) {
        Username = username;
        Password = password;
    }
}
