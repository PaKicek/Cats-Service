package org.pakicek.webgateway.Controllers;

import org.pakicek.webgateway.Dtos.Requests.LoginRequest;
import org.pakicek.webgateway.Jwt.JwtAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/log")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    public final JwtAuthProvider jwtAuthProvider;
    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtAuthProvider jwtAuthProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthProvider = jwtAuthProvider;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtAuthProvider.generateToken(auth);
        return ResponseEntity.status(HttpStatus.OK).body(jwt);
    }
}
