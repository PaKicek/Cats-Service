package org.pakicek.lab3.Controllers;

import jakarta.validation.Valid;
import org.pakicek.lab3.Dtos.PersonDto;
import org.pakicek.lab3.Dtos.Requests.PersonRequest;
import org.pakicek.lab3.Services.PersonService;
import org.pakicek.lab3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public PersonDto save(@RequestBody PersonRequest personRequest) {
        return personService.save(personRequest);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isOwnerIdOwnedByUser(authentication.name, #id)")
    public void deleteById(@PathVariable long id) {
        personService.deleteById(id);
    }
    @DeleteMapping("/deleteall")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAll() {
        personService.deleteAll();
    }
    @PutMapping("/put/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isOwnerIdOwnedByUser(authentication.name, #id)")
    public PersonDto update(@RequestBody PersonRequest personRequest, @PathVariable long id) {
        return personService.update(personRequest, id);
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isOwnerIdOwnedByUser(authentication.name, #id)")
    public PersonDto getById(@PathVariable long id) {
        return personService.getById(id);
    }
    @GetMapping("/getall")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getAll() {
        return personService.getAll();
    }
    @GetMapping("/get/name/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getByNameStartingWith(@PathVariable @Valid String name) {
        return personService.getByNameStartingWith(name);
    }
    @GetMapping("/get/birthdate/{start}/{end}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getByBirthdateBetween(@PathVariable @Valid LocalDate start, @PathVariable @Valid LocalDate end) {
        return personService.getByBirthdateBetween(start, end);
    }
    @GetMapping("/getall/sorted/name/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getFirstSortedByName(@PathVariable Integer count) {
        return personService.getFirstSortedByName(count);
    }
    @GetMapping("/getall/sorted/birthdate/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getFirstSortedByBirthDate(@PathVariable Integer count) {
        return personService.getFirstSortedByBirthDate(count);
    }
    @GetMapping("/getall/lastsorted/birthdate/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getLastRevSortedByName(@PathVariable Integer count) {
        return personService.getLastSortedByBirthDate(count);
    }
}
