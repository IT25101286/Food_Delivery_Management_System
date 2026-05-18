package com.fooddelivery.service.interfaces;

import com.fooddelivery.entity.DeliveryOrder;
import java.util.List;
import java.util.Optional;

public interface DeliveryOrderService {
    DeliveryOrder save(DeliveryOrder order);
    Optional<DeliveryOrder> findById(Long id);
    Optional<DeliveryOrder> findByOrderRef(String orderRef);
    List<DeliveryOrder> findAll();
    void assign(Long id, Long deliveryPersonId, Integer estimatedMinutes);
    void updateStatus(Long id, String status);
}
