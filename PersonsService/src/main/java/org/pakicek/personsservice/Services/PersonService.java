package org.pakicek.personsservice.Services;

import org.pakicek.personsservice.Dtos.PersonDto;
import org.pakicek.personsservice.Dtos.Requests.PersonRequest;
import org.pakicek.personsservice.Entities.Person;
import org.pakicek.personsservice.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService  {
    private final PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @KafkaListener(topics = "person-save-topic", groupId = "reply-topics")
    @SendTo
    public PersonDto save(PersonRequest personRequest, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Person person = new Person(personRequest.getName(), personRequest.getBirthdate());
        personRepository.save(person);
        return new PersonDto(person);
    }
    @Transactional
    @KafkaListener(topics = "person-deletebyid-topic", groupId = "reply-topics")
    public void deleteById(long id) {
        personRepository.deletePersonById(id);
    }
    @KafkaListener(topics = "person-deleteall-topic", groupId = "reply-topics")
    public void deleteAll() {
        personRepository.deleteAll();
    }
    @KafkaListener(topics = "person-update-topic", groupId = "reply-topics")
    @SendTo
    public PersonDto update(PersonRequest personRequest, long id, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Person person = personRepository.findPersonById(id);
        person.setName(personRequest.getName());
        person.setBirthDate(personRequest.getBirthdate());
        personRepository.save(person);
        return new PersonDto(person);
    }
    @KafkaListener(topics = "person-getbyid-topic", groupId = "reply-topics")
    @SendTo
    public PersonDto getById(long id, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        return new PersonDto(personRepository.findPersonById(id));
    }
    @KafkaListener(topics = "person-getall-topic", groupId = "reply-topics")
    @SendTo
    public List<PersonDto> getAll(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Person> list = personRepository.findAll();
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    @KafkaListener(topics = "person-getbynamestartingwith-topic", groupId = "reply-topics")
    @SendTo
    public List<PersonDto> getByNameStartingWith(String name, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Person> list = personRepository.findByNameStartingWith(name);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    @KafkaListener(topics = "person-getbybirthdatebetween-topic", groupId = "reply-topics")
    @SendTo
    public List<PersonDto> getByBirthdateBetween(LocalDate startBirthDate, LocalDate endBirthDate, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Person> list = personRepository.findByBirthDateBetween(startBirthDate, endBirthDate);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    @KafkaListener(topics = "person-getfirstsortedbyname-topic", groupId = "reply-topics")
    @SendTo
    public List<PersonDto> getFirstSortedByName(Integer count, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("name").ascending());
        Iterable<Person> list = personRepository.findAll(pageRequest);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    @KafkaListener(topics = "person-getfirstsortedbybirthdate-topic", groupId = "reply-topics")
    @SendTo
    public List<PersonDto> getFirstSortedByBirthDate(Integer count, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("birthDate").ascending());
        Iterable<Person> list = personRepository.findAll(pageRequest);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
    @KafkaListener(topics = "person-getlastsortedbybirthdate-topic", groupId = "reply-topics")
    @SendTo
    public List<PersonDto> getLastSortedByBirthDate(Integer count, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("birthDate").descending());
        Iterable<Person> list = personRepository.findAll(pageRequest);
        List<PersonDto> dtolist = new ArrayList<>();
        for (Person person : list) {
            dtolist.add(new PersonDto(person));
        }
        return dtolist;
    }
}
