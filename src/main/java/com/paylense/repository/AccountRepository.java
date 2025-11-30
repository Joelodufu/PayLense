package com.paylense.repository;

import com.paylense.model.UserProfile;
import com.paylense.model.AccountSettings;
import java.util.Optional;

public interface AccountRepository {

    // Fetch user profile by user ID
    Optional<UserProfile> findUserProfileById(String userId);

    // Update user profile
    void updateUserProfile(UserProfile userProfile);

    // Fetch account settings by user ID
    Optional<AccountSettings> findAccountSettingsByUserId(String userId);

    // Update account settings
    void updateAccountSettings(AccountSettings accountSettings);

}