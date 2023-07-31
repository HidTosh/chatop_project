package com.api.chatop.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ROLES")
public class Role {
    private int id;
    private int user_id;

    private String authority;

    private int enabled;

    public Role() {
    }
    public Role(int id, int user_id, String authority, int enabled) {
        this.id = id;
        this.user_id = user_id;
        this.authority = authority;
        this.enabled = enabled;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
}