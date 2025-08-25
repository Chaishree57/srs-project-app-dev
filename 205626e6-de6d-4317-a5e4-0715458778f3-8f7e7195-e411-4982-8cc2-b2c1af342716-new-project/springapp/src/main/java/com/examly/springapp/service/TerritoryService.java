package com.examly.springapp.service;

import com.examly.springapp.model.Territory;
import com.examly.springapp.repository.TerritoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TerritoryService {
    
    @Autowired
    private TerritoryRepo territoryRepository;
    
    public Territory createTerritory(Territory territory) {
        return territoryRepository.save(territory);
    }
    
    public Optional<Territory> getTerritoryById(Long id) {
        return territoryRepository.findById(id);
    }
    
    public Optional<Territory> getTerritoryByName(String name) {
        return territoryRepository.findByTerritoryName(name);
    }
    
    public List<Territory> getAllTerritories() {
        return territoryRepository.findAll();
    }
    
    public Territory updateTerritory(Territory territory) {
        return territoryRepository.save(territory);
    }
    
    public void deleteTerritory(Long id) {
        territoryRepository.deleteById(id);
    }
}