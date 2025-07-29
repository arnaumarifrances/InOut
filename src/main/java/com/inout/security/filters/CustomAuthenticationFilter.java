package com.inout.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final String SECRET = "inout_secret_key"; // This can be changed to a more secure password

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
                .signWith(SignatureAlgorithm.HS256, SECRET) // HS256 is a secret key
                .compact();
        // Send the JWT how answer
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), Map.of("token", token));
    }
}
