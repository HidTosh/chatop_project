package com.api.chatop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String name;

    @Column
    private String password;

    @Column
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Message> userMessages;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private Set<Rental> ownerRentals;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Role> userRoles;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Message> getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(final Set<Message> userMessages) {
        this.userMessages = userMessages;
    }

    public Set<Rental> getOwnerRentals() {
        return ownerRentals;
    }

    public void setOwnerRentals(final Set<Rental> ownerRentals) {
        this.ownerRentals = ownerRentals;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(final Set<Role> userRoles) {
        this.userRoles = userRoles;
    }
}