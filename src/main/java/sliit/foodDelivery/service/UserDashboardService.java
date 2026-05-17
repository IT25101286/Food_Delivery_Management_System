package sliit.foodDelivery.service;

import sliit.foodDelivery.dto.UserDashboardStatsDto;

public interface UserDashboardService {
    UserDashboardStatsDto getStats(Long userId);
}
