package ru.artemkliucharov.luodingoserver.luodingo_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Card;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Deck;

import java.util.ArrayList;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    public ArrayList<Deck> getAllByAppUser(AppUser appUser);
    public void deleteByName(String name);
}
