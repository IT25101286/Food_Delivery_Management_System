package com.fooddelivery.service.impl;

import com.fooddelivery.entity.MenuItem;
import com.fooddelivery.entity.Order;
import com.fooddelivery.repository.MenuItemRepository;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repo;
    private final MenuItemRepository menuRepo;

    @Override
    public Order placeOrder(Long userId, String customerName, String deliveryAddress, Long restaurantId, Map<Long, Integer> quantities) {
        List<MenuItem> items = menuRepo.findAllById(quantities.keySet());
        BigDecimal total = items.stream()
                .map(i -> i.getPrice().multiply(new BigDecimal(quantities.getOrDefault(i.getId(), 1))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String summary = items.stream()
                .map(i -> i.getName() + " x" + quantities.getOrDefault(i.getId(), 1))
                .collect(Collectors.joining(", "));
        String data = serializeQty(quantities);
        return repo.save(Order.builder()
                .orderRef(generateRef())
                .userId(userId)
                .customerName(customerName)
                .deliveryAddress(deliveryAddress)
                .restaurantId(restaurantId)
                .status("PENDING")
                .totalAmount(total)
                .itemsSummary(summary)
                .itemsData(data)
                .build());
    }

    @Override
    public void updateOrder(Long id, Long userId, String deliveryAddress, Long restaurantId, Map<Long, Integer> quantities) {
        repo.findById(id)
            .filter(o -> o.getUserId().equals(userId) && "PENDING".equals(o.getStatus()))
            .ifPresent(o -> {
                List<MenuItem> items = menuRepo.findAllById(quantities.keySet());
                BigDecimal total = items.stream()
                        .map(i -> i.getPrice().multiply(new BigDecimal(quantities.getOrDefault(i.getId(), 1))))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                String summary = items.stream()
                        .map(i -> i.getName() + " x" + quantities.getOrDefault(i.getId(), 1))
                        .collect(Collectors.joining(", "));
                o.setDeliveryAddress(deliveryAddress);
                o.setRestaurantId(restaurantId);
                o.setTotalAmount(total);
                o.setItemsSummary(summary);
                o.setItemsData(serializeQty(quantities));
                repo.save(o);
            });
    }

    @Override
    public Map<Long, Integer> getQuantities(Long id) {
        return repo.findById(id)
                .map(o -> deserializeQty(o.getItemsData()))
                .orElse(Collections.emptyMap());
    }

    private String serializeQty(Map<Long, Integer> quantities) {
        return quantities.entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue())
                .collect(Collectors.joining(","));
    }

    private Map<Long, Integer> deserializeQty(String data) {
        if (data == null || data.isBlank()) return Collections.emptyMap();
        return Stream.of(data.split(","))
                .map(s -> s.split(":"))
                .filter(a -> a.length == 2)
                .collect(Collectors.toMap(a -> Long.parseLong(a[0]), a -> Integer.parseInt(a[1])));
    }

    @Override public Optional<Order> findById(Long id) { return repo.findById(id); }
    @Override public Optional<Order> findByOrderRef(String ref) { return repo.findByOrderRef(ref); }
    @Override public List<Order> findByUserId(Long uid) { return repo.findByUserIdOrderByCreatedAtDesc(uid); }
    @Override public List<Order> findAll() { return repo.findAllByOrderByCreatedAtDesc(); }

    @Override
    public void updateStatus(Long id, String status) {
        repo.findById(id).ifPresent(o -> { o.setStatus(status); repo.save(o); });
    }

    @Override
    public void cancelOrder(Long id, Long userId) {
        repo.findById(id)
            .filter(o -> o.getUserId().equals(userId) && "PENDING".equals(o.getStatus()))
            .ifPresent(o -> { o.setStatus("CANCELLED"); repo.save(o); });
    }

    @Override
    public void deleteOrder(Long id, Long userId) {
        repo.findById(id)
            .filter(o -> o.getUserId().equals(userId) && "CANCELLED".equals(o.getStatus()))
            .ifPresent(repo::delete);
    }

    @Override
    public Map<String, Long> getStatusCounts() {
        Map<String, Long> m = new LinkedHashMap<>();
        for (String s : List.of("PENDING","CONFIRMED","PREPARING","DELIVERED","CANCELLED"))
            m.put(s, repo.countByStatus(s));
        return m;
    }

    private String generateRef() {
        String ref;
        do { ref = "FD-" + String.format("%04d", new Random().nextInt(9999) + 1); }
        while (repo.existsByOrderRef(ref));
        return ref;
    }
}
