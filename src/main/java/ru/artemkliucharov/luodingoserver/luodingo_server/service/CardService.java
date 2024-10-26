package ru.artemkliucharov.luodingoserver.luodingo_server.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Card;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Deck;
import ru.artemkliucharov.luodingoserver.luodingo_server.exeption.CardIsNullException;
import ru.artemkliucharov.luodingoserver.luodingo_server.repository.CardRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CardService {
    private CardRepository cardRepository;
    public void save(Card card){
        if(card == null) {
            try {
                throw new CardIsNullException("Card is null");
            } catch (CardIsNullException e) {
                throw new RuntimeException(e);
            }
        }
        cardRepository.save(card);
    }

    public ArrayList<Card> getCardsFromDeck(Deck deck) {
        return cardRepository.getCardByDeck(deck);

    }

    public void delete(Card card){
        cardRepository.delete(card);
    }
}
