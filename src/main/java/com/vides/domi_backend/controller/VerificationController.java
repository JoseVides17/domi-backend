package com.vides.domi_backend.controller;


import com.vides.domi_backend.model.User;
import com.vides.domi_backend.model.VerificationToken;
import com.vides.domi_backend.repository.UserRepository;
import com.vides.domi_backend.repository.VerificationTokenRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class VerificationController {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public VerificationController(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/auth/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        VerificationToken vToken = verificationTokenRepository.findByToken((token)
                .describeConstable().orElseThrow(() -> new RuntimeException("Token inválido")));

        if (vToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expirado");
        }

        User user = vToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(vToken);

        return ResponseEntity.ok("Cuenta verificada correctamente");
    }

}
