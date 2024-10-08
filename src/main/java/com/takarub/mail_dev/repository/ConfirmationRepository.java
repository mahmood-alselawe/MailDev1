package com.takarub.mail_dev.repository;

import com.takarub.mail_dev.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<Confirmation,Long> {
    Confirmation findByToken(String token);
}
