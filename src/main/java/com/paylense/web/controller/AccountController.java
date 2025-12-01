package com.paylense.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.paylense.web.model.User;

@Tag(name = "Account Controller", description = "APIs for managing user accounts")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Operation(summary = "Get account details", description = "Retrieve details of the authenticated user's account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account details retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication failed or missing"),
        @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/me")
    public String getAccountDetails(
        @Parameter(description = "Authenticated user", required = true)
        @AuthenticationPrincipal User user) {
        // Implementation here
        return "Account details for user: " + user.getUsername();
    }

    @Operation(summary = "Update account information", description = "Update the authenticated user's account information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication failed or missing"),
        @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PutMapping("/me")
    public String updateAccount(
        @Parameter(description = "Authenticated user", required = true)
        @AuthenticationPrincipal User user,
        @RequestBody String accountUpdateRequest) {
        // Implementation here
        return "Account updated for user: " + user.getUsername();
    }

    @Operation(summary = "Delete account", description = "Delete the authenticated user's account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication failed or missing"),
        @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @DeleteMapping("/me")
    public void deleteAccount(
        @Parameter(description = "Authenticated user", required = true)
        @AuthenticationPrincipal User user) {
        // Implementation here
    }

}