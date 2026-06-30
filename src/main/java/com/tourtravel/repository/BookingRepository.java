package com.tourtravel.repository;

import com.tourtravel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Booking entity.
 * Provides CRUD operations for booking records.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}