package org.pakicek.catsservice.Services;

import org.pakicek.catsservice.Dtos.CatDto;
import org.pakicek.catsservice.Dtos.Requests.CatRequest;
import org.pakicek.catsservice.Entities.Cat;
import org.pakicek.catsservice.Enums.CatColor;
import org.pakicek.catsservice.Repositories.CatRepository;
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
public class CatService  {
    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }
    @KafkaListener(topics = "cat-save-topic", groupId = "cat-topics")
    @SendTo
    public CatDto save(CatRequest catRequest, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Cat cat = new Cat(catRequest.getName(), catRequest.getBirthdate(), catRequest.getBreed(), catRequest.getColor(), catRequest.getOwnerId());
        catRepository.save(cat);
        return new CatDto(cat);
    }
    @Transactional
    @KafkaListener(topics = "cat-deletebyid-topic", groupId = "cat-topics")
    @SendTo
    public void deleteById(long id, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        catRepository.deleteCatById(id);
    }
    @KafkaListener(topics = "cat-deleteall-topic", groupId = "cat-topics")
    @SendTo
    public void deleteAll(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        catRepository.deleteAll();
    }
    @KafkaListener(topics = "cat-update-topic", groupId = "cat-topics")
    @SendTo
    public CatDto update(CatRequest catRequest, long id, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Cat cat = catRepository.findCatById(id);
        cat.setName(catRequest.getName());
        cat.setBirthDate(catRequest.getBirthdate());
        cat.setBreed(catRequest.getBreed());
        cat.setColor(catRequest.getColor());
        cat.setOwnerId(catRequest.getOwnerId());
        catRepository.save(cat);
        return new CatDto(cat);
    }
    @KafkaListener(topics = "cat-addfriend-topic", groupId = "cat-topics")
    @SendTo
    public boolean addFriend(long id1, long id2, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
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
    @KafkaListener(topics = "cat-removefriend-topic", groupId = "cat-topics")
    @SendTo
    public boolean removeFriend(long id1, long id2, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
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
    @KafkaListener(topics = "cat-getfriends-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getFriends(long id, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Cat foundcat = catRepository.findCatById(id);
        Iterable<Cat> list = foundcat.getFriends();
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getbyid-topic", groupId = "cat-topics")
    @SendTo
    public CatDto getById(long id, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Cat cat = catRepository.findCatById(id);
        return new CatDto(cat);
    }
    @KafkaListener(topics = "cat-getall-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getAll(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Cat> list = catRepository.findAll();
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getbynamestartingwith-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getByNameStartingWith(String name, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Cat> list = catRepository.findByNameStartingWith(name);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getbybirthdatebetween-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getByBirthdateBetween(LocalDate startBirthDate, LocalDate endBirthDate, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Cat> list = catRepository.findByBirthDateBetween(startBirthDate, endBirthDate);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getbybreed-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getByBreed(String breed, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Cat> list = catRepository.findByBreed(breed);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getbycolor-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getByCatColor(CatColor color, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Cat> list = catRepository.findByColor(color);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getbyownerid-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getByOwnerId(long ownerId, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Iterable<Cat> list = catRepository.findByOwnerId(ownerId);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getfirstsortedbyname-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getFirstSortedByName(Integer count, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("name").ascending());
        Iterable<Cat> list = catRepository.findAll(pageRequest);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getfirstsortedbybirthdate-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getFirstSortedByBirthDate(Integer count, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("birthDate").ascending());
        Iterable<Cat> list = catRepository.findAll(pageRequest);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
    @KafkaListener(topics = "cat-getlastsortedbybirthdate-topic", groupId = "cat-topics")
    @SendTo
    public List<CatDto> getLastSortedByBirthDate(Integer count, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        PageRequest pageRequest = PageRequest.of(0, count, Sort.by("birthDate").descending());
        Iterable<Cat> list = catRepository.findAll(pageRequest);
        List<CatDto> dtolist = new ArrayList<>();
        for (Cat cat : list) {
            dtolist.add(new CatDto(cat));
        }
        return dtolist;
    }
}
