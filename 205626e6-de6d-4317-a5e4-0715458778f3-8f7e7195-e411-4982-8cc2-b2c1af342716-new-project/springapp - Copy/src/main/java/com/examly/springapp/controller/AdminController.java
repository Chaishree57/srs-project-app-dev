package com.examly.springapp.controller;

import com.examly.springapp.dto.ApiResponse;
import com.examly.springapp.model.Territory;
import com.examly.springapp.model.User;
import com.examly.springapp.service.TerritoryService;
import com.examly.springapp.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "https://8080-bcdcaffbfaaeeafadbdbacbbfeeccbcaf.premiumproject.examly.io")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TerritoryService territoryService;
    
    // User management endpoints
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve users: " + e.getMessage()));
        }
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user.get()));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve user: " + e.getMessage()));
        }
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isPresent()) {
                user.setId(id);
                User updatedUser = userService.updateUser(user);
                return ResponseEntity.ok(ApiResponse.success("User updated successfully", updatedUser));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update user: " + e.getMessage()));
        }
    }
    
       @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                userService.deleteUser(id);
                return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to delete user: " + e.getMessage()));
        }
    }
    
    // Territory management endpoints
    @PostMapping("/territories")
    public ResponseEntity<ApiResponse> createTerritory(@Valid @RequestBody Territory territory) {
        try {
            Territory savedTerritory = territoryService.createTerritory(territory);
            return ResponseEntity.ok(ApiResponse.success("Territory created successfully", savedTerritory));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to create territory: " + e.getMessage()));
        }
    }
    
    @GetMapping("/territories")
    public ResponseEntity<ApiResponse> getAllTerritories() {
        try {
            List<Territory> territories = territoryService.getAllTerritories();
            return ResponseEntity.ok(ApiResponse.success("Territories retrieved successfully", territories));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve territories: " + e.getMessage()));
        }
    }
    
    @GetMapping("/territories/{id}")
    public ResponseEntity<ApiResponse> getTerritoryById(@PathVariable Long id) {
        try {
            Optional<Territory> territory = territoryService.getTerritoryById(id);
            if (territory.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Territory retrieved successfully", territory.get()));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("Territory not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve territory: " + e.getMessage()));
        }
    }
    
    @PutMapping("/territories/{id}")
    public ResponseEntity<ApiResponse> updateTerritory(@PathVariable Long id, @Valid @RequestBody Territory territory) {
        try {
            Optional<Territory> existingTerritory = territoryService.getTerritoryById(id);
            if (existingTerritory.isPresent()) {
                territory.setId(id);
                Territory updatedTerritory = territoryService.updateTerritory(territory);
                return ResponseEntity.ok(ApiResponse.success("Territory updated successfully", updatedTerritory));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("Territory not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update territory: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/territories/{id}")
    public ResponseEntity<ApiResponse> deleteTerritory(@PathVariable Long id) {
        try {
            Optional<Territory> territory = territoryService.getTerritoryById(id);
            if (territory.isPresent()) {
                territoryService.deleteTerritory(id);
                return ResponseEntity.ok(ApiResponse.success("Territory deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("Territory not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to delete territory: " + e.getMessage()));
        }
    }
}