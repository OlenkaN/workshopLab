package com.workshopLab.workshopLab.repository;

import com.workshopLab.workshopLab.model.Role;
import com.workshopLab.workshopLab.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
