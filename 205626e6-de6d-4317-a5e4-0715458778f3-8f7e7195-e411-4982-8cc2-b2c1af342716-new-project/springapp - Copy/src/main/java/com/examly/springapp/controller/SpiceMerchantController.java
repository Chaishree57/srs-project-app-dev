package com.examly.springapp.controller;

import com.examly.springapp.dto.ApiResponse;
import com.examly.springapp.dto.SpiceMerchantRequest;
import com.examly.springapp.model.SpiceMerchant;
import com.examly.springapp.model.User;
import com.examly.springapp.service.SpiceMerchantService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spice-merchants")
@CrossOrigin(origins = "https://8080-bcdcaffbfaaeeafadbdbacbbfeeccbcaf.premiumproject.examly.io")
public class SpiceMerchantController {
    
    @Autowired
    private SpiceMerchantService spiceMerchantService;
    
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createSpiceMerchant(
            @Valid @RequestBody SpiceMerchantRequest request,
            @AuthenticationPrincipal User user) {
        
        try {
            SpiceMerchant spiceMerchant = new SpiceMerchant();
            spiceMerchant.setName(request.getName());
            spiceMerchant.setSpices(request.getSpices());
            spiceMerchant.setExperience(request.getExperience());
            spiceMerchant.setStoreLocation(request.getStoreLocation());
            spiceMerchant.setPhoneNumber(request.getPhoneNumber());
            spiceMerchant.setUser(user); // Associate with logged-in user
            
            if (request.getTerritoryId() != null) {
                spiceMerchant.setTerritoryId(request.getTerritoryId());
            }
            
            SpiceMerchant savedMerchant = spiceMerchantService.createSpiceMerchant(spiceMerchant);
            return ResponseEntity.ok(ApiResponse.success("Spice merchant application submitted successfully", savedMerchant));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Application submission failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllSpiceMerchants() {
        try {
            List<SpiceMerchant> merchants = spiceMerchantService.getAllSpiceMerchants();
            return ResponseEntity.ok(ApiResponse.success("Spice merchants retrieved successfully", merchants));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve spice merchants: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getSpiceMerchantById(@PathVariable Long id) {
        try {
            Optional<SpiceMerchant> merchant = spiceMerchantService.getSpiceMerchantById(id);
            if (merchant.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Spice merchant retrieved successfully", merchant.get()));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("Spice merchant not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve spice merchant: " + e.getMessage()));
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse> getSpiceMerchantsByStatus(@PathVariable SpiceMerchant.Status status) {
        try {
            List<SpiceMerchant> merchants = spiceMerchantService.getSpiceMerchantsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success("Spice merchants retrieved successfully", merchants));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve spice merchants: " + e.getMessage()));
        }
    }
    
    // Add the new endpoint to get applications by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getSpiceMerchantsByUser(@PathVariable Long userId) {
        try {
            List<SpiceMerchant> merchants = spiceMerchantService.getSpiceMerchantsByUserId(userId);
            if (merchants.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("No applications found for this user"));
            }
            return ResponseEntity.ok(ApiResponse.success("Spice merchants retrieved successfully", merchants));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve spice merchants: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSpiceMerchant(@PathVariable Long id, @RequestBody SpiceMerchant spiceMerchant) {
        try {
            Optional<SpiceMerchant> existingMerchant = spiceMerchantService.getSpiceMerchantById(id);
            if (existingMerchant.isPresent()) {
                spiceMerchant.setId(id);
                SpiceMerchant updatedMerchant = spiceMerchantService.updateSpiceMerchant(spiceMerchant);
                return ResponseEntity.ok(ApiResponse.success("Spice merchant updated successfully", updatedMerchant));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("Spice merchant not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update spice merchant: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSpiceMerchant(@PathVariable Long id) {
        try {
            Optional<SpiceMerchant> merchant = spiceMerchantService.getSpiceMerchantById(id);
            if (merchant.isPresent()) {
                spiceMerchantService.deleteSpiceMerchant(id);
                return ResponseEntity.ok(ApiResponse.success("Spice merchant deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("Spice merchant not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to delete spice merchant: " + e.getMessage()));
        }
    }
}