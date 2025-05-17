package org.pakicek.lab3.Repositories;

import org.pakicek.lab3.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    void deleteUserById(long id);

    User findUserById(long id);

    User findUserByUsername(String username);
}
