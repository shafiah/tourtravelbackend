package com.tourtravel.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tourtravel.dto.request.UpdateBookingStatusRequest;
import com.tourtravel.dto.response.AdminBookingResponse;
import com.tourtravel.dto.response.ApiResponse;
import com.tourtravel.service.BookingService;

import jakarta.validation.Valid;
import java.util.List;

import com.tourtravel.dto.request.UpdateContactStatusRequest;
import com.tourtravel.dto.response.AdminContactResponse;
import com.tourtravel.service.ContactService;

import com.tourtravel.service.DashboardService;
import com.tourtravel.dto.response.DashboardResponse;

/**
 * REST Controller for Admin APIs.
 * Accessible only by ADMIN role.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	private final BookingService bookingService;
	private final ContactService contactService;
	private final DashboardService dashboardService;

	public AdminController(BookingService bookingService,
	                       ContactService contactService,
	                       DashboardService dashboardService) {
	    this.bookingService = bookingService;
	    this.contactService = contactService;
	    this.dashboardService = dashboardService;
	}

    /**
     * Protected Admin Test API.
     *
     * @param authentication Spring Security Authentication
     * @return Success response
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> adminTest(Authentication authentication) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("success", true);
        response.put("message", "Welcome Admin");
        response.put("email", authentication.getName());

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/bookings")
    public ResponseEntity<List<AdminBookingResponse>> getAllBookings() {

        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
    @GetMapping("/bookings/{id}")
    public ResponseEntity<AdminBookingResponse> getBookingById(@PathVariable Long id) {

        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    
    @PutMapping("/bookings/{id}/status")
    public ResponseEntity<ApiResponse> updateBookingStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingStatusRequest request) {

        return ResponseEntity.ok(
                bookingService.updateBookingStatus(id, request));
    }
    
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<ApiResponse> deleteBooking(@PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.deleteBooking(id));
    }
    
    @GetMapping("/contacts")
    public ResponseEntity<List<AdminContactResponse>> getAllContacts() {

        return ResponseEntity.ok(contactService.getAllContacts());
    }
    
    @GetMapping("/contacts/{id}")
    public ResponseEntity<AdminContactResponse> getContactById(@PathVariable Long id) {

        return ResponseEntity.ok(contactService.getContactById(id));
    }
    
    @PutMapping("/contacts/{id}/status")
    public ResponseEntity<ApiResponse> updateContactStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateContactStatusRequest request) {

        return ResponseEntity.ok(
                contactService.updateContactStatus(id, request));
    }
    
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<ApiResponse> deleteContact(@PathVariable Long id) {

        return ResponseEntity.ok(
                contactService.deleteContact(id));
    }
    
    /**
     * Fetch dashboard statistics.
     *
     * Accessible only by ADMIN.
     *
     * @return dashboard statistics
     */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardStatistics() {

        return ResponseEntity.ok(
                dashboardService.getDashboardStatistics());
    }
    
    
    
}