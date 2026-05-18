package sliit.foodDelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardStatsDto {
    private long ordersThisMonth;
    private long orderDeltaFromLastMonth;
    private long savedAddresses;
    private String savedAddressPreview;
    private long deliveredOrders;
    private long activeOrders;
    private long loyaltyPoints;
}
