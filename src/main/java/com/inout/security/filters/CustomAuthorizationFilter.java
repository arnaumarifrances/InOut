package com.inout.security.filters;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final String SECRET = "inout_secret_key";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Get the token
            try {
            var claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            String role = (String) claims.get("roles");

            // Check if is ok
            System.out.println("Validating token for user: " + email);
            System.out.println("Extracted Role: " + role);


            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(email, null,
                            Collections.singletonList(new SimpleGrantedAuthority(role)));

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                System.out.println("Invalid token: " + e.getMessage());
                // If token is not ok, answer 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        chain.doFilter(request, response);  // continue with the filter chain
    }
}