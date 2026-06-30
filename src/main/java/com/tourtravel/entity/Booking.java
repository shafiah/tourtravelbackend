package com.tourtravel.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.tourtravel.enums.BookingStatus;

/**
 * Entity representing a travel package booking.
 * Stores customer booking details.
 */
@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Destination selected by customer.
     * Example: Bali, Kashmir, Kerala
     */
    @Column(nullable = false, length = 100)
    private String destination;

    /**
     * Date of departure.
     */
    @Column(nullable = false)
    private LocalDate departureDate;

    /**
     * Departure city.
     * Example: New York, Delhi
     */
    @Column(nullable = false, length = 100)
    private String departureCity;

    /**
     * Customer full name.
     */
    @Column(nullable = false, length = 100)
    private String fullName;

    /**
     * Customer phone number.
     */
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    /**
     * Customer email address.
     */
    @Column(nullable = false, length = 100)
    private String email;

    /**
     * Number of adults.
     */
    @Column(nullable = false)
    private Integer adults;

    /**
     * Number of children.
     */
    @Column(nullable = false)
    private Integer children;

    /**
     * Number of infants.
     */
    @Column(nullable = false)
    private Integer infants;

    /**
     * Booking status.
     * Default: PENDING
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus bookingStatus;

    /**
     * Record creation timestamp.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Record update timestamp.
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.bookingStatus == null) {
            this.bookingStatus = BookingStatus.PENDING;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}