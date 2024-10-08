package com.takarub.mail_dev.repository;

import com.takarub.mail_dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailIgnoreCase (String email);
    Boolean existsByEmail(String email);
}
