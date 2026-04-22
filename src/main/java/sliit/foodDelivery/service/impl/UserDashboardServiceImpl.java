package sliit.foodDelivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sliit.foodDelivery.dto.UserDashboardStatsDto;
import sliit.foodDelivery.entity.Address;
import sliit.foodDelivery.repository.AddressRepository;
import sliit.foodDelivery.repository.OrderRepository;
import sliit.foodDelivery.service.UserDashboardService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDashboardServiceImpl implements UserDashboardService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    @Override
    public UserDashboardStatsDto getStats(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime thisMonthStart = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime nextMonthStart = today.plusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime lastMonthStart = today.minusMonths(1).withDayOfMonth(1).atStartOfDay();

        long ordersThisMonth = orderRepository.countByUserIdAndOrderDateBetween(userId, thisMonthStart, nextMonthStart);
        long ordersLastMonth = orderRepository.countByUserIdAndOrderDateBetween(userId, lastMonthStart, thisMonthStart);
        long orderDeltaFromLastMonth = ordersThisMonth - ordersLastMonth;

        long savedAddresses = addressRepository.countByUserId(userId);
        List<Address> recentAddresses = addressRepository.findTop3ByUserIdOrderByUpdatedAtDesc(userId);

        String savedAddressPreview = recentAddresses.stream()
                .map(Address::getCity)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
                .distinct()
                .limit(3)
                .collect(Collectors.joining(", "));

        if (savedAddressPreview.isBlank()) {
            savedAddressPreview = "No saved addresses yet";
        }

        long deliveredOrders = orderRepository.countByUserIdAndStatusIgnoreCase(userId, "DELIVERED");
        long activeOrders = orderRepository.countActiveOrdersByUserId(userId);
        long loyaltyPoints = deliveredOrders * 100;

        return UserDashboardStatsDto.builder()
                .ordersThisMonth(ordersThisMonth)
                .orderDeltaFromLastMonth(orderDeltaFromLastMonth)
                .savedAddresses(savedAddresses)
                .savedAddressPreview(savedAddressPreview)
                .deliveredOrders(deliveredOrders)
                .activeOrders(activeOrders)
                .loyaltyPoints(loyaltyPoints)
                .build();
    }
}
