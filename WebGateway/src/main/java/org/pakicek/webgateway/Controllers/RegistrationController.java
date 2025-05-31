package org.pakicek.webgateway.Controllers;

import org.pakicek.webgateway.Dtos.Requests.AdminRequest;
import org.pakicek.webgateway.Dtos.Requests.UserRequest;
import org.pakicek.webgateway.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/auth/register")
public class RegistrationController {
    private final UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) throws ExecutionException, InterruptedException {
        userService.register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/admin")
    public ResponseEntity<?> register(@RequestBody AdminRequest adminRequest) {
        userService.register(adminRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
