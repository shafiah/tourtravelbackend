package com.tourtravel.service;

import com.tourtravel.dto.request.CreateContactRequest;
import com.tourtravel.dto.response.ContactResponse;

/**
 * Service interface for Contact Us operations.
 */
public interface ContactService {

    /**
     * Save a new contact enquiry.
     *
     * @param request Contact request DTO
     * @return Contact response DTO
     */
    ContactResponse createContact(CreateContactRequest request);

}