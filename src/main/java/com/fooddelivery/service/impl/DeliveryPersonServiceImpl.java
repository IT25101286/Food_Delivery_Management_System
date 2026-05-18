package com.fooddelivery.service.impl;

import com.fooddelivery.entity.DeliveryPerson;
import com.fooddelivery.repository.DeliveryPersonRepository;
import com.fooddelivery.service.interfaces.DeliveryPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryPersonServiceImpl implements DeliveryPersonService {
    private final DeliveryPersonRepository repo;

    @Override public DeliveryPerson save(DeliveryPerson p) { return repo.save(p); }
    @Override public Optional<DeliveryPerson> findById(Long id) { return repo.findById(id); }
    @Override public List<DeliveryPerson> findAll() { return repo.findAll(); }
    @Override public List<DeliveryPerson> findAvailable() { return repo.findByIsAvailableTrue(); }
    @Override public void deleteById(Long id) { repo.deleteById(id); }
}
