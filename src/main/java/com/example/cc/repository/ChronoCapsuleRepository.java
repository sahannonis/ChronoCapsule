package com.example.cc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cc.model.ChronoCapsule;
import com.example.cc.model.User;

@Repository
public interface ChronoCapsuleRepository extends JpaRepository<ChronoCapsule, Long> {
    List<ChronoCapsule> findByUnlockDate(LocalDate unlockDate);
    List<ChronoCapsule> findByTitle(String title);
    List<ChronoCapsule> findByUser(User user);
    Optional<ChronoCapsule> findByIdAndUser(Long id, User user);
}
