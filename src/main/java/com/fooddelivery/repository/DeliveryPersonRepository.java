package com.fooddelivery.repository;

import com.fooddelivery.entity.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
    List<DeliveryPerson> findByIsAvailableTrue();
}
