package com.tourtravel.repository;

import com.tourtravel.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

    /**
     * Find the most recent OTP record for the given email that is not yet verified.
     *
     * @param email the email address
     * @return Optional containing the OTP entity if found
     */
    @Query("SELECT o FROM OtpEntity o WHERE o.email = :email AND o.verified = false " +
           "ORDER BY o.createdAt DESC LIMIT 1")
    Optional<OtpEntity> findLatestUnverifiedOtpByEmail(@Param("email") String email);

    /**
     * Find OTP by email and OTP code that is not verified and not expired.
     *
     * @param email the email address
     * @param otp the OTP code
     * @param currentTime the current time to check expiry
     * @return Optional containing the OTP entity if found
     */
    @Query("SELECT o FROM OtpEntity o WHERE o.email = :email AND o.otp = :otp " +
           "AND o.verified = false AND o.expiryTime > :currentTime")
    Optional<OtpEntity> findValidOtpByEmailAndOtp(@Param("email") String email, 
                                                   @Param("otp") String otp,
                                                   @Param("currentTime") LocalDateTime currentTime);

    /**
     * Delete expired OTP records.
     *
     * @param currentTime the current time
     */
    @Query("DELETE FROM OtpEntity o WHERE o.expiryTime < :currentTime")
    void deleteExpiredOtps(@Param("currentTime") LocalDateTime currentTime);
}
