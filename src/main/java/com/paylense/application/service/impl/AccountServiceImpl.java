package com.paylense.application.service.impl;

import com.paylense.application.dto.AccountManagementDto;
import com.paylense.application.service.AccountService;
import com.paylense.domain.entity.AccountSettings;
import com.paylense.domain.entity.User;
import com.paylense.domain.entity.Wallet;
import com.paylense.repository.AccountRepository;
import com.paylense.repository.AccountSettingsRepository;
import com.paylense.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountSettingsRepository accountSettingsRepository;
    private final WalletRepository walletRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountSettingsRepository accountSettingsRepository,
                              WalletRepository walletRepository) {
        this.accountRepository = accountRepository;
        this.accountSettingsRepository = accountSettingsRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public Optional<AccountManagementDto.UserProfileDto> getUserProfile(Long userId) {
        return accountRepository.findById(userId).map(this::mapToUserProfileDto);
    }

    @Override
    @Transactional
    public AccountManagementDto.UserProfileDto updateUserProfile(Long userId, AccountManagementDto.UserProfileDto updatedProfile) {
        User user = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(updatedProfile.getFirstName());
        user.setLastName(updatedProfile.getLastName());
        // Email and Phone are usually sensitive and might require verification to change, 
        // but for now we'll allow updating if provided, or keep existing if null/empty logic is needed.
        // Here we assume the DTO provides the desired state.
        // Note: Changing email/phone might conflict with unique constraints if not handled carefully.
        
        // For this iteration, let's only update names to be safe, or assume the DTO is fully populated.
        // Let's update all for now as per requirement.
        if (updatedProfile.getEmail() != null) user.setEmail(updatedProfile.getEmail());
        if (updatedProfile.getPhoneNumber() != null) user.setPhoneNumber(updatedProfile.getPhoneNumber());

        User savedUser = accountRepository.save(user);
        return mapToUserProfileDto(savedUser);
    }

    @Override
    public Optional<AccountManagementDto.AccountSettingsDto> getAccountSettings(Long userId) {
        return accountSettingsRepository.findByUserId(userId)
                .map(this::mapToAccountSettingsDto)
                .or(() -> {
                    // Return default settings if none exist
                    return Optional.of(new AccountManagementDto.AccountSettingsDto());
                });
    }

    @Override
    @Transactional
    public AccountManagementDto.AccountSettingsDto updateAccountSettings(Long userId, AccountManagementDto.AccountSettingsDto updatedSettings) {
        AccountSettings settings = accountSettingsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    AccountSettings newSettings = new AccountSettings();
                    User user = accountRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    newSettings.setUser(user);
                    return newSettings;
                });

        settings.setEmailNotificationsEnabled(updatedSettings.isEmailNotificationsEnabled());
        settings.setSmsNotificationsEnabled(updatedSettings.isSmsNotificationsEnabled());
        settings.setPreferredLanguage(updatedSettings.getPreferredLanguage());

        AccountSettings savedSettings = accountSettingsRepository.save(settings);
        return mapToAccountSettingsDto(savedSettings);
    }

    @Override
    public AccountManagementDto.BalanceResponseDto getAccountBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        
        AccountManagementDto.BalanceResponseDto response = new AccountManagementDto.BalanceResponseDto();
        response.setBalance(wallet.getBalance());
        response.setCurrency(wallet.getCurrency());
        return response;
    }

    private AccountManagementDto.UserProfileDto mapToUserProfileDto(User user) {
        AccountManagementDto.UserProfileDto dto = new AccountManagementDto.UserProfileDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    private AccountManagementDto.AccountSettingsDto mapToAccountSettingsDto(AccountSettings settings) {
        AccountManagementDto.AccountSettingsDto dto = new AccountManagementDto.AccountSettingsDto();
        dto.setEmailNotificationsEnabled(settings.isEmailNotificationsEnabled());
        dto.setSmsNotificationsEnabled(settings.isSmsNotificationsEnabled());
        dto.setPreferredLanguage(settings.getPreferredLanguage());
        return dto;
    }
}
