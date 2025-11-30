package com.paylense.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paylense.data.model.Transaction;
import com.paylense.data.model.User;
import com.paylense.data.model.Wallet;
import com.paylense.data.repository.UserRepository;
import com.paylense.data.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User sender;
    private User receiver;
    private Wallet senderWallet;
    private Wallet receiverWallet;

    @BeforeEach
    public void setup() {
        // Clean up repositories
        walletRepository.deleteAll();
        userRepository.deleteAll();

        // Create sender user and wallet
        sender = new User();
        sender.setUsername("senderUser");
        sender.setEmail("sender@example.com");
        sender = userRepository.save(sender);

        senderWallet = new Wallet();
        senderWallet.setUser(sender);
        senderWallet.setBalance(new BigDecimal("1000.00"));
        senderWallet = walletRepository.save(senderWallet);

        // Create receiver user and wallet
        receiver = new User();
        receiver.setUsername("receiverUser");
        receiver.setEmail("receiver@example.com");
        receiver = userRepository.save(receiver);

        receiverWallet = new Wallet();
        receiverWallet.setUser(receiver);
        receiverWallet.setBalance(new BigDecimal("500.00"));
        receiverWallet = walletRepository.save(receiverWallet);
    }

    @Test
    @WithMockUser(username = "senderUser")
    public void transfer_Success() throws Exception {
        var transferRequest = new TransferRequest(receiver.getUsername(), new BigDecimal("100.00"));

        mockMvc.perform(post("/api/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.senderUsername", is(sender.getUsername())))
                .andExpect(jsonPath("$.receiverUsername", is(receiver.getUsername())))
                .andExpect(jsonPath("$.amount", is(100.00)))
                .andExpect(jsonPath("$.transactionId", notNullValue()));
    }

    @Test
    @WithMockUser(username = "senderUser")
    public void transfer_InsufficientFunds() throws Exception {
        var transferRequest = new TransferRequest(receiver.getUsername(), new BigDecimal("2000.00"));

        mockMvc.perform(post("/api/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Insufficient funds")));
    }

    @Test
    @WithMockUser(username = "senderUser")
    public void transfer_SelfTransfer() throws Exception {
        var transferRequest = new TransferRequest(sender.getUsername(), new BigDecimal("100.00"));

        mockMvc.perform(post("/api/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Cannot transfer to self")));
    }

    @Test
    @WithMockUser(username = "senderUser")
    public void transfer_ReceiverNotFound() throws Exception {
        var transferRequest = new TransferRequest("nonexistentUser", new BigDecimal("100.00"));

        mockMvc.perform(post("/api/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Receiver not found")));
    }

    @Test
    @WithMockUser(username = "senderUser")
    public void getHistory_Success() throws Exception {
        // Perform a transfer to have a transaction in history
        var transferRequest = new TransferRequest(receiver.getUsername(), new BigDecimal("50.00"));

        mockMvc.perform(post("/api/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/transactions/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].senderUsername", is(sender.getUsername())));
    }

    // Helper DTO for transfer request
    static class TransferRequest {
        private String receiverUsername;
        private BigDecimal amount;

        public TransferRequest() {}

        public TransferRequest(String receiverUsername, BigDecimal amount) {
            this.receiverUsername = receiverUsername;
            this.amount = amount;
        }

        public String getReceiverUsername() {
            return receiverUsername;
        }

        public void setReceiverUsername(String receiverUsername) {
            this.receiverUsername = receiverUsername;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
