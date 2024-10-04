package ru.artemkliucharov.luodingoserver.luodingo_server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Card;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Deck;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AppUserService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.CardService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.DeckService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.JwtService;

import java.util.ArrayList;

@RestController
@RequestMapping("/luodingo/deck")
@AllArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final AppUserService appUserService;
    private final CardService cardService;
    private final JwtService jwtService;

    @PostMapping("/add-deck")
    public ResponseEntity<Object> addDeck(@RequestHeader (value = "Authorization") String header,
                                          @RequestBody Deck deck){
        AppUser appUser;
        try {
            appUser = jwtService.authorize(header);

            deck.setAppUser(appUser);
            deckService.save(deck);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Deck saved");
    }

    @PostMapping("/delete-deck")
    public ResponseEntity<Object> deleteDeck(@RequestHeader(value = "Authorization") String header, @RequestBody Deck deck){
        try{
            jwtService.authorize(header);
            deckService.delete(deck);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/add-card")
    public ResponseEntity<Object> addCard(@RequestHeader(value = "Authorization") String header,
                                          @RequestBody Deck deck,
                                          @RequestBody Card card){

        try {
            jwtService.authorize(header);
            card.setDeck(deck);
            cardService.save(card);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> getCards(@RequestHeader(value = "Authorization") String header,
                                           @RequestBody Deck deck){
        try{
            jwtService.authorize(header);
            ArrayList<Card> cardsOfDeck = cardService.getCardsFromDeck(deck);
            return ResponseEntity.status(HttpStatus.OK).body(cardsOfDeck);
        } catch (Exception e){

        }


    }

    @PostMapping("/delete-card")
    public ResponseEntity<Object> deleteCard(@RequestHeader(value = "Authorization")String header,
                                             @RequestBody Deck deck,
                                             @RequestBody Card card){

        try{
            jwtService.authorize(header);


        }
    }


}
