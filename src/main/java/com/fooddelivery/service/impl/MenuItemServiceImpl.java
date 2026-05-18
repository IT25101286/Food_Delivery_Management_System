package com.fooddelivery.service.impl;

import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.repository.MenuItemRepository;
import com.fooddelivery.service.interfaces.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repo;

    @Override public MenuItem save(MenuItem i) { return repo.save(i); }
    @Override public Optional<MenuItem> findById(Long id) { return repo.findById(id); }
    @Override public List<MenuItem> findAll() { return repo.findAll(); }
    @Override public List<MenuItem> findAvailable() { return repo.findByIsAvailableTrue(); }
    @Override public List<MenuItem> findByRestaurantId(Long rid) { return repo.findByRestaurantId(rid); }
    @Override public void deleteById(Long id) { repo.deleteById(id); }
}
