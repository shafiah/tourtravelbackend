package com.tourtravel.controller;

import com.tourtravel.dto.request.CreateContactRequest;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.dto.response.ContactResponse;
import com.tourtravel.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Contact Us APIs.
 */
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * Submit Contact Us enquiry.
     *
     * @param request Contact request DTO
     * @return Success response
     */
    @PostMapping
    public ResponseEntity<ApiResponse> submitContact(
            @Valid @RequestBody CreateContactRequest request) {

        ContactResponse contactResponse = contactService.createContact(request);

        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("Your enquiry has been submitted successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}