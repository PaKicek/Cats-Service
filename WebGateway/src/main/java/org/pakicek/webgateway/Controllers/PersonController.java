package org.pakicek.webgateway.Controllers;

import jakarta.validation.Valid;
import org.pakicek.webgateway.Dtos.PersonDto;
import org.pakicek.webgateway.Dtos.Requests.PersonRequest;
import org.pakicek.webgateway.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    public PersonDto save(@RequestBody PersonRequest personRequest) throws ExecutionException, InterruptedException {
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
    public PersonDto update(@RequestBody PersonRequest personRequest, @PathVariable long id) throws ExecutionException, InterruptedException {
        return personService.update(personRequest, id);
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isOwnerIdOwnedByUser(authentication.name, #id)")
    public PersonDto getById(@PathVariable long id) throws ExecutionException, InterruptedException {
        return personService.getById(id);
    }
    @GetMapping("/getall")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getAll() throws ExecutionException, InterruptedException {
        return personService.getAll();
    }
    @GetMapping("/get/name/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getByNameStartingWith(@PathVariable @Valid String name) throws ExecutionException, InterruptedException {
        return personService.getByNameStartingWith(name);
    }
    @GetMapping("/get/birthdate/{start}/{end}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getByBirthdateBetween(@PathVariable @Valid LocalDate start, @PathVariable @Valid LocalDate end) throws ExecutionException, InterruptedException {
        return personService.getByBirthdateBetween(start, end);
    }
    @GetMapping("/getall/sorted/name/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getFirstSortedByName(@PathVariable Integer count) throws ExecutionException, InterruptedException {
        return personService.getFirstSortedByName(count);
    }
    @GetMapping("/getall/sorted/birthdate/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getFirstSortedByBirthDate(@PathVariable Integer count) throws ExecutionException, InterruptedException {
        return personService.getFirstSortedByBirthDate(count);
    }
    @GetMapping("/getall/lastsorted/birthdate/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<PersonDto> getLastRevSortedByName(@PathVariable Integer count) throws ExecutionException, InterruptedException {
        return personService.getLastSortedByBirthDate(count);
    }
}
