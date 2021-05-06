package com.workshopLab.workshopLab.repository;

import com.workshopLab.workshopLab.model.Role;
import com.workshopLab.workshopLab.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
