package com.paylense.application.service;

import com.paylense.application.dto.AccountManagementDto;
import java.util.Optional;

public interface AccountService {

    // User profile management
    Optional<AccountManagementDto.UserProfileDto> getUserProfile(Long userId);
    AccountManagementDto.UserProfileDto updateUserProfile(Long userId, AccountManagementDto.UserProfileDto userProfile);

    // Account settings management
    Optional<AccountManagementDto.AccountSettingsDto> getAccountSettings(Long userId);
    AccountManagementDto.AccountSettingsDto updateAccountSettings(Long userId, AccountManagementDto.AccountSettingsDto accountSettings);

    // Balance inquiries
    AccountManagementDto.BalanceResponseDto getAccountBalance(Long userId);

}