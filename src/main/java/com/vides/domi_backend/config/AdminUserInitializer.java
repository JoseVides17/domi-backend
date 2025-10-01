package com.vides.domi_backend.config;

import com.vides.domi_backend.model.User;
import com.vides.domi_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "admin@miapp.com";
            if (userRepository.findByEmail(email).isEmpty()) {
                User admin = new User();
                admin.setEmail(email);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol(User.Rol.ADMIN);
                admin.setEnabled(true);
                userRepository.save(admin);
                System.out.println("✅ Usuario admin creado: " + email);
            }
        };
    }

}
