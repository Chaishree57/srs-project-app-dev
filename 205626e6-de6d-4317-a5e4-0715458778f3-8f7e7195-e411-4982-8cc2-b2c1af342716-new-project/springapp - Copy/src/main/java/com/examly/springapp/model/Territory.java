package com.examly.springapp.model;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;





@Entity
@Table(name = "territories")
public class Territory {
    
    public enum CompetitionLevel {
        LOW, MEDIUM, HIGH
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "territory_name", unique = true, nullable = false, length = 100)
    private String territoryName;
    
    private String boundaries;
    
    @Column(name = "manager_id")
    private Long managerId;
    
    private String demographics;
    
    @Column(name = "market_data")
    private String marketData;
    
    private Integer population;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "competition_level")
    private CompetitionLevel competitionLevel;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    
    // Constructors
    public Territory() {}
    
    public Territory(String territoryName, String boundaries) {
        this.territoryName = territoryName;
        this.boundaries = boundaries;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTerritoryName() { return territoryName; }
    public void setTerritoryName(String territoryName) { this.territoryName = territoryName; }
    public String getBoundaries() { return boundaries; }
    public void setBoundaries(String boundaries) { this.boundaries = boundaries; }
    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
    public String getDemographics() { return demographics; }
    public void setDemographics(String demographics) { this.demographics = demographics; }
    public String getMarketData() { return marketData; }
    public void setMarketData(String marketData) { this.marketData = marketData; }
    public Integer getPopulation() { return population; }
    public void setPopulation(Integer population) { this.population = population; }
    public CompetitionLevel getCompetitionLevel() { return competitionLevel; }
    public void setCompetitionLevel(CompetitionLevel competitionLevel) { this.competitionLevel = competitionLevel; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}