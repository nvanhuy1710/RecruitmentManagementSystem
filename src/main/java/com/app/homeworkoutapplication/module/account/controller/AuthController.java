package com.app.homeworkoutapplication.module.account.controller;

import com.app.homeworkoutapplication.module.account.dto.LoginDTO;
import com.app.homeworkoutapplication.module.account.dto.TokenRefreshRequest;
import com.app.homeworkoutapplication.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtil.createToken(loginDTO.getUsername(), false);
            String refreshToken = jwtUtil.createToken(loginDTO.getUsername(), true);

            Map<String, Object> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            tokens.put("expiredIn", jwtUtil.getAccessTokenExpired());

            return ResponseEntity.ok(tokens);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        String username = jwtUtil.extractUsername(refreshToken);

        if (jwtUtil.isTokenValid(refreshToken, username)) {
            String newAccessToken = jwtUtil.createToken(username, false);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(403).body("Invalid refresh token or expired.");
        }
    }
}

