package ru.artemkliucharov.luodingoserver.luodingo_server.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
import ru.artemkliucharov.luodingoserver.luodingo_server.repository.AppUserRepository;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;


    /**
     * Saving user
     *
     * @param appUser user
     * @return saved user
     */
    public AppUser save(AppUser appUser){
        return appUserRepository.save(appUser);
    }


    /**
     * Creating user
     *
     * @param appUser user
     * @return created user
     */
    public AppUser create(AppUser appUser){
        if(appUserRepository.existsByUsername(appUser.getUsername())){
            throw new RuntimeException("User with this username exist");
        }

        if (appUserRepository.existsByEmail(appUser.getEmail())){
            throw new RuntimeException("User with this email is exist");
        }

        return save(appUser);
    }

    /**
     * Getting user by username
     *
     * @param username username
     * @return user
     */
    public AppUser getByUsername(String username){
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Getting user by username
     *
     * need for Sring security
     * @return user
     */
    public UserDetailsService userDetailsService(){
        return this::getByUsername;
    }
    public AppUser getCurrentUser(){
        var username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return getByUsername(username);
    }
}
