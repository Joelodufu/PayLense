package com.paylense.application.service;

import com.paylense.application.model.UserProfile;
import com.paylense.application.model.AccountSettings;
import java.math.BigDecimal;

public interface AccountService {

    // User profile management
    UserProfile getUserProfile(String userId);
    void updateUserProfile(String userId, UserProfile userProfile);

    // Account settings management
    AccountSettings getAccountSettings(String userId);
    void updateAccountSettings(String userId, AccountSettings accountSettings);

    // Balance inquiries
    BigDecimal getAccountBalance(String userId);

}
