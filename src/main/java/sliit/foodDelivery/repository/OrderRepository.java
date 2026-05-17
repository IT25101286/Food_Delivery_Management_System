package sliit.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sliit.foodDelivery.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // READ: Get orders by user
    List<Order> findByUserId(Long userId);
    
    // READ: Count orders in date range
    long countByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    
    // READ: Count orders by user and date range
    long countByUserIdAndOrderDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
    
    // READ: Count orders by user and status
    long countByUserIdAndStatusIgnoreCase(Long userId, String status);

    // READ: Count active orders by user (custom query)
    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId AND UPPER(o.status) NOT IN ('DELIVERED', 'CANCELLED')")
    long countActiveOrdersByUserId(@Param("userId") Long userId);

    // READ: Count all active orders (custom query)
    @Query("SELECT COUNT(o) FROM Order o WHERE UPPER(o.status) NOT IN ('DELIVERED', 'CANCELLED')")
    long countActiveOrders();
}