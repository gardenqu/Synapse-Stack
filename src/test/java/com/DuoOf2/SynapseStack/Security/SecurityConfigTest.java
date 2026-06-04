package com.DuoOf2.SynapseStack.Security;

import com.DuoOf2.SynapseStack.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JwtService jwtService;

    // No @MockitoBean on JwtFilter — let the real one run

    @Test
    void authEndpoints_shouldBePublic() throws Exception {
        mockMvc.perform(post("/api/auth/login"))
                .andExpect(status().isNotFound()); // gets past security, no controller = 404
    }

    @Test
    void protectedEndpoint_withNoToken_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/decks"))
                .andExpect(status().isUnauthorized()); // will pass once SecurityFilterChain is fixed
    }

    @Test
    void protectedEndpoint_withInvalidToken_shouldReturn401() throws Exception {
        // Make JwtService throw when it sees a bad token
        when(jwtService.extractUserId("garbage.token.here"))
                .thenThrow(new io.jsonwebtoken.MalformedJwtException("bad token"));

        mockMvc.perform(get("/api/decks")
                        .header("Authorization", "Bearer garbage.token.here"))
                .andExpect(status().isUnauthorized());
    }
}