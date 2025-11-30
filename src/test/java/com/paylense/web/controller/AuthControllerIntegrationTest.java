package com.paylense.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paylense.web.model.User;
import com.paylense.web.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Clean up before each test
        userRepository.deleteAll();
    }

    @AfterEach
    public void cleanup() {
        // Clean up after each test
        userRepository.deleteAll();
    }

    @Test
    public void register_Success() throws Exception {
        String jsonRequest = "{" +
                "\"email\":\"testuser@example.com\"," +
                "\"password\":\"password123\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("token");
    }

    @Test
    public void register_EmailAlreadyExists() throws Exception {
        // Pre-insert a user with the email
        User existingUser = new User();
        existingUser.setEmail("duplicate@example.com");
        existingUser.setPassword("password123");
        userRepository.save(existingUser);

        String jsonRequest = "{" +
                "\"email\":\"duplicate@example.com\"," +
                "\"password\":\"password123\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void login_Success() throws Exception {
        // Pre-insert a user for login
        User user = new User();
        user.setEmail("loginuser@example.com");
        user.setPassword("password123");
        userRepository.save(user);

        String jsonRequest = "{" +
                "\"email\":\"loginuser@example.com\"," +
                "\"password\":\"password123\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("token");
    }

    @Test
    public void login_InvalidCredentials() throws Exception {
        String jsonRequest = "{" +
                "\"email\":\"wronguser@example.com\"," +
                "\"password\":\"wrongpassword\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/test"))
                .andExpect(status().isOk());
    }
}
