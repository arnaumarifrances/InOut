package com.inout.security.filters;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import org.springframework.security.authentication.AuthenticationManager;

import static org.mockito.Mockito.*;

class CustomAuthenticationFilterTest {

    private static final Key KEY = Keys.hmacShaKeyFor("clave-que-flipas-en-colores-a-tope-de-power".getBytes(StandardCharsets.UTF_8));

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationManager authenticationManager;  // Mock AuthenticationManager

    private CustomAuthenticationFilter customAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Pass AuthenticationManager to the filter constructor
        customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
    }

    @Test
    void testDoFilterInternal_WithValidToken() throws Exception {
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .claim("roles", "ROLE_USER")
                .signWith(KEY)
                .compact();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        // Execute the filter
        customAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Ensure that the filter chain continues as expected
        verify(filterChain, times(1)).doFilter(request, response); // Check if doFilter was invoked
    }

    @Test
    void testDoFilterInternal_WithInvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");

        // Execute the filter
        customAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the response status is set to 401 Unauthorized
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Ensure status 401 is set
        verify(filterChain, times(0)).doFilter(request, response); // Ensure the filter chain is not executed
    }
}
