package org.pakicek.webgateway.Services;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.pakicek.webgateway.Dtos.CatDto;
import org.pakicek.webgateway.Dtos.PersonDto;
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
import java.util.concurrent.ExecutionException;

@Service
public class CatService {
    private final ReplyingKafkaTemplate<String, Object, Object> replyingkafkaTemplate;
    @Autowired
    public CatService(ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate) {
        this.replyingkafkaTemplate = replyingKafkaTemplate;
    }
    public CatDto save(CatRequest catRequest) throws ExecutionException, InterruptedException {
        return (CatDto) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-save-topic", catRequest)).get().value();
    }
    @Transactional
    public void deleteById(long id) {
        replyingkafkaTemplate.send("cat-deletebyid-topic", id);
    }
    public void deleteAll() {
        replyingkafkaTemplate.send("cat-deleteall-topic", null);
    }
    public CatDto update(CatRequest catRequest, long id) throws ExecutionException, InterruptedException {
        return (CatDto) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-update-topic", catRequest)).get().value();
    }
    public boolean addFriend(long id1, long id2) throws ExecutionException, InterruptedException {
        return (boolean) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-addfriend-topic", id1)).get().value();
    }
    public boolean removeFriend(long id1, long id2) throws ExecutionException, InterruptedException {
        return (boolean) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-removefriend-topic", id1)).get().value();
    }
    public List<CatDto> getFriends(long id) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getfriends-topic", id)).get().value();
    }
    public CatDto getById(long id) throws ExecutionException, InterruptedException {
        return (CatDto) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getbyid-topic", id)).get().value();
    }
    public List<CatDto> getAll() throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getall-topic", "")).get().value();
    }
    public List<CatDto> getByNameStartingWith(String name) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getbynamestartingwith-topic", name)).get().value();
    }
    public List<CatDto> getByBirthdateBetween(LocalDate startBirthDate, LocalDate endBirthDate) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getbybirthdatebetween-topic", startBirthDate)).get().value();
    }
    public List<CatDto> getByBreed(String breed) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getbybreed-topic", breed)).get().value();
    }
    public List<CatDto> getByCatColor(CatColor color) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getbycatcolor-topic", color)).get().value();
    }
    public List<CatDto> getByOwnerId(long ownerId) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getbyownerid-topic", ownerId)).get().value();
    }
    public List<CatDto> getFirstSortedByName(Integer count) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getfirstsortedbyname-topic", count)).get().value();
    }
    public List<CatDto> getFirstSortedByBirthDate(Integer count) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getfirstsortedbybirthdate-topic", count)).get().value();
    }
    public List<CatDto> getLastSortedByBirthDate(Integer count) throws ExecutionException, InterruptedException {
        return (List<CatDto>) replyingkafkaTemplate.sendAndReceive(new ProducerRecord<>("cat-getlastsortedbybirthdate-topic", count)).get().value();
    }
}
