package org.pakicek.webgateway.Controllers;

import org.pakicek.webgateway.Dtos.AdminDto;
import org.pakicek.webgateway.Dtos.UserDto;
import org.pakicek.webgateway.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/session")
public class SessionController {
    private final UserService userService;
    @Autowired
    public SessionController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/get")
    public ResponseEntity<?> getSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getPrincipal() + " " + auth.getAuthorities());
    }
    @GetMapping("/users/getall")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/admins/getall")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AdminDto> getAllAdmins() {
        return userService.getAllAdmins();
    }
    @GetMapping("/users/delete/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUserByUsername(username);
    }
    @GetMapping("/admins/delete/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAdmin(@PathVariable String username) {
        userService.deleteAdminByUsername(username);
    }
}
