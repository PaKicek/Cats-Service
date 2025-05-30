package org.pakicek.webgateway.Repositories;

import org.pakicek.webgateway.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    void deleteUserById(long id);

    User findUserById(long id);

    User findUserByUsername(String username);
}
