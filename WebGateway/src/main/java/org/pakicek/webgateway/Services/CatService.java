package org.pakicek.webgateway.Services;

import org.pakicek.webgateway.Dtos.CatDto;
import org.pakicek.webgateway.Dtos.Requests.CatRequest;
import org.pakicek.webgateway.Enums.CatColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    public CatService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
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
        kafkaTemplate.send("cat-deletebyid-topic", id);
    }
    public void deleteAll() {
        kafkaTemplate.send("cat-deleteall-topic", null);
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
