package com.tourtravel.dto.response;

import lombok.*;

/**
 * Dashboard statistics response for Admin Dashboard.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DashboardResponse {

    private Long totalUsers;

    private Long totalBookings;

    private Long pendingBookings;

    private Long confirmedBookings;

    private Long completedBookings;

    private Long cancelledBookings;

    private Long totalContacts;

    private Long newContacts;

    private Long readContacts;

    private Long respondedContacts;

    private Long closedContacts;

}