package com.tourtravel.enums;

/**
 * Booking status enum.
 */
public enum BookingStatus {

    /**
     * Booking submitted by customer.
     */
    PENDING,

    /**
     * Booking confirmed by admin.
     */
    CONFIRMED,

    /**
     * Booking cancelled.
     */
    CANCELLED,

    /**
     * Trip completed.
     */
    COMPLETED
}