package com.vides.domi_backend.service;

import com.vides.domi_backend.dto.UserProfileDto;
import com.vides.domi_backend.dto.request.UpdateProfileRequest;
import com.vides.domi_backend.model.User;
import com.vides.domi_backend.model.VerificationToken;
import com.vides.domi_backend.repository.UserRepository;
import com.vides.domi_backend.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public UserProfileDto getCurrentUserProfile(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return toDto(user);
    }

    @Transactional
    public UserProfileDto updateCurrentUserProfile(UpdateProfileRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setNombre(request.getNombre());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setFechaNacimiento(request.getFechaNacimiento());
        user.setProfileUrl(request.getProfileUrl());

        userRepository.save(user);
        return toDto(user);
    }

    private UserProfileDto toDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setNombre(user.getNombre());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setFechaNacimiento(user.getFechaNacimiento());
        dto.setProfileUrl(user.getProfileUrl());
        return dto;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
