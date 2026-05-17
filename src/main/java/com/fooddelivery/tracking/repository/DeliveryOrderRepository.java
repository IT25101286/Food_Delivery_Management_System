package com.fooddelivery.tracking.repository;

import com.fooddelivery.tracking.entity.DeliveryOrder;
import com.fooddelivery.tracking.entity.DeliveryPerson;
import com.fooddelivery.tracking.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
    Optional<DeliveryOrder> findByOrderRef(String orderRef);
    long countByStatus(DeliveryStatus status);
    long countByStatusAndCreatedAtAfter(DeliveryStatus status, java.time.LocalDateTime dateTime);
    List<DeliveryOrder> findByStatus(DeliveryStatus status);
    List<DeliveryOrder> findByDeliveryPerson(DeliveryPerson deliveryPerson);
    List<DeliveryOrder> findByDeliveryPersonAndStatus(DeliveryPerson deliveryPerson, DeliveryStatus status);
}

