package com.paylense.application.service;

import com.paylense.application.dto.AccountManagementDto;
import com.paylense.application.service.impl.AccountServiceImpl;
import com.paylense.domain.entity.AccountSettings;
import com.paylense.domain.entity.User;
import com.paylense.domain.entity.Wallet;
import com.paylense.repository.AccountRepository;
import com.paylense.repository.AccountSettingsRepository;
import com.paylense.repository.WalletRepository;
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
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountSettingsRepository accountSettingsRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private User user;
    private AccountSettings settings;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");

        settings = new AccountSettings();
        settings.setId(1L);
        settings.setUser(user);
        settings.setEmailNotificationsEnabled(true);

        wallet = new Wallet();
        wallet.setId(1L);
        wallet.setUser(user);
        wallet.setBalance(new BigDecimal("1000.00"));
        wallet.setCurrency("NGN");
    }

    @Test
    void getUserProfile_Success() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<AccountManagementDto.UserProfileDto> result = accountService.getUserProfile(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void updateUserProfile_Success() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(User.class))).thenReturn(user);

        AccountManagementDto.UserProfileDto updateDto = new AccountManagementDto.UserProfileDto();
        updateDto.setFirstName("Jane");
        updateDto.setLastName("Doe");

        AccountManagementDto.UserProfileDto result = accountService.updateUserProfile(1L, updateDto);

        assertEquals("Jane", result.getFirstName());
        verify(accountRepository).save(any(User.class));
    }

    @Test
    void getAccountBalance_Success() {
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(wallet));

        AccountManagementDto.BalanceResponseDto result = accountService.getAccountBalance(1L);

        assertEquals(new BigDecimal("1000.00"), result.getBalance());
        assertEquals("NGN", result.getCurrency());
    }
}
