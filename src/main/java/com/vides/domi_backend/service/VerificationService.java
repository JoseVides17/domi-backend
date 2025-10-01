package com.vides.domi_backend.service;

import com.vides.domi_backend.model.User;
import com.vides.domi_backend.model.VerificationToken;
import com.vides.domi_backend.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken vToken = new VerificationToken();
        vToken.setToken(token);
        vToken.setUser(user);
        vToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        return verificationTokenRepository.save(vToken);
    }

}
