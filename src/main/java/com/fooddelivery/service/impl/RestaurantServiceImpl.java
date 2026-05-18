package com.fooddelivery.service.impl;

import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.service.interfaces.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repo;

    @Override public Restaurant save(Restaurant r) { return repo.save(r); }
    @Override public Optional<Restaurant> findById(Long id) { return repo.findById(id); }
    @Override public List<Restaurant> findAll() { return repo.findAll(); }
    @Override public List<Restaurant> findOpen() { return repo.findByStatus("OPEN"); }
    @Override public void deleteById(Long id) { repo.deleteById(id); }
}
