package org.pakicek.catsservice.Repositories;

import org.pakicek.catsservice.Entities.Cat;
import org.pakicek.catsservice.Enums.CatColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CatRepository extends JpaRepository<Cat, Integer> {
    Cat findCatById(long id);

    void deleteCatById(long id);

    Iterable<Cat> findByNameStartingWith(String name);
    Iterable<Cat> findByBirthDateBetween(LocalDate startBirthDate, LocalDate endBirthDate);

    Iterable<Cat> findByBreed(String breed);

    Iterable<Cat> findByColor(CatColor color);

    Iterable<Cat> findByOwnerId(long ownerId);
}
