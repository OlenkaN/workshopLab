package com.workshopLab.workshopLab.repository;

import com.workshopLab.workshopLab.model.Request;
import com.workshopLab.workshopLab.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
