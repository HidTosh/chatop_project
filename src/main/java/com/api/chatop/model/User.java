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

    @Column(name = "created_at")
    private OffsetDateTime created_at;

    @Column(name = "updated_at")
    private OffsetDateTime updated_at;

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