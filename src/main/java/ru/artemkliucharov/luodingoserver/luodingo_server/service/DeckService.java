package ru.artemkliucharov.luodingoserver.luodingo_server.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
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

    public void deleteByName(String deckName){
        deckRepository.deleteByName(deckName);
    }


    public ArrayList<Deck> getAllDecksByAppUser(AppUser appUser) {
        return deckRepository.getAllByAppUser(appUser);
    }
}
