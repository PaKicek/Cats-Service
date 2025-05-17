package org.pakicek.lab3.Repositories;

import org.pakicek.lab3.Dtos.CatDto;
import org.pakicek.lab3.Entities.Cat;
import org.pakicek.lab3.Entities.Person;
import org.pakicek.lab3.Enums.CatColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface CatRepository extends JpaRepository<Cat, Integer> {
    Cat findCatById(long id);

    void deleteCatById(long id);

    Iterable<Cat> findByNameStartingWith(String name);
    Iterable<Cat> findByBirthDateBetween(LocalDate startBirthDate, LocalDate endBirthDate);

    Iterable<Cat> findByBreed(String breed);

    Iterable<Cat> findByColor(CatColor color);

    Iterable<Cat> findByOwner(Person owner);
}
