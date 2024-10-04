package ru.artemkliucharov.luodingoserver.luodingo_server.service;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Card;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Deck;
import ru.artemkliucharov.luodingoserver.luodingo_server.repository.DeckRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class DeckService {
    private final DeckRepository deckRepository;

    public void save(Deck deck){
        deckRepository.save(deck);
    }

    public void delete(Deck deck){
        deckRepository.delete(deck);
    }


}
