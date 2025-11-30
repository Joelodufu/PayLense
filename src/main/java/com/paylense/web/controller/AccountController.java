package com.paylense.web.controller;

import com.paylense.application.dto.AccountManagementDto;
import com.paylense.application.service.AccountService;
import com.paylense.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Endpoint to get user profile
    @GetMapping("/profile")
    public ResponseEntity<AccountManagementDto.UserProfileDto> getUserProfile(@AuthenticationPrincipal User user) {
        return accountService.getUserProfile(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to update user profile
    @PutMapping("/profile")
    public ResponseEntity<AccountManagementDto.UserProfileDto> updateUserProfile(@AuthenticationPrincipal User user, @RequestBody AccountManagementDto.UserProfileDto profileData) {
        return ResponseEntity.ok(accountService.updateUserProfile(user.getId(), profileData));
    }

    // Endpoint to get account settings
    @GetMapping("/settings")
    public ResponseEntity<AccountManagementDto.AccountSettingsDto> getAccountSettings(@AuthenticationPrincipal User user) {
        return accountService.getAccountSettings(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to update account settings
    @PutMapping("/settings")
    public ResponseEntity<AccountManagementDto.AccountSettingsDto> updateAccountSettings(@AuthenticationPrincipal User user, @RequestBody AccountManagementDto.AccountSettingsDto settingsData) {
        return ResponseEntity.ok(accountService.updateAccountSettings(user.getId(), settingsData));
    }

    // Endpoint to get balance inquiry
    @GetMapping("/balance")
    public ResponseEntity<AccountManagementDto.BalanceResponseDto> getBalance(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.getAccountBalance(user.getId()));
    }
}
