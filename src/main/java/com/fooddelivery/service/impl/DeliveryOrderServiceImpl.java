package com.fooddelivery.service.impl;

import com.fooddelivery.entity.DeliveryOrder;
import com.fooddelivery.entity.DeliveryPerson;
import com.fooddelivery.repository.DeliveryOrderRepository;
import com.fooddelivery.repository.DeliveryPersonRepository;
import com.fooddelivery.service.interfaces.DeliveryOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryOrderServiceImpl implements DeliveryOrderService {
    private final DeliveryOrderRepository repo;
    private final DeliveryPersonRepository personRepo;

    @Override public DeliveryOrder save(DeliveryOrder o) { return repo.save(o); }
    @Override public Optional<DeliveryOrder> findById(Long id) { return repo.findById(id); }
    @Override public Optional<DeliveryOrder> findByOrderRef(String ref) { return repo.findByOrderRef(ref); }
    @Override public List<DeliveryOrder> findAll() { return repo.findAll(); }

    @Override
    public void assign(Long id, Long deliveryPersonId, Integer estimatedMinutes) {
        repo.findById(id).ifPresent(o -> {
            o.setDeliveryPersonId(deliveryPersonId);
            o.setEstimatedMinutes(estimatedMinutes);
            o.setStatus("ASSIGNED");
            repo.save(o);
            personRepo.findById(deliveryPersonId).ifPresent(p -> {
                p.setAvailable(false);
                personRepo.save(p);
            });
        });
    }

    @Override
    public void updateStatus(Long id, String status) {
        repo.findById(id).ifPresent(o -> {
            o.setStatus(status);
            if ("DELIVERED".equals(status) || "CANCELLED".equals(status)) {
                if (o.getDeliveryPersonId() != null) {
                    personRepo.findById(o.getDeliveryPersonId()).ifPresent(p -> {
                        p.setAvailable(true);
                        personRepo.save(p);
                    });
                }
            }
            repo.save(o);
        });
    }
}
