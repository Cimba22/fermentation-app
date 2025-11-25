package org.cimba.backend.auth;


import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cimba.backend.email.EmailService;
import org.cimba.backend.email.EmailTemplateName;
import org.cimba.backend.role.RoleRepository;
import org.cimba.backend.security.JwtService;
import org.cimba.backend.user.Token;
import org.cimba.backend.user.TokenRepository;
import org.cimba.backend.user.User;
import org.cimba.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


    public void register(RegistrationRequest request) throws MessagingException {
        var role = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER is not initialized"));
        var user = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPasswordHash()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(role))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getLogin(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation");
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateAndSaveActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(180))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateAndSaveActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPasswordHash()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("Login", user.getLogin());
        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

//    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token saveToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(saveToken.getExpiresAt())){
            sendValidationEmail(saveToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent.");
        }
        var user = userRepository.findById(saveToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        saveToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(saveToken);
    }
}
