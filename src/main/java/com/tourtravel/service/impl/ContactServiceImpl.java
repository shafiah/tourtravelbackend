package com.tourtravel.service.impl;

import com.tourtravel.dto.request.CreateContactRequest;
import com.tourtravel.dto.response.ContactResponse;
import com.tourtravel.entity.ContactMessage;
import com.tourtravel.repository.ContactRepository;
import com.tourtravel.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tourtravel.enums.ContactStatus;

import com.tourtravel.dto.request.UpdateContactStatusRequest;
import com.tourtravel.dto.response.AdminContactResponse;
import com.tourtravel.dto.response.ApiResponse;

import com.tourtravel.service.EmailService;
import java.util.List;

/**
 * Implementation of ContactService.
 * Handles Contact Us enquiry operations.
 */
@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

	private final ContactRepository contactRepository;
	private final EmailService emailService;

	public ContactServiceImpl(ContactRepository contactRepository,
	                          EmailService emailService) {
	    this.contactRepository = contactRepository;
	    this.emailService = emailService;
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
                .status(ContactStatus.NEW)
                .build();

        // Save into database
        ContactMessage savedContact = contactRepository.save(contactMessage);

        log.info("Contact enquiry created successfully with ID: {}", savedContact.getId());
        
     // Send confirmation email to customer
        emailService.sendContactConfirmationToCustomer(
                savedContact.getEmail(),
                savedContact.getFullName());

        // Notify admin about new enquiry
        emailService.sendContactNotificationToAdmin(
                savedContact.getFullName(),
                savedContact.getEmail());

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
    
    @Override
    public List<AdminContactResponse> getAllContacts() {

        log.info("Fetching all contact enquiries");

        return contactRepository.findAll()
                .stream()
                .map(contact -> AdminContactResponse.builder()
                        .id(contact.getId())
                        .fullName(contact.getFullName())
                        .email(contact.getEmail())
                        .phoneNumber(contact.getPhoneNumber())
                        .preferredDestination(contact.getPreferredDestination())
                        .message(contact.getMessage())
                        .status(contact.getStatus())
                        .createdAt(contact.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public AdminContactResponse getContactById(Long id) {

        log.info("Fetching contact enquiry with ID: {}", id);

        ContactMessage contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Contact enquiry not found with ID: " + id));

        return AdminContactResponse.builder()
                .id(contact.getId())
                .fullName(contact.getFullName())
                .email(contact.getEmail())
                .phoneNumber(contact.getPhoneNumber())
                .preferredDestination(contact.getPreferredDestination())
                .message(contact.getMessage())
                .status(contact.getStatus())
                .createdAt(contact.getCreatedAt())
                .build();
    }

    @Override
    public ApiResponse updateContactStatus(Long id, UpdateContactStatusRequest request) {

        log.info("Updating contact enquiry status for ID: {}", id);

        ContactMessage contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Contact enquiry not found with ID: " + id));

        contact.setStatus(request.getStatus());

        contactRepository.save(contact);

        log.info("Contact enquiry status updated successfully for ID: {}", id);

        return ApiResponse.builder()
                .success(true)
                .message("Contact enquiry status updated successfully")
                .build();
    }

    @Override
    public ApiResponse deleteContact(Long id) {

        log.info("Deleting contact enquiry with ID: {}", id);

        ContactMessage contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Contact enquiry not found with ID: " + id));

        contactRepository.delete(contact);

        log.info("Contact enquiry deleted successfully with ID: {}", id);

        return ApiResponse.builder()
                .success(true)
                .message("Contact enquiry deleted successfully")
                .build();
    }
}