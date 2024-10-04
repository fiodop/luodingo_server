package ru.artemkliucharov.luodingoserver.luodingo_server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.Card;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AppUserService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.CardService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.JwtService;

@RestController
@RequestMapping("/luodingo")
@AllArgsConstructor
public class CardController {
        private final JwtService jwtService;
        private final AppUserService appUserService;
        private final CardService cardService;


}
