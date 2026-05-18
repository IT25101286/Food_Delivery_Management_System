package com.fooddelivery.service.interfaces;

import com.fooddelivery.entity.DeliveryPerson;
import java.util.List;
import java.util.Optional;

public interface DeliveryPersonService {
    DeliveryPerson save(DeliveryPerson person);
    Optional<DeliveryPerson> findById(Long id);
    List<DeliveryPerson> findAll();
    List<DeliveryPerson> findAvailable();
    void deleteById(Long id);
}
