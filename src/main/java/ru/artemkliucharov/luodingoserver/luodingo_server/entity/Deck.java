package ru.artemkliucharov.luodingoserver.luodingo_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Deck {
    @Id
    private Long id;
    private String name;
    @OneToOne
    private AppUser appUser;
    @ManyToOne
    private Card card;

    public Deck(String name){
        this.name = name;
    }
}