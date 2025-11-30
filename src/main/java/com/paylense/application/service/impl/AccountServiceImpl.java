package com.paylense.application.service.impl;

import com.paylense.application.service.AccountService;
import com.paylense.application.model.UserProfile;
import com.paylense.application.model.AccountSettings;
import com.paylense.application.repository.UserProfileRepository;
import com.paylense.application.repository.AccountSettingsRepository;
import com.paylense.application.repository.AccountBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountSettingsRepository accountSettingsRepository;

    @Autowired
    private AccountBalanceRepository accountBalanceRepository;

    @Override
    public Optional<UserProfile> getUserProfile(Long userId) {
        return userProfileRepository.findById(userId);
    }

    @Override
    public UserProfile updateUserProfile(Long userId, UserProfile updatedProfile) {
        return userProfileRepository.findById(userId).map(profile -> {
            profile.setFirstName(updatedProfile.getFirstName());
            profile.setLastName(updatedProfile.getLastName());
            profile.setEmail(updatedProfile.getEmail());
            profile.setPhoneNumber(updatedProfile.getPhoneNumber());
            // Add other fields as necessary
            return userProfileRepository.save(profile);
        }).orElseThrow(() -> new RuntimeException("User profile not found"));
    }

    @Override
    public Optional<AccountSettings> getAccountSettings(Long userId) {
        return accountSettingsRepository.findByUserId(userId);
    }

    @Override
    public AccountSettings updateAccountSettings(Long userId, AccountSettings updatedSettings) {
        return accountSettingsRepository.findByUserId(userId).map(settings -> {
            settings.setNotificationEnabled(updatedSettings.isNotificationEnabled());
            settings.setPrivacyLevel(updatedSettings.getPrivacyLevel());
            // Add other settings fields as necessary
            return accountSettingsRepository.save(settings);
        }).orElseThrow(() -> new RuntimeException("Account settings not found"));
    }

    @Override
    public Double getAccountBalance(Long userId) {
        return accountBalanceRepository.findBalanceByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account balance not found"));
    }
}
