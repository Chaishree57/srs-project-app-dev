package com.examly.springapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public class SpiceMerchantRequest {
    
    @NotBlank(message = "Business name is required")
    private String name;
    
    @NotBlank(message = "Spices information is required")
    private String spices;
    
    @NotNull(message = "Experience is required")
    @PositiveOrZero(message = "Experience must be a positive number or zero")
    private Integer experience;
    
    @NotBlank(message = "Store location is required")
    private String storeLocation;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    
    private Long territoryId;
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpices() { return spices; }
    public void setSpices(String spices) { this.spices = spices; }
    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }
    public String getStoreLocation() { return storeLocation; }
    public void setStoreLocation(String storeLocation) { this.storeLocation = storeLocation; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Long getTerritoryId() { return territoryId; }
    public void setTerritoryId(Long territoryId) { this.territoryId = territoryId; }
}