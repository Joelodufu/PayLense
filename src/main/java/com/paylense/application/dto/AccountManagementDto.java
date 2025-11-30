package com.paylense.application.dto;

import lombok.Data;
import java.math.BigDecimal;

public class AccountManagementDto {

    @Data
    public static class UserProfileDto {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
    }

    @Data
    public static class AccountSettingsDto {
        private boolean emailNotificationsEnabled;
        private boolean smsNotificationsEnabled;
        private String preferredLanguage;
    }

    @Data
    public static class BalanceResponseDto {
        private BigDecimal balance;
        private String currency;
    }
}