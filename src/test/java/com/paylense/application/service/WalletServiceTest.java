package com.paylense.application.service;

import com.paylense.application.entity.User;
import com.paylense.application.entity.Wallet;
import com.paylense.application.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setPhoneNumber("1234567890");
    }

    @Test
    void createWallet_Success() {
        when(walletRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(walletRepository.save(any(Wallet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Wallet wallet = walletService.createWallet(user);

        assertNotNull(wallet);
        assertEquals(user.getPhoneNumber(), wallet.getWalletNumber());
        assertEquals(BigDecimal.ZERO, wallet.getBalance());
        assertEquals(user, wallet.getUser());
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    void createWallet_AlreadyExists() {
        Wallet existingWallet = new Wallet();
        existingWallet.setUser(user);

        when(walletRepository.findByUserId(user.getId())).thenReturn(Optional.of(existingWallet));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            walletService.createWallet(user);
        });

        assertEquals("Wallet already exists for user", exception.getMessage());
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void getWalletByUser_Success() {
        Wallet wallet = new Wallet();
        wallet.setUser(user);

        when(walletRepository.findByUserId(user.getId())).thenReturn(Optional.of(wallet));

        Wallet foundWallet = walletService.getWalletByUser(user.getId());

        assertNotNull(foundWallet);
        assertEquals(wallet, foundWallet);
    }

    @Test
    void getWalletByUser_NotFound() {
        when(walletRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            walletService.getWalletByUser(user.getId());
        });

        assertEquals("Wallet not found for user", exception.getMessage());
    }

}
