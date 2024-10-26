package ru.artemkliucharov.luodingoserver.luodingo_server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.DeckDTO;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Card;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Deck;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AuthenticationService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.CardService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.DeckService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.JwtService;

import java.util.ArrayList;

@RestController
@RequestMapping("/luodingo/deck")
@AllArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final CardService cardService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    /**
     *
     * @param header bearer jwt token
     * @param deckRequest deckDTO.class object
     * @return
     */
    @PostMapping("/add-deck")
    public ResponseEntity<Object> addDeck(@RequestHeader (value = "Authorization") String header,
                                          @RequestBody DeckDTO deckRequest){
        AppUser appUser;
        try {
            appUser = authenticationService.authenticate(header);
            Deck deck = new Deck(deckRequest.getName());
            deck.setAppUser(appUser);
            deckService.save(deck);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Deck saved");
    }

    /**
     *
     * @param header
     * @param deckRequest
     * @return
     */
    @DeleteMapping("/delete-deck")
    public ResponseEntity<Object> deleteDeck(@RequestHeader(value = "Authorization") String header, @RequestBody DeckDTO deckRequest){
        try{

            authenticationService.authenticate(header);
            deckService.deleteByName(deckRequest.getName());
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
            authenticationService.authenticate(header);
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
            authenticationService.authenticate(header);
            ArrayList<Card> cardsOfDeck = cardService.getCardsFromDeck(deck);
            return ResponseEntity.status(HttpStatus.OK).body(cardsOfDeck);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }


    }

    @DeleteMapping("/delete-card")
    public ResponseEntity<Object> deleteCard(@RequestHeader(value = "Authorization")String header,
                                             @RequestBody Card card){

        try{
            authenticationService.authenticate(header);
            cardService.delete(card);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }


}
