package com.RaccoonGG.test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor()
public class Model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal averagePrice;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "updated_at")
    private String updatedAt;

    public Model() {
    }

    public Model(Long id, String name, BigDecimal averagePrice, Brand brand) {
        this.id = id;
        this.name = name;
        this.averagePrice = averagePrice;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt.toString();
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt.toString();
    }

    @PrePersist
    public void beforeCreate(){
        this.createdAt = LocalDateTime.now().toString();
    }

    @PreUpdate
    public void beforeUpdate(){
        this.updatedAt = LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", averagePrice=" + averagePrice +
                ", brand=" + brand +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
