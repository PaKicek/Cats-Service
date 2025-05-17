package org.pakicek.lab3.Repositories;

import org.pakicek.lab3.Dtos.PersonDto;
import org.pakicek.lab3.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonById(long id);

    void deletePersonById(long id);

    Iterable<Person> findByNameStartingWith(String name);

    Iterable<Person> findByBirthDateBetween(LocalDate birthDateAfter, LocalDate birthDateBefore);

    Person findPersonByName(String name);
}
