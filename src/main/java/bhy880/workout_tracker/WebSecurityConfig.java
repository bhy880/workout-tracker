package bhy880.workout_tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity 
@EnableMethodSecurity(securedEnabled = true)  
public class WebSecurityConfig {

   @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            // static files and the login page are open to everyone
            .requestMatchers("/css/**", "/login", "/signup").permitAll()
            // read-only pages are open to everyone (GET only)
            .requestMatchers(HttpMethod.GET, "/", "/workouts", "/workout/**",
             "/api/workouts", "/api/workouts/**").permitAll()
            // everything else (add/edit) requires login
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/myworkouts", true)
            .permitAll()
        )
        .oauth2Login(oauth -> oauth
            .loginPage("/login")
            .defaultSuccessUrl("/myworkouts", true)
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/workouts")
            .permitAll()
        );
    return http.build();
}
}





