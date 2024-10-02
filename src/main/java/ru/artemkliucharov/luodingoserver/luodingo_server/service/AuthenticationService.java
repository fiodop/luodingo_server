package ru.artemkliucharov.luodingoserver.luodingo_server.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.JwtAuthenticationResponse;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.SignInRequest;
import ru.artemkliucharov.luodingoserver.luodingo_server.DTO.SignUpRequest;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AppUserService appUserService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /**
     * Registration user
     *
     * @param request user's data
     * @return jwt
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request){
        var user = AppUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        appUserService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }


    public JwtAuthenticationResponse signIn(SignInRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = appUserService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }


}
