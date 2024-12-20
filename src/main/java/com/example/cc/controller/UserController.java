package com.example.cc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cc.model.User;
import com.example.cc.security.JwtResponse;
import com.example.cc.security.JwtUtils;
import com.example.cc.service.UserService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Authenticate the user using the AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            
            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Generate JWT token using JwtUtils
            String jwtToken = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());

            // Return the generated token in the response
            return ResponseEntity.ok(new JwtResponse(jwtToken));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
     
    @GetMapping("/email")
    public ResponseEntity<String> getEmailOfLoggedInUser() {
        try {
            // Get the username of the logged-in user from Security Context
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // Fetch the user by username
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Return the user's email
            return ResponseEntity.ok(user.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to retrieve email: " + e.getMessage());
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }
}
