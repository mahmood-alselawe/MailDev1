package com.takarub.mail_dev.service;

import com.takarub.mail_dev.model.User;

public interface UserService {
    User saveUser(User user);
    Boolean verifyToken(String token);
}
