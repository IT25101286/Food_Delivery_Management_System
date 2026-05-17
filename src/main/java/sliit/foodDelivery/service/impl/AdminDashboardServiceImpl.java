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

    // READ: Get admin dashboard statistics
    @Override
    public AdminDashboardStatsDto getStats() {
        // READ: Count total users, active orders, and total addresses
        long totalUsers = userRepository.count();
        long activeOrders = orderRepository.countActiveOrders();
        long totalAddresses = addressRepository.count();

        // READ: Count orders today
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrowStart = LocalDate.now().plusDays(1).atStartOfDay();
        long ordersToday = orderRepository.countByOrderDateBetween(todayStart, tomorrowStart);

        return new AdminDashboardStatsDto(totalUsers, activeOrders, totalAddresses, ordersToday);
    }
}
