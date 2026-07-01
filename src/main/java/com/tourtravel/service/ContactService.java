package com.tourtravel.service;

import com.tourtravel.dto.request.CreateContactRequest;
import com.tourtravel.dto.request.UpdateContactStatusRequest;
import com.tourtravel.dto.response.AdminContactResponse;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.dto.response.ContactResponse;

import java.util.List;

/**
 * Service interface for Contact operations.
 */
public interface ContactService {

    /**
     * Create a new contact enquiry.
     *
     * @param request contact request
     * @return contact response
     */
    ContactResponse createContact(CreateContactRequest request);

    /**
     * Get all contact enquiries.
     *
     * @return list of contact enquiries
     */
    List<AdminContactResponse> getAllContacts();

    /**
     * Get contact enquiry by ID.
     *
     * @param id contact ID
     * @return contact enquiry
     */
    AdminContactResponse getContactById(Long id);

    /**
     * Update contact enquiry status.
     *
     * @param id contact ID
     * @param request update status request
     * @return success response
     */
    ApiResponse updateContactStatus(Long id, UpdateContactStatusRequest request);

    /**
     * Delete contact enquiry.
     *
     * @param id contact ID
     * @return success response
     */
    ApiResponse deleteContact(Long id);

}