package org.pakicek.webgateway.Services;

import org.pakicek.webgateway.Dtos.PersonDto;
import org.pakicek.webgateway.Dtos.Requests.PersonRequest;
import org.pakicek.webgateway.Entities.User;
import org.pakicek.webgateway.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    public PersonService(UserRepository userRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    public PersonDto save(PersonRequest personRequest) {
        PersonDto person = new PersonDto(personRequest.getName(), personRequest.getBirthdate());
        kafkaTemplate.send("create-person-topic", person);
        return person;
    }
    @Transactional
    public void deleteById(long id) {
        kafkaTemplate.send("delete-person-by-id-topic", id);
    }
    public void deleteAll() {
        kafkaTemplate.send("delete-all-persons", new PersonRequest());
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
