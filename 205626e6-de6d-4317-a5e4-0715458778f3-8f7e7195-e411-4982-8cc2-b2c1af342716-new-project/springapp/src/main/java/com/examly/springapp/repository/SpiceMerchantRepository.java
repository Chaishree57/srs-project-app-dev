package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examly.springapp.model.SpiceMerchant;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpiceMerchantRepository extends JpaRepository<SpiceMerchant, Long> {
    List<SpiceMerchant> findByStatus(SpiceMerchant.Status status);
    List<SpiceMerchant> findByTerritoryId(Long territoryId);
    Boolean existsByNameAndStoreLocation(String name, String storeLocation);
    // Add this method to your repository interface
List<SpiceMerchant> findByUserId(Long userId);

}