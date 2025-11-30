package com.paylense.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication Controller", description = "APIs for user authentication and authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Operation(summary = "User login", description = "Authenticate user with username and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful login"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // login logic
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "User registration", description = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request - invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // registration logic
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "User logout", description = "Logout the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful logout"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // logout logic
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get current user details", description = "Retrieve details of the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        // get current user logic
        return ResponseEntity.ok().build();
    }

}
