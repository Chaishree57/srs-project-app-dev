package com.examly.springapp.service;

import com.examly.springapp.exception.InvalidNameException;
import com.examly.springapp.exception.InvalidPhoneException;
import com.examly.springapp.model.SpiceMerchant;
import com.examly.springapp.repository.SpiceMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SpiceMerchantService {
    
    @Autowired
    private SpiceMerchantRepository spiceMerchantRepository;
    
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    
    public SpiceMerchant createSpiceMerchant(SpiceMerchant spiceMerchant) {
        // Validate name
        if (!NAME_PATTERN.matcher(spiceMerchant.getName()).matches()) {
            throw new InvalidNameException("Name must contain only alphabetic characters");
        }
        
        // Validate phone number
        if (!PHONE_PATTERN.matcher(spiceMerchant.getPhoneNumber()).matches()) {
            throw new InvalidPhoneException("Phone number must be exactly 10 digits");
        }
        
        // Validate experience
        if (spiceMerchant.getExperience() < 0) {
            throw new IllegalArgumentException("Experience must be a non-negative number");
        }
        
        // Check for duplicate application
        if (spiceMerchantRepository.existsByNameAndStoreLocation(
            spiceMerchant.getName(), spiceMerchant.getStoreLocation())) {
            throw new IllegalArgumentException("An application already exists for this merchant at this location");
        }
        
        return spiceMerchantRepository.save(spiceMerchant);
    }
    
   public Optional<SpiceMerchant> getSpiceMerchantById(Long id) {
        return spiceMerchantRepository.findById(id);
    }
    
    public List<SpiceMerchant> getAllSpiceMerchants() {
        return spiceMerchantRepository.findAll();
    }
    
    public List<SpiceMerchant> getSpiceMerchantsByStatus(SpiceMerchant.Status status) {
        return spiceMerchantRepository.findByStatus(status);
    }
    
    public List<SpiceMerchant> getSpiceMerchantsByTerritory(Long territoryId) {
        return spiceMerchantRepository.findByTerritoryId(territoryId);
    }
    
    public SpiceMerchant updateSpiceMerchant(SpiceMerchant spiceMerchant) {
        return spiceMerchantRepository.save(spiceMerchant);
    }
    
    public void deleteSpiceMerchant(Long id) {
        spiceMerchantRepository.deleteById(id);
    }

    // Add this method to your service class
public List<SpiceMerchant> getSpiceMerchantsByUserId(Long userId) {
    return spiceMerchantRepository.findByUserId(userId);
}

}