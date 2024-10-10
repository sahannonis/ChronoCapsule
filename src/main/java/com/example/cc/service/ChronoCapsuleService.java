package com.example.cc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cc.model.ChronoCapsule;
import com.example.cc.model.User;
import com.example.cc.repository.ChronoCapsuleRepository;
import com.example.cc.repository.UserRepository;

@Service
public class ChronoCapsuleService {
	 	
	 @Autowired
	    private ChronoCapsuleRepository capsuleRepository;

	    @Autowired
	    private UserRepository userRepository;

	    public ChronoCapsule addCapsule(String username, ChronoCapsule capsule) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        capsule.setUser(user);
	        return capsuleRepository.save(capsule);
	    }

	    public List<ChronoCapsule> getAllCapsulesForUser(String username) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        return capsuleRepository.findByUser(user);
	    }

	    public ChronoCapsule updateCapsule(String username, Long capsuleId, ChronoCapsule updatedCapsule) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        ChronoCapsule existingCapsule = capsuleRepository.findByIdAndUser(capsuleId, user)
	                .orElseThrow(() -> new RuntimeException("Capsule not found or not authorized"));

	        existingCapsule.setTitle(updatedCapsule.getTitle());
	        existingCapsule.setDescription(updatedCapsule.getDescription());
	        existingCapsule.setUnlockDate(updatedCapsule.getUnlockDate());

	        return capsuleRepository.save(existingCapsule);
	    }

	    public void deleteCapsule(String username, Long capsuleId) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        ChronoCapsule existingCapsule = capsuleRepository.findByIdAndUser(capsuleId, user)
	                .orElseThrow(() -> new RuntimeException("Capsule not found or not authorized"));

	        capsuleRepository.delete(existingCapsule);
	    }

	    public List<ChronoCapsule> getCapsulesByTitle(String title) {
	        return capsuleRepository.findByTitle(title);
	    }

	    public ChronoCapsule updateCapsule(ChronoCapsule capsule) {
	        return capsuleRepository.save(capsule);
	    }

	    public void deleteCapsule(ChronoCapsule capsule) {
	        capsuleRepository.delete(capsule);
	    }
}
