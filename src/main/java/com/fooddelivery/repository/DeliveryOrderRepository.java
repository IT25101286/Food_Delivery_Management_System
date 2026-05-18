package com.fooddelivery.repository;

import com.fooddelivery.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
    Optional<DeliveryOrder> findByOrderRef(String orderRef);
    List<DeliveryOrder> findByDeliveryPersonId(Long deliveryPersonId);
    List<DeliveryOrder> findByStatus(String status);
}
