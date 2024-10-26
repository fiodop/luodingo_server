package ru.artemkliucharov.luodingoserver.luodingo_server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.AppUserDTO;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AppUserService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AuthenticationService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.DeckService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.JwtService;


@RestController
@RequestMapping("/luodingo")
@AllArgsConstructor
public class AppUserController {
    private JwtService jwtService;
    private AppUserService appUserService;
    private DeckService deckService;

    /**
     *
     * @param header jwt token
     * @return AppUserDTO.class object if catch exceptions return HttpStatus.NOT_FOUND, HttpStatus. FORBIDDEN
     */
    @GetMapping("/account")
    public ResponseEntity<Object> account(@RequestHeader (value = "Authorization") String header){
        if(!header.startsWith("Bearer ") || header == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var token = header.substring(7);
        try{
            var username = jwtService.extractUsername(token);
            AppUser appUser = appUserService.getByUsername(username);

            if(appUser != null){
                var decks = deckService.getAllDecksByAppUser(appUser);
                AppUserDTO appUserDTO = new AppUserDTO(appUser.getUsername(), appUser.getEmail(), decks);

                return  ResponseEntity.status(HttpStatus.OK).body(appUserDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
