package com.example.cc.controller;

import com.example.cc.model.ChronoCapsule;
import com.example.cc.security.JwtUtils;
import com.example.cc.service.ChronoCapsuleService;
import com.example.cc.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/capsules")
public class ChronoCapsuleController {

    @Autowired
    private ChronoCapsuleService capsuleService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    // Add a new capsule
    @PostMapping("/create")
    public ResponseEntity<?> createCapsule(HttpServletRequest request, @RequestBody ChronoCapsule capsule) {
        String username = extractUsernameFromRequest(request);
        if (username == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        capsuleService.addCapsule(username, capsule);
        return ResponseEntity.ok("Capsule created successfully");
    }

    // Get all capsules for the logged-in user
    @GetMapping("/my-capsules")
    public ResponseEntity<?> getAllCapsules(HttpServletRequest request) {
        String username = extractUsernameFromRequest(request);
        if (username == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<ChronoCapsule> capsules = capsuleService.getAllCapsulesForUser(username);
        return ResponseEntity.ok(capsules);
    }

    // Update a capsule
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCapsule(HttpServletRequest request, @PathVariable Long id, @RequestBody ChronoCapsule updatedCapsule) {
        String username = extractUsernameFromRequest(request);
        if (username == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        capsuleService.updateCapsule(username, id, updatedCapsule);
        return ResponseEntity.ok("Capsule updated successfully");
    }

    // Delete a capsule
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCapsule(HttpServletRequest request, @PathVariable Long id) {
        String username = extractUsernameFromRequest(request);
        if (username == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        capsuleService.deleteCapsule(username, id);
        return ResponseEntity.ok("Capsule deleted successfully");
    }

    // Helper method to extract username from JWT token
    private String extractUsernameFromRequest(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authorizationHeader.substring(7);
        return jwtUtils.extractUsername(token);
    }
}
