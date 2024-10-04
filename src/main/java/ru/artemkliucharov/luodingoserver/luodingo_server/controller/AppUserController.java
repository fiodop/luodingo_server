package ru.artemkliucharov.luodingoserver.luodingo_server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AppUserService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AuthenticationService;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.JwtService;

@RestController
@RequestMapping("/luodingo")
@AllArgsConstructor
public class AppUserController {
    private JwtService jwtService;
    private AppUserService appUserService;
    private AuthenticationService authenticationService;
    @GetMapping("/account")
    public ResponseEntity<Object> account(@RequestHeader (value = "Authorization") String header){
        if(!header.startsWith("Bearer ") || header == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var token = header.substring(7);
        System.out.println("token:" + token);
        try{
            var username = jwtService.extractUsername(token);
            System.out.println("username: " + username);
            AppUser appUser = appUserService.getByUsername(username);

            if(appUser != null){
                return  ResponseEntity.ok(appUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
