package org.pakicek.webgateway.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getStartPage( ) {
        return "index.html";
    }
    @GetMapping("/cats")
    @PreAuthorize("isAuthenticated()")
    public String getCatsPage() {
        return "cats.html";
    }
    @GetMapping("/persons")
    @PreAuthorize("isAuthenticated()")
    public String getPersonsPage() {
        return "persons.html";
    }
}
