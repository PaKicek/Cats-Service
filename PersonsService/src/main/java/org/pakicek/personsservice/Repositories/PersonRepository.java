package org.pakicek.personsservice.Repositories;

import org.pakicek.personsservice.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonById(long id);

    void deletePersonById(long id);

    Iterable<Person> findByNameStartingWith(String name);

    Iterable<Person> findByBirthDateBetween(LocalDate birthDateAfter, LocalDate birthDateBefore);

    Person findPersonByName(String name);
}
