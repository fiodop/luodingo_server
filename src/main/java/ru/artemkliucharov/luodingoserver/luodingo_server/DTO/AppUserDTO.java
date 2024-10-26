package ru.artemkliucharov.luodingoserver.luodingo_server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Deck;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private String username;
    private String email;
    private List<Deck> decks;
}
