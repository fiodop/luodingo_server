package ru.artemkliucharov.luodingoserver.luodingo_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String word;
    private String translation;
    @ManyToOne
    private Deck deck;

    public Card(String word, String translation){
        this.word = word;
        this.translation = translation;
    }
}
