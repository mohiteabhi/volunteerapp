package com.example.volunteerapp.repository;
import com.example.volunteerapp.entity.PasswordResetToken;
import com.example.volunteerapp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(UserInfo user);
}
