package sliit.foodDelivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sliit.foodDelivery.dto.AdminDashboardStatsDto;
import sliit.foodDelivery.repository.AddressRepository;
import sliit.foodDelivery.repository.OrderRepository;
import sliit.foodDelivery.repository.UserRepository;
import sliit.foodDelivery.service.AdminDashboardService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    @Override
    public AdminDashboardStatsDto getStats() {
        long totalUsers = userRepository.count();
        long activeOrders = orderRepository.countActiveOrders();
        long totalAddresses = addressRepository.count();

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrowStart = LocalDate.now().plusDays(1).atStartOfDay();
        long ordersToday = orderRepository.countByOrderDateBetween(todayStart, tomorrowStart);

        return new AdminDashboardStatsDto(totalUsers, activeOrders, totalAddresses, ordersToday);
    }
}
