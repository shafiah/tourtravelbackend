package com.tourtravel.repository;

import com.tourtravel.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for ContactMessage entity.
 * Provides CRUD operations for Contact Us enquiries.
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactMessage, Long> {

}