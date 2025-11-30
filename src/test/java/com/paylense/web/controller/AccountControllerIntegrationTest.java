package com.paylense.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paylense.repository.UserRepository;
import com.paylense.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PROFILE_URL = "/api/account/profile";
    private static final String SETTINGS_URL = "/api/account/settings";
    private static final String BALANCE_URL = "/api/account/balance";

    @BeforeEach
    public void setup() {
        // Setup test data if needed
    }

    @Test
    @WithMockUser
    public void getUserProfile_Success() throws Exception {
        mockMvc.perform(get(PROFILE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", not(emptyString())))
                .andExpect(jsonPath("$.email", containsString("@")));
    }

    @Test
    @WithMockUser(username = "nonexistentuser")
    public void getUserProfile_NotFound() throws Exception {
        mockMvc.perform(get(PROFILE_URL))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void updateUserProfile_Success() throws Exception {
        var updateProfile = new java.util.HashMap<String, Object>();
        updateProfile.put("email", "newemail@example.com");
        updateProfile.put("fullName", "New Full Name");

        mockMvc.perform(put(PROFILE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("newemail@example.com")))
                .andExpect(jsonPath("$.fullName", is("New Full Name")));
    }

    @Test
    @WithMockUser
    public void getAccountSettings_Success() throws Exception {
        mockMvc.perform(get(SETTINGS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationsEnabled", notNullValue()));
    }

    @Test
    @WithMockUser
    public void updateAccountSettings_Success() throws Exception {
        var updateSettings = new java.util.HashMap<String, Object>();
        updateSettings.put("notificationsEnabled", false);

        mockMvc.perform(put(SETTINGS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSettings)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationsEnabled", is(false)));
    }

    @Test
    @WithMockUser
    public void getBalance_Success() throws Exception {
        mockMvc.perform(get(BALANCE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", greaterThanOrEqualTo(0)));
    }
}
