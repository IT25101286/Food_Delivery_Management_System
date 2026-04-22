package sliit.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sliit.foodDelivery.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    long countByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    long countByUserIdAndOrderDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
    long countByUserIdAndStatusIgnoreCase(Long userId, String status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId AND UPPER(o.status) NOT IN ('DELIVERED', 'CANCELLED')")
    long countActiveOrdersByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(o) FROM Order o WHERE UPPER(o.status) NOT IN ('DELIVERED', 'CANCELLED')")
    long countActiveOrders();
}