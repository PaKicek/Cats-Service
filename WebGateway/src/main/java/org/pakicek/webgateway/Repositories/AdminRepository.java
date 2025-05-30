package org.pakicek.webgateway.Repositories;

import org.pakicek.webgateway.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminByUsername(String username);

    Admin findAdminById(long id);
}
