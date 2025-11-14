package org.example.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.models.LibraryUser;
import org.example.library.repositories.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimplePasswordResetService {

    private final LibraryUserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpiration;

    @Value("${library.notification.email.enabled:true}")
    private boolean emailEnabled;

    public boolean requestPasswordReset(String email) {
        try {
            LibraryUser user = userRepository.findByEmail(email);
            if (user == null) {
                log.warn("Password reset requested for non-existent email: {}", email);
                return false;
            }

            String resetToken = generateResetToken(user.getEmail());
            
            if (emailEnabled) {
                sendPasswordResetEmail(user.getEmail(), resetToken);
            }
            
            log.info("Password reset requested for user: {}", email);
            return true;
        } catch (Exception e) {
            log.error("Error processing password reset request for email: {}", email, e);
            return false;
        }
    }

    public boolean resetPassword(String token, String newPassword) {
        try {
            String email = validateResetToken(token);
            if (email == null) {
                log.warn("Invalid or expired password reset token");
                return false;
            }

            LibraryUser user = userRepository.findByEmail(email);
            if (user == null) {
                log.warn("User not found for password reset: {}", email);
                return false;
            }

            user.setPasswordHash(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            log.info("Password successfully reset for user: {}", email);
            return true;
        } catch (Exception e) {
            log.error("Error resetting password with token: {}", token, e);
            return false;
        }
    }

    private String generateResetToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    private String validateResetToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                log.warn("Password reset token expired");
                return null;
            }

            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error validating password reset token", e);
            return null;
        }
    }

    public boolean isValidResetToken(String token) {
        return validateResetToken(token) != null;
    }

    private void sendPasswordResetEmail(String userEmail, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEmail);
            message.setFrom("library@example.com");
            message.setSubject("Восстановление пароля");
            
            String resetLink = "http://localhost:8080/password-reset?token=" + resetToken;
            String text = String.format(
                "Для восстановления пароля перейдите по ссылке:\n\n" +
                "%s\n\n" +
                "Ссылка действительна в течение 1 часа.\n\n" +
                "Если вы не запрашивали восстановление пароля, проигнорируйте это письмо.",
                resetLink
            );
            
            message.setText(text);
            mailSender.send(message);
            
            log.info("Password reset email sent to: {}", userEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", userEmail, e);
        }
    }
}