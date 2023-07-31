package com.api.chatop.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Data
@Table(name = "USERS")
public class User {
    private int id;
    private String email;

    private String name;

    private String password;

    private Timestamp created_at;

    private Timestamp updated_at;

    public User() {
    }
    public User(int id, String email, String name, String password, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

}