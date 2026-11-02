package bhy880.workout_tracker.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import bhy880.workout_tracker.domain.AppUser;
import bhy880.workout_tracker.domain.AppUserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

        return User
        .withUsername(appUser.getUsername())
        .password(appUser.getPasswordHash())
        .roles(appUser.getRole())
        .build();
        

    }
    

}
