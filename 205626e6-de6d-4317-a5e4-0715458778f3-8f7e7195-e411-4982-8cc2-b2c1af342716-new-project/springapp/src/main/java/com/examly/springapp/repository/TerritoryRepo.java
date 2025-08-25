package com.examly.springapp.repository;


import com.examly.springapp.model.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TerritoryRepo extends JpaRepository<Territory, Long> {
    Optional<Territory> findByTerritoryName(String territoryName);
}