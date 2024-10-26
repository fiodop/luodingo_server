package ru.artemkliucharov.luodingoserver.luodingo_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.JwtAuthenticationResponse;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.SignInRequest;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.SignUpRequest;
import ru.artemkliucharov.luodingoserver.luodingo_server.service.AuthenticationService;

import java.io.File;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final ObjectMapper objectMapper;

    /**
     *
     * @param request SignUpRequest.class
     * @return token, if throw exception return HttpStatus. FORBIDDEN
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest request) throws JsonProcessingException {

        try {
            return ResponseEntity.ok(authenticationService.signUp(request));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(objectMapper.writeValueAsString(e.fillInStackTrace()));
        }
    }

    /**
     *
     * @param request SignInRequest.class
     * @return  token
     * @throws JsonProcessingException
     */
    @GetMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody SignInRequest request) throws JsonProcessingException {
        try {
            return ResponseEntity.ok(authenticationService.signIn(request));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(objectMapper.writeValueAsString(e.fillInStackTrace()));
        }
    }
}
