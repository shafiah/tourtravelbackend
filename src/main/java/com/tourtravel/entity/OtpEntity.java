package com.tourtravel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verifications", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "otp"}, name = "uk_otp_email_otp")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {})
public class OtpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "otp", nullable = false, length = 6)
    private String otp;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "verified", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean verified;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.verified == null) {
            this.verified = false;
        }
    }
}
