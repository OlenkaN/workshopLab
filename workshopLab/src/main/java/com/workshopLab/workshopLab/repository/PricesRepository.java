package com.workshopLab.workshopLab.repository;
import com.workshopLab.workshopLab.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricesRepository extends JpaRepository<Prices,Long>{
}
