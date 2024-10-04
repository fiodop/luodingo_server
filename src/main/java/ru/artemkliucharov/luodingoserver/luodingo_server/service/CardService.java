package ru.artemkliucharov.luodingoserver.luodingo_server.service;

import org.springframework.stereotype.Service;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Card;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Deck;
import ru.artemkliucharov.luodingoserver.luodingo_server.exeption.CardIsNullExeption;
import ru.artemkliucharov.luodingoserver.luodingo_server.repository.CardRepository;

import java.util.ArrayList;

@Service
public class CardService {
    private CardRepository cardRepository;
    public void save(Card card){
        if(card == null) {
            try {
                throw new CardIsNullExeption("Card is null");
            } catch (CardIsNullExeption e) {
                throw new RuntimeException(e);
            }
        }
        cardRepository.save(card);
    }

    public ArrayList<Card> getCardsFromDeck(Deck deck) {
        cardRepository.getCardByDeck(deck);
    }
}
