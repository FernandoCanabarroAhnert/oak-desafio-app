package com.fernandocanabarro.oak_desafio.services.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fernandocanabarro.oak_desafio.dtos.ActivateAccountDTO;
import com.fernandocanabarro.oak_desafio.dtos.EmailDTO;
import com.fernandocanabarro.oak_desafio.dtos.PasswordRecoverDTO;
import com.fernandocanabarro.oak_desafio.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.oak_desafio.models.ActivationCode;
import com.fernandocanabarro.oak_desafio.models.PasswordRecover;
import com.fernandocanabarro.oak_desafio.models.User;
import com.fernandocanabarro.oak_desafio.repositories.ActivationCodeRepository;
import com.fernandocanabarro.oak_desafio.repositories.PasswordRecoverRepository;
import com.fernandocanabarro.oak_desafio.repositories.RoleRepository;
import com.fernandocanabarro.oak_desafio.repositories.UserRepository;
import com.fernandocanabarro.oak_desafio.services.AuthService;
import com.fernandocanabarro.oak_desafio.services.EmailService;
import com.sendgrid.helpers.mail.Mail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final PasswordRecoverRepository passwordRecoverRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegistrationRequestDTO dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setActivated(false);
        user.addRole(roleRepository.findByAuthority("ROLE_USER"));
        userRepository.save(user);
        sendConfirmationEmail(user);
    }

    @Override
    public void sendConfirmationEmail(User user) {
        String code = createActivationCodeAndSave(user);
        Mail mail = emailService.createConfirmationEmailTemplate(user.getFullName(), user.getEmail(), code);
        emailService.sendEmail(mail);
    }

    @Override
    public String createActivationCodeAndSave(User user) {
        String code = createCode();
        ActivationCode activationCode = new ActivationCode(code, user);
        activationCodeRepository.save(activationCode);
        return code;
    }

    @Override
    public String createCode() {
        String chars = "0123456789";
        int size = chars.length();
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int randomIndex = secureRandom.nextInt(size);
            builder.append(chars.charAt(randomIndex));
        }
        return builder.toString();
    }

    @Override
    public void activateAccount(ActivateAccountDTO dto) {
        Optional<ActivationCode> activationCode = activationCodeRepository.findByCode(dto.getCode());
        activationCode.get().setValidatedAt(LocalDateTime.now());
        User user = userRepository.findByEmail(activationCode.get().getUser().getEmail()).get();
        user.setActivated(true);
        userRepository.save(user);
        activationCodeRepository.save(activationCode.get());
    }

    @Override
    public void createPasswordRecover(EmailDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail()).get();
        String code = createCode();
        PasswordRecover passwordRecover = new PasswordRecover(code,user);
        passwordRecoverRepository.save(passwordRecover);
        Mail mail = emailService.createPasswordRecoverTemplate(user.getFullName(), user.getEmail(), code);
        emailService.sendEmail(mail);
    }

    @Override
    public void saveNewPassword(PasswordRecoverDTO dto) {
        PasswordRecover passwordRecover = passwordRecoverRepository.findByCode(dto.getCode()).get();
        User user = passwordRecover.getUser();
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        passwordRecover.setValidatedAt(LocalDateTime.now());
    }

    

}
