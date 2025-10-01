package com.vides.domi_backend.repository;

import com.vides.domi_backend.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    <T> VerificationToken findByToken(String token);
}
