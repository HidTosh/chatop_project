package com.api.chatop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column
    private String picture;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    @Column(precision = 12, scale = 2)
    private BigDecimal surface;

    @Column(name = "created_at")
    private OffsetDateTime created_at;

    @Column(name = "updated_at")
    private OffsetDateTime updated_at;

    @OneToMany(mappedBy = "rental")
    @JsonIgnore
    private Set<Message> rentalMessages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public void setSurface(final BigDecimal surface) {
        this.surface = surface;
    }

    public OffsetDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(final OffsetDateTime created_at) {
        this.created_at = created_at;
    }

    public OffsetDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(final OffsetDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Set<Message> getRentalMessages() {
        return rentalMessages;
    }

    public void setRentalMessages(final Set<Message> rentalMessages) {
        this.rentalMessages = rentalMessages;
    }

    public User getOwner() {
        return owner;
    }

    public Integer getOwner_id() {
        return owner.getId();
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }
}
