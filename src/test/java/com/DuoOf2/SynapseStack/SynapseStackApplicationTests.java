package com.DuoOf2.SynapseStack;

import com.DuoOf2.SynapseStack.Security.JwtFilter;
import com.DuoOf2.SynapseStack.Security.JwtService;
import com.DuoOf2.SynapseStack.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class SynapseStackApplicationTests {

	@MockitoBean
	private UserRepository userRepository;

	@MockitoBean
	private JwtService jwtService;

	@MockitoBean
	private JwtFilter jwtFilter;

	@Test
	void contextLoads() {
	}

}
