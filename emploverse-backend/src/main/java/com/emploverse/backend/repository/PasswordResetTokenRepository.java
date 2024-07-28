package com.emploverse.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emploverse.backend.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
