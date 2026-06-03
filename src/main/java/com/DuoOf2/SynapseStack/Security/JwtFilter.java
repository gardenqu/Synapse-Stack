package com.DuoOf2.SynapseStack.Security;

import com.DuoOf2.SynapseStack.Entity.AppUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;
    private final AppUserRepository userRepository;

    public JwtFilter(JwtService jwtService, AppUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // No token — skip authentication, let Spring Security handle it
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // Reject obviously invalid tokens early
        if (token.isBlank() || token.equals("null")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String userId = jwtService.extractUserId(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtService.isTokenValid(token)) {
                    AppUser user = userRepository.findById(userId).orElse(null);

                    if (user != null) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        user.getAuthorities() // pulls roles from the user entity
                                );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        log.warn("Valid token but user not found for id: {}", userId);
                    }
                }
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.warn("Expired JWT token for request: {}", request.getRequestURI());
            SecurityContextHolder.clearContext();
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            log.warn("Malformed JWT token for request: {}", request.getRequestURI());
            SecurityContextHolder.clearContext();
        } catch (io.jsonwebtoken.security.SecurityException e) {
            log.warn("Invalid JWT signature for request: {}", request.getRequestURI());
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            log.error("Unexpected error processing JWT for request: {}", request.getRequestURI(), e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }


}
