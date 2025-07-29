package com.inout.security;

import com.inout.security.filters.CustomAuthenticationFilter;
import com.inout.security.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthFilter =
                new CustomAuthenticationFilter(authenticationManager());
        customAuthFilter.setFilterProcessesUrl("/api/login");


        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login/**", "/users/register").permitAll()
                .requestMatchers(GET, "/shifts/me").hasAnyAuthority("ROLE_EMPLOYEE", "ROLE_ADMIN")
                .requestMatchers(GET, "/shifts").hasAuthority("ROLE_ADMIN") // Only Admin can see all shifts
                .requestMatchers(POST, "/shifts/**").hasAuthority("ROLE_EMPLOYEE")
                .anyRequest().authenticated()
        );

        http.addFilter(customAuthFilter);  // authentication filter
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);  // authorization filter

        return http.build();
    }
}
