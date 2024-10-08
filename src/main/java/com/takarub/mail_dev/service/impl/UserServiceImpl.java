package com.takarub.mail_dev.service.impl;

import com.takarub.mail_dev.model.Confirmation;
import com.takarub.mail_dev.model.User;
import com.takarub.mail_dev.repository.ConfirmationRepository;
import com.takarub.mail_dev.repository.UserRepository;
import com.takarub.mail_dev.service.EmailService;
import com.takarub.mail_dev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("The email already exists");
        }
        user.setEnabled(false);
        userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

//        emailService.sendSimpleMailMessage(user.getName(), user.getEmail(), confirmation.getToken());
        emailService.sendHtmlEmail(user.getName(), user.getEmail(),confirmation.getToken());
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation byToken = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(byToken.getUser().getEmail());

        user.setEnabled(true);
        userRepository.save(user);
        return Boolean.TRUE;
    }
}
