package com.api.chatop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "RENTAL")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    private String name;

    private float surface;

    private float price;

    private String picture;

    private String description;

    private int owner_id;

    private Timestamp created_at;

    private Timestamp updated_at;

    public Rental() {
    }

    public Rental(
            int id,
            String name,
            float surface,
            float price,
            String picture,
            String description,
            int owner_id,
            Timestamp created_at,
            Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.owner_id = owner_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
/*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rental_id", referencedColumnName = "id")
    private Message message;*/

}
