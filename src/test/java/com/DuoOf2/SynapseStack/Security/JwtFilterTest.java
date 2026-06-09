package com.DuoOf2.SynapseStack.Security;

import com.DuoOf2.SynapseStack.entity.User;
import com.DuoOf2.SynapseStack.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {

    @Mock private JwtService jwtService;
    @Mock private UserRepository userRepository;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void noAuthHeader_shouldContinueFilterChain() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void wrongPrefix_shouldSkipAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic sometoken");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void blankToken_shouldSkipAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer ");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void malformedToken_shouldClearContextAndContinue() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer bad.token.here");
        when(jwtService.extractUserId("bad.token.here"))
                .thenThrow(new io.jsonwebtoken.MalformedJwtException("bad token"));
        when(request.getRequestURI()).thenReturn("/api/decks");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void validToken_butUserNotFound_shouldNotAuthenticate() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");
        when(jwtService.extractUserId("validtoken")).thenReturn("user-123");
        when(jwtService.isTokenValid("validtoken")).thenReturn(true);
        when(userRepository.findById("user-123")).thenReturn(Optional.empty());

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void validToken_withValidUser_shouldSetAuthentication() throws Exception {
        User mockUser = new User("John", "Doe", "john@test.com", "hash");

        when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");
        when(jwtService.extractUserId("validtoken")).thenReturn("user-123");
        when(jwtService.isTokenValid("validtoken")).thenReturn(true);
        when(userRepository.findById("user-123")).thenReturn(Optional.of(mockUser));

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}