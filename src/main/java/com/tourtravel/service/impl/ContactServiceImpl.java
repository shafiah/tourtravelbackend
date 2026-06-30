package com.tourtravel.service.impl;

import com.tourtravel.dto.request.CreateContactRequest;
import com.tourtravel.dto.response.ContactResponse;
import com.tourtravel.entity.ContactMessage;
import com.tourtravel.repository.ContactRepository;
import com.tourtravel.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of ContactService.
 * Handles Contact Us enquiry operations.
 */
@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Creates a new contact enquiry and saves it into database.
     *
     * @param request Contact request DTO
     * @return Contact response DTO
     */
    @Override
    public ContactResponse createContact(CreateContactRequest request) {

        log.info("Creating contact enquiry for email: {}", request.getEmail());

        // Convert Request DTO -> Entity
        ContactMessage contactMessage = ContactMessage.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .preferredDestination(request.getPreferredDestination())
                .message(request.getMessage())
                .status("NEW")
                .build();

        // Save into database
        ContactMessage savedContact = contactRepository.save(contactMessage);

        log.info("Contact enquiry created successfully with ID: {}", savedContact.getId());

        // Convert Entity -> Response DTO
        return ContactResponse.builder()
                .id(savedContact.getId())
                .fullName(savedContact.getFullName())
                .email(savedContact.getEmail())
                .phoneNumber(savedContact.getPhoneNumber())
                .preferredDestination(savedContact.getPreferredDestination())
                .message(savedContact.getMessage())
                .status(savedContact.getStatus())
                .createdAt(savedContact.getCreatedAt())
                .build();
    }
}