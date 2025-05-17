package org.pakicek.lab3.Services;

import org.pakicek.lab3.Dtos.CatDto;
import org.pakicek.lab3.Dtos.Requests.CatRequest;
import org.pakicek.lab3.Entities.Cat;
import org.pakicek.lab3.Entities.Person;
import org.pakicek.lab3.Entities.User;
import org.pakicek.lab3.Enums.CatColor;
import org.pakicek.lab3.Repositories.CatRepository;
import org.pakicek.lab3.Repositories.PersonRepository;
import org.pakicek.lab3.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatService  {
    private final CatRepository catRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public CatService(CatRepository catRepository, PersonRepository personRepository, UserRepository userRepository, UserService userService) {
        this.catRepository = catRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    public CatDto save(CatRequest catRequest) {
        Cat cat = new Cat(catRequest.getName(), catRequest.getBirthdate(), catRequest.getBreed(), catRequest.getColor(), personRepository.findPersonById((catRequest.getOwnerId())));
        Person person = personRepository.findPersonById(catRequest.getOwnerId());
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
            if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || person.getId() == user.getOwner().getId()) {
                catRepository.save(cat);
                return new CatDto(cat);
            }
        }
        throw new RuntimeException("User not found");
    }
    @Transactional
    public void deleteById(long id) {
        Cat cat = catRepository.findCatById(id);
        catRepository.deleteCatById(id);
    }
    public void deleteAll() {
        catRepository.deleteAll();
    }
    public CatDto update(CatRequest catRequest, long id) {
        Cat cat = catRepository.findCatById(id);
        cat.setName(catRequest.getName());
        cat.setBirthDate(catRequest.getBirthdate());
        cat.setBreed(catRequest.getBreed());
        cat.setColor(catRequest.getColor());
        cat.setOwner(personRepository.findPersonById(catRequest.getOwnerId()));
        catRepository.save(cat);
        return new CatDto(cat);
    }
    public boolean addFriend(long id1, long id2) {
        Cat cat1 = catRepository.findCatById(id1);
        Cat cat2 = catRepository.findCatById(id2);
        if (cat1.getId() != cat2.getId() && !cat1.getFriends().contains(cat2) && !cat2.getFriends().contains(cat1)) {
            cat1.addFriend(cat2);
            catRepository.save(cat1);
            catRepository.save(cat2);
            return true;
        } else {
            return false;
        }
    }
    public boolean removeFriend(long id1, long id2) {
        Cat cat1 = catRepository.findCatById(id1);
        Cat cat2 = catRepository.findCatById(id2);
        if (cat1.getId() != cat2.getId() && cat1.getFriends().contains(cat2) && cat2.getFriends().contains(cat1)) {
            cat1.removeFriend(cat2);
            catRepository.save(cat1);
            catRepository.save(cat2);
            return true;
        } else {
            return false;
        }
    }
    public List<CatDto> getFriends(long id) {
        Cat foundcat = catRepository.findCatById(id);
        Iterable<Cat> list = foundcat.getFriends();
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    public CatDto getById(long id) {
        Cat cat = catRepository.findCatById(id);
        return new CatDto(cat);
    }
    public List<CatDto> getAll() {
        Iterable<Cat> list = catRepository.findAll();
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userService.isCatOwnedByUser(user.getUsername(), cat.getId())) {
                    dtolist.add(new CatDto(cat));
                }
            }
        }
        return dtolist;
    }
    public List<CatDto> getByNameStartingWith(String name) {
        Iterable<Cat> list = catRepository.findByNameStartingWith(name);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userService.isCatOwnedByUser(user.getUsername(), cat.getId())) {
                    dtolist.add(new CatDto(cat));
                }
            }
        }
        return dtolist;
    }
    public List<CatDto> getByBirthdateBetween(LocalDate startBirthDate, LocalDate endBirthDate) {
        Iterable<Cat> list = catRepository.findByBirthDateBetween(startBirthDate, endBirthDate);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userService.isCatOwnedByUser(user.getUsername(), cat.getId())) {
                    dtolist.add(new CatDto(cat));
                }
            }
        }
        return dtolist;
    }
    public List<CatDto> getByBreed(String breed) {
        Iterable<Cat> list = catRepository.findByBreed(breed);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userService.isCatOwnedByUser(user.getUsername(), cat.getId())) {
                    dtolist.add(new CatDto(cat));
                }
            }
        }
        return dtolist;
    }
    public List<CatDto> getByCatColor(CatColor color) {
        Iterable<Cat> list = catRepository.findByColor(color);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userService.isCatOwnedByUser(user.getUsername(), cat.getId())) {
                    dtolist.add(new CatDto(cat));
                }
            }
        }
        return dtolist;
    }
    public List<CatDto> getByOwnerId(long ownerId) {
        Iterable<Cat> list = catRepository.findByOwner(personRepository.findPersonById(ownerId));
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    public List<CatDto> getFirstSortedByName(Integer count) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("name").ascending());
        Iterable<Cat> list = catRepository.findAll(pageRequest);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userService.isCatOwnedByUser(user.getUsername(), cat.getId())) {
                    dtolist.add(new CatDto(cat));
                }
            }
        }
        return dtolist;
    }
    public List<CatDto> getFirstSortedByBirthDate(Integer count) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("birthDate").ascending());
        Iterable<Cat> list = catRepository.findAll(pageRequest);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userService.isCatOwnedByUser(user.getUsername(), cat.getId())) {
                    dtolist.add(new CatDto(cat));
                }
            }
        }
        return dtolist;
    }
    public List<CatDto> getLastSortedByBirthDate(Integer count) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("birthDate").descending());
        Iterable<Cat> list = catRepository.findAll(pageRequest);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
                if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userService.isCatOwnedByUser(user.getUsername(), cat.getId())) {
                    dtolist.add(new CatDto(cat));
                }
            }
        }
        return dtolist;
    }
}
