package org.pakicek.webgateway.Services;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.pakicek.webgateway.Dtos.PersonDto;
import org.pakicek.webgateway.Dtos.Requests.PersonRequest;
import org.pakicek.webgateway.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PersonService {
    private final UserRepository userRepository;
    private final ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;
    @Autowired
    public PersonService(UserRepository userRepository, ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate) {
        this.userRepository = userRepository;
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }
    public PersonDto save(PersonRequest personRequest) throws ExecutionException, InterruptedException {
        return (PersonDto) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-save-topic", personRequest)).get().value();
    }
    @Transactional
    public void deleteById(long id) {
        replyingKafkaTemplate.send("person-deletebyid-topic", id);
    }
    public void deleteAll() {
        replyingKafkaTemplate.send("person-deleteall-topic", new PersonRequest());
    }
    public PersonDto update(PersonRequest personRequest, long id) throws ExecutionException, InterruptedException {
        return (PersonDto) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-update-topic", personRequest)).get().value();
    }
    public PersonDto getById(long id) throws ExecutionException, InterruptedException {
        return (PersonDto) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-getbyid-topic", id)).get().value();
    }
    public List<PersonDto> getAll() throws ExecutionException, InterruptedException {
        return (List<PersonDto>) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-getall-topic", "")).get().value();
    }
    public List<PersonDto> getByNameStartingWith(String name) throws ExecutionException, InterruptedException {
        return (List<PersonDto>) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-getbynamestartingwith-topic", name)).get().value();
    }
    public List<PersonDto> getByBirthdateBetween(LocalDate startBirthDate, LocalDate endBirthDate) throws ExecutionException, InterruptedException {
        return (List<PersonDto>) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-getbybirthdatebetween-topic", "")).get().value();
    }
    public List<PersonDto> getFirstSortedByName(Integer count) throws ExecutionException, InterruptedException {
        return (List<PersonDto>) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-getfirstsortedbyname-topic", count)).get().value();
    }
    public List<PersonDto> getFirstSortedByBirthDate(Integer count) throws ExecutionException, InterruptedException {
        return (List<PersonDto>) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-getfirstsortedbybirthdate-topic", count)).get().value();
    }
    public List<PersonDto> getLastSortedByBirthDate(Integer count) throws ExecutionException, InterruptedException {
        return (List<PersonDto>) replyingKafkaTemplate.sendAndReceive(new ProducerRecord<>("person-getlastsortedbybirthdate-topic", count)).get().value();
    }
}
