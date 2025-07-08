package com.example.volunteerapp.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter @Setter
    private UserInfo user;

    @Column(nullable = false)
    @Getter @Setter
    private Instant expiryDate;

    // Constructors, getters & setters
    public PasswordResetToken() {}
    public PasswordResetToken(String token, UserInfo user, Instant expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

}
