package ru.artemkliucharov.luodingoserver.luodingo_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.JwtAuthenticationResponse;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.SignInRequest;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.SignUpRequest;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request){
        return authenticationService.signUp(request);
    }
    @GetMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request){
        return authenticationService.signIn(request);
    }
}
