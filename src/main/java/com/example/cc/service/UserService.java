package com.example.cc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.cc.model.User;
import com.example.cc.repository.UserRepository;


@Service
public class UserService {

	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    public User register(User user) {
	        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
	            throw new RuntimeException("Username already exists");
	        }
	        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
	            throw new RuntimeException("Email already registered");
	        }
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return userRepository.save(user);
	    }

	    public Optional<User> findByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }
	    
	    public Optional<User> findByEmail(String email) {
	    	return userRepository.findByEmail(email);
	    }
}