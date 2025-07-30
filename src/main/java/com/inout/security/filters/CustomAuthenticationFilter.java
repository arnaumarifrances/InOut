package com.inout.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Key KEY = Keys.hmacShaKeyFor("clave-que-flipas-en-colores-a-tope-de-power".getBytes(StandardCharsets.UTF_8));
    private final AuthenticationManager authenticationManager;


      //user authentication
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> creds = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    creds.get("email"), creds.get("password")
            );
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException("Invalid login request");
        }
    }
    //If authentication is successful, generate a JWT and return it to the client
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {

        String email = authResult.getName();
        String role = authResult.getAuthorities().iterator().next().getAuthority(); // ROLE_EMPLOYEE o ROLE_ADMIN

        // Check if is generated
        System.out.println("Authentication successful for user: " + email);
        System.out.println("Role: " + role);

        // Generate the JWT
        String token = Jwts.builder()
                .setSubject(email)
                .claim("roles", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day until expiration
                .signWith(SignatureAlgorithm.HS256, KEY) // HS256 is a secret key
                .compact();
        // Send the JWT how answer
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), Map.of("token", token));
    }

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
    }
}
