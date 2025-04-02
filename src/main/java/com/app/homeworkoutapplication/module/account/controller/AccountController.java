package com.app.homeworkoutapplication.module.account.controller;

import com.app.homeworkoutapplication.module.account.dto.RegisterRequest;
import com.app.homeworkoutapplication.module.account.service.AccountService;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Authentication")
public class AccountController {

    private final AccountService accountService;

    private final CurrentUserUtil currentUserUtil;

    public AccountController(AccountService accountService, CurrentUserUtil currentUserUtil) {
        this.accountService = accountService;
        this.currentUserUtil = currentUserUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        User user = accountService.register(registerRequest, "USER");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/activate")
    public ResponseEntity<Void> activateUser(@RequestParam("email") String email) {
        accountService.activateUser(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update-avatar")
    public ResponseEntity<Void> updateAvatar(@RequestParam("file") MultipartFile file) {
        accountService.updateAvatar(file);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestParam("newPass") String password) {
        accountService.updatePassword(password);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/account")
    public ResponseEntity<User> getCurrentUser() {
        User user = currentUserUtil.getCurrentUser();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/account")
    public ResponseEntity<User> updateAccount(@RequestBody User user) {
        User result = accountService.updateAccount(user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/forgot-password/{username}")
    public ResponseEntity<Void> forgotPassword(@PathVariable("username") String username) {
        accountService.forgotPassword(username);
        return ResponseEntity.noContent().build();
    }
}
