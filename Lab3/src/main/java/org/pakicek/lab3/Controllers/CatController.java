package org.pakicek.lab3.Controllers;

import jakarta.validation.Valid;
import org.pakicek.lab3.Dtos.CatDto;
import org.pakicek.lab3.Dtos.Requests.CatRequest;
import org.pakicek.lab3.Enums.CatColor;
import org.pakicek.lab3.Services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cats")
public class CatController {
    private final CatService catService;
    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public CatDto save(@RequestBody CatRequest catRequest) {
        return catService.save(catRequest);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isCatOwnedByUser(authentication.name, #id)")
    public void deleteById(@PathVariable long id) {
        catService.deleteById(id);
    }
    @DeleteMapping("/deleteall")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteAll() {
        catService.deleteAll();
    }
    @PutMapping("/put/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isCatOwnedByUser(authentication.name, #id)")
    public CatDto update(@RequestBody CatRequest catRequest, @PathVariable long id) {
        return catService.update(catRequest, id);
    }
    @PostMapping("/friend/add/{id1}/{id2}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or (@userService.isCatOwnedByUser(authentication.name, #id1) and @userService.isCatOwnedByUser(authentication.name, #id2))")
    public boolean addFriend(@PathVariable long id1, @PathVariable long id2) {
        return catService.addFriend(id1, id2);
    }
    @PostMapping("/friend/remove/{id1}/{id2}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or (@userService.isCatOwnedByUser(authentication.name, #id1) and @userService.isCatOwnedByUser(authentication.name, #id2))")
    public boolean removeFriend(@PathVariable long id1, @PathVariable long id2) {
        return catService.removeFriend(id1, id2);
    }
    @PostMapping("/friend/getall/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isCatOwnedByUser(authentication.name, #id)")
    public List<CatDto> getFriends(@PathVariable long id) {
        return catService.getFriends(id);
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isCatOwnedByUser(authentication.name, #id)")
    public CatDto getById(@PathVariable long id) {
        return catService.getById(id);
    }
    @GetMapping("/getall")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<CatDto> getAll() {
        return catService.getAll();
    }
    @GetMapping("/get/name/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<CatDto> getByNameStartingWith(@PathVariable @Valid String name) {
        return catService.getByNameStartingWith(name);
    }
    @GetMapping("/get/birthdate/{start}/{end}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<CatDto> getByBirthdateBetween(@PathVariable @Valid LocalDate start, @PathVariable @Valid LocalDate end) {
        return catService.getByBirthdateBetween(start, end);
    }
    @GetMapping("/get/breed/{breed}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<CatDto> getByBreed(@PathVariable @Valid String breed) {
        return catService.getByBreed(breed);
    }
    @GetMapping("/get/color/{color}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<CatDto> getByCatColor(@PathVariable @Valid CatColor color) {
        return catService.getByCatColor(color);
    }
    @GetMapping("/get/owner/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.isOwnerIdOwnedByUser(authentication.name, #id)")
    public List<CatDto> getByOwnerId(@PathVariable long id) {
        return catService.getByOwnerId(id);
    }
    @GetMapping("/getall/sorted/name/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<CatDto> getFirstSortedByName(@PathVariable Integer count) {
        return catService.getFirstSortedByName(count);
    }
    @GetMapping("/getall/sorted/birthdate/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<CatDto> getFirstSortedByBirthDate(@PathVariable Integer count) {
        return catService.getFirstSortedByBirthDate(count);
    }
    @GetMapping("/getall/lastsorted/birthdate/{count}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public List<CatDto> getLastRevSortedByName(@PathVariable Integer count) {
        return catService.getLastSortedByBirthDate(count);
    }
}
