package com.tourtravel.service;

import com.tourtravel.dto.response.DashboardResponse;

/**
 * Service interface for Admin Dashboard statistics.
 */
public interface DashboardService {

    /**
     * Fetch dashboard statistics.
     *
     * @return dashboard statistics
     */
    DashboardResponse getDashboardStatistics();

}