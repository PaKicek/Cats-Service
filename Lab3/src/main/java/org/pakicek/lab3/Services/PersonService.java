package org.pakicek.lab3.Services;

import org.pakicek.lab3.Dtos.PersonDto;
import org.pakicek.lab3.Dtos.Requests.PersonRequest;
import org.pakicek.lab3.Entities.Person;
import org.pakicek.lab3.Entities.User;
import org.pakicek.lab3.Repositories.PersonRepository;
import org.pakicek.lab3.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService  {
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    @Autowired
    public PersonService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }
    public PersonDto save(PersonRequest personRequest) {
        Person person = new Person(personRequest.getName(), personRequest.getBirthdate());
        personRepository.save(person);
        return new PersonDto(person);
    }
    @Transactional
    public void deleteById(long id) {
        personRepository.deletePersonById(id);
    }
    public void deleteAll() {
        personRepository.deleteAll();
    }
    public PersonDto update(PersonRequest personRequest, long id) {
        Person person = personRepository.findPersonById(id);
        person.setName(personRequest.getName());
        person.setBirthDate(personRequest.getBirthdate());
        personRepository.save(person);
        User user = userRepository.findUserByUsername(personRequest.getName());
        user.setOwner(person);
        userRepository.save(user);
        return new PersonDto(person);
    }
    public PersonDto getById(long id) {
        return new PersonDto(personRepository.findPersonById(id));
    }
    public List<PersonDto> getAll() {
        Iterable<Person> list = personRepository.findAll();
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    public List<PersonDto> getByNameStartingWith(String name) {
        Iterable<Person> list = personRepository.findByNameStartingWith(name);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    public List<PersonDto> getByBirthdateBetween(LocalDate startBirthDate, LocalDate endBirthDate) {
        Iterable<Person> list = personRepository.findByBirthDateBetween(startBirthDate, endBirthDate);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    public List<PersonDto> getFirstSortedByName(Integer count) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("name").ascending());
        Iterable<Person> list = personRepository.findAll(pageRequest);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    public List<PersonDto> getFirstSortedByBirthDate(Integer count) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("birthDate").ascending());
        Iterable<Person> list = personRepository.findAll(pageRequest);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    public List<PersonDto> getLastSortedByBirthDate(Integer count) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("birthDate").descending());
        Iterable<Person> list = personRepository.findAll(pageRequest);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
}
