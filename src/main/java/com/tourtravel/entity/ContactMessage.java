package com.tourtravel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing Contact Us enquiries submitted by users.
 */
@Entity
@Table(name = "contact_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Customer Full Name
     */
    @Column(nullable = false, length = 100)
    private String fullName;

    /**
     * Customer Email Address
     */
    @Column(nullable = false, length = 100)
    private String email;

    /**
     * Customer Phone Number
     */
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    /**
     * Preferred Travel Destination
     */
    @Column(nullable = false, length = 100)
    private String preferredDestination;

    /**
     * Customer Message
     */
    @Column(nullable = false, length = 1000)
    private String message;

    /**
     * Enquiry Status
     * Default: NEW
     */
    @Column(nullable = false, length = 20)
    private String status;

    /**
     * Record Creation Time
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Record Last Updated Time
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = "NEW";
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}