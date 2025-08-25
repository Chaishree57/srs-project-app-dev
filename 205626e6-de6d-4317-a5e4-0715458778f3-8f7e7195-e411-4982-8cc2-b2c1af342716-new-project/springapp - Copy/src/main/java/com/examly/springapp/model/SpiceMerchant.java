package com.examly.springapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity

public class SpiceMerchant {
    
    public enum Status {
        SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, PENDING
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
// Add this field to your SpiceMerchant class (after territoryId field)
@ManyToOne
@JoinColumn(name = "user_id")
private User user;


    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 500)
    private String spices;
    
    @Column(nullable = false)
    private Integer experience;
    
    @Column(name = "store_location", nullable = false, length = 200)
    private String storeLocation;
    
    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;
    
    @Column(name = "application_date")
    private LocalDateTime applicationDate = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.SUBMITTED;
    
    @Column(name = "evaluator_id")
    private Long evaluatorId;
    
    @Column(name = "territory_id")
    private Long territoryId;
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Constructors
    public SpiceMerchant() {}
    
    public SpiceMerchant(String name, String spices, Integer experience, 
                        String storeLocation, String phoneNumber) {
        this.name = name;
        this.spices = spices;
        this.experience = experience;
        this.storeLocation = storeLocation;
        this.phoneNumber = phoneNumber;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Long getEvaluatorId() { return evaluatorId; }
    public void setEvaluatorId(Long evaluatorId) { this.evaluatorId = evaluatorId; }
    public Long getTerritoryId() { return territoryId; }
    public void setTerritoryId(Long territoryId) { this.territoryId = territoryId; }

    
}