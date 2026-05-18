package sliit.foodDelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDashboardStatsDto {
    private long totalUsers;
    private long activeOrders;
    private long totalAddresses;
    private long ordersToday;
}
