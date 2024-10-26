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
import ru.artemkliucharov.luodingoserver.luodingo_server.exeption.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AppUserService appUserService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /**
     * Authorize user by token
     * @param header authorization header with token
     * @return AppUser.class object from db
     * @throws UserNotFoundException
     */
    public AppUser authenticate(String header){
        if(!header.startsWith("Bearer ") || header == null){
            throw new RuntimeException("Unauthorized");
        }
        var token = header.substring(7);
        try{
            var username = jwtService.extractUsername(token);
            AppUser appUser = appUserService.getByUsername(username);

            if(appUser != null){
                return  appUser;
            } else {
                throw new UserNotFoundException("User with username " + username + " not found");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Unauthorized");
        }
    }
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
        try {
            appUserService.create(user);
        } catch (RuntimeException e){
            throw new RuntimeException("User with this username exist");
        }

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
