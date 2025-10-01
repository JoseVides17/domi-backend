package com.vides.domi_backend.service;

import com.vides.domi_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user, String token) {
        String link = "http://localhost:8080/auth/verify?token=" + token;
        String subject = "Confirma tu registro";
        String message = "Hola " + user.getNombre() + ",\n\n" +
                "Por favor confirma tu cuenta haciendo clic en el siguiente enlace:\n" + link;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

}
