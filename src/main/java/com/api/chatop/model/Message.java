package com.api.chatop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Data
@Table(name = "MESSAGES")
public class Message {

    private int id;

    private int rental_id;

    private int user_id;

    private String message;

    private Timestamp created_at;

    private Timestamp updated_at;

    public Message() {

    }

    public Message(int id, int rental_id, int user_id, String message, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.rental_id = rental_id;
        this.user_id = user_id;
        this.message = message;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
}
