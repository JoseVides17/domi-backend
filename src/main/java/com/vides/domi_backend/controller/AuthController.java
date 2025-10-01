package com.vides.domi_backend.controller;

import com.vides.domi_backend.dto.UserLoginDTO;
import com.vides.domi_backend.dto.UserRegisterDTO;
import com.vides.domi_backend.model.User;
import com.vides.domi_backend.model.VerificationToken;
import com.vides.domi_backend.repository.UserRepository;
import com.vides.domi_backend.repository.VerificationTokenRepository;
import com.vides.domi_backend.security.JwtUtil;
import com.vides.domi_backend.service.EmailService;
import com.vides.domi_backend.service.UserService;
import com.vides.domi_backend.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final VerificationService verificationService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO dto) {
        User user = User.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rol(User.Rol.USER)
                .build();

        userService.register(user);

        VerificationToken vt = verificationService.createVerificationToken(user);
        verificationTokenRepository.save(vt);
        emailService.sendVerificationEmail(user, vt.getToken());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuario registrado correctamente");
        response.put("email", user.getEmail());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO dto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
            User user = userRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!user.isEnabled()) {
                return ResponseEntity.status(403).body("Debes verificar tu correo antes de iniciar sesión");
            }
            String token = jwtUtil.generateToken(dto.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRol());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

}
