package org.pakicek.lab3.Repositories;

import org.pakicek.lab3.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminByUsername(String username);

    Admin findAdminById(long id);
}
