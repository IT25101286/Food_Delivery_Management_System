package com.fooddelivery.config;

import com.fooddelivery.entity.*;
import com.fooddelivery.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository users;
    private final AdminRepository admins;
    private final RestaurantRepository restaurants;
    private final MenuItemRepository menuItems;
    private final OrderRepository orders;
    private final PaymentRepository payments;
    private final DeliveryPersonRepository deliveryPersons;
    private final DeliveryOrderRepository deliveryOrders;

    @PostConstruct
    public void init() {
        if (!users.existsByEmail("user@food.com")) {
            users.save(User.builder().name("John Doe").email("user@food.com").password("user123").phone("0711234567").role("USER").build());
        }

        if (!users.existsByEmail("admin@food.com")) {
            users.save(User.builder().name("Admin User").email("admin@food.com").password("admin123").phone("0770000000").role("ADMIN").build());
        }

        // Always enforce correct credentials and roles for seed accounts on every startup
        admins.findByEmail("admin@food.com").ifPresentOrElse(
            a -> { a.setPassword("admin123"); a.setAdminRole("ADMIN"); admins.save(a); },
            () -> admins.save(Admin.builder().name("Admin User").email("admin@food.com").password("admin123").phone("0770000000").adminRole("ADMIN").build())
        );

        admins.findByEmail("superadmin@food.com").ifPresentOrElse(
            a -> { a.setPassword("superadmin123"); a.setAdminRole("SUPER_ADMIN"); admins.save(a); },
            () -> admins.save(Admin.builder().name("Super Admin").email("superadmin@food.com").password("superadmin123").phone("0770000001").adminRole("SUPER_ADMIN").build())
        );

        if (restaurants.count() == 0) {
            restaurants.saveAll(List.of(
                Restaurant.builder().name("Spice Garden").address("12 Main St, Colombo").phone("0112345678").cuisineType("Sri Lankan").status("OPEN").openingTime("08:00").closingTime("22:00").ownerName("Nimal Silva").build(),
                Restaurant.builder().name("Pizza Palace").address("45 Union Place").phone("0113456789").cuisineType("Italian").status("OPEN").openingTime("10:00").closingTime("23:00").ownerName("Kamal Perera").build(),
                Restaurant.builder().name("Burger Hub").address("7 Galle Rd").phone("0114567890").cuisineType("Fast Food").status("OPEN").openingTime("09:00").closingTime("21:00").ownerName("Saman Fernando").build(),
                Restaurant.builder().name("Dragon Wok").address("33 Kandy Rd").phone("0115678901").cuisineType("Chinese").status("CLOSED").openingTime("11:00").closingTime("22:00").ownerName("Li Wei").build(),
                Restaurant.builder().name("The Grill House").address("88 Flower Rd").phone("0116789012").cuisineType("BBQ").status("OPEN").openingTime("12:00").closingTime("23:00").ownerName("Ravi Kumar").build()
            ));
        }

        if (menuItems.count() == 0) {
            List<Restaurant> rs = restaurants.findAll();
            menuItems.saveAll(List.of(
                MenuItem.builder().restaurantId(rs.get(0).getId()).name("Rice & Curry").category("Main").price(new BigDecimal("350.00")).isAvailable(true).build(),
                MenuItem.builder().restaurantId(rs.get(0).getId()).name("Kottu Roti").category("Main").price(new BigDecimal("400.00")).isAvailable(true).build(),
                MenuItem.builder().restaurantId(rs.get(1).getId()).name("Margherita Pizza").category("Pizza").price(new BigDecimal("1200.00")).isAvailable(true).build(),
                MenuItem.builder().restaurantId(rs.get(1).getId()).name("Pepperoni Pizza").category("Pizza").price(new BigDecimal("1400.00")).isAvailable(true).build(),
                MenuItem.builder().restaurantId(rs.get(2).getId()).name("Classic Burger").category("Burger").price(new BigDecimal("550.00")).isAvailable(true).build(),
                MenuItem.builder().restaurantId(rs.get(2).getId()).name("Chicken Burger").category("Burger").price(new BigDecimal("650.00")).isAvailable(false).build()
            ));
        }

        if (orders.count() == 0) {
            User user = users.findByEmail("user@food.com").orElseThrow();
            List<Restaurant> rs = restaurants.findAll();
            orders.saveAll(List.of(
                Order.builder().orderRef("FD-0001").userId(user.getId()).customerName("John Doe").deliveryAddress("12 Oak Lane, Colombo").restaurantId(rs.get(0).getId()).status("DELIVERED").totalAmount(new BigDecimal("750.00")).itemsSummary("Rice & Curry, Kottu Roti").createdAt(LocalDateTime.now().minusDays(2)).build(),
                Order.builder().orderRef("FD-0002").userId(user.getId()).customerName("John Doe").deliveryAddress("12 Oak Lane, Colombo").restaurantId(rs.get(1).getId()).status("PREPARING").totalAmount(new BigDecimal("1200.00")).itemsSummary("Margherita Pizza").createdAt(LocalDateTime.now().minusHours(3)).build(),
                Order.builder().orderRef("FD-0003").userId(user.getId()).customerName("John Doe").deliveryAddress("12 Oak Lane, Colombo").restaurantId(rs.get(2).getId()).status("PENDING").totalAmount(new BigDecimal("550.00")).itemsSummary("Classic Burger").createdAt(LocalDateTime.now().minusMinutes(30)).build()
            ));
        }

        if (payments.count() == 0) {
            Order o1 = orders.findByOrderRef("FD-0001").orElseThrow();
            Order o2 = orders.findByOrderRef("FD-0002").orElseThrow();
            payments.saveAll(List.of(
                Payment.builder().paymentRef("PAY-00001").orderId(o1.getId()).orderRef("FD-0001").customerName("John Doe").amount(new BigDecimal("750.00")).paymentMethod("CARD").status("COMPLETED").paidAt(LocalDateTime.now().minusDays(2)).build(),
                Payment.builder().paymentRef("PAY-00002").orderId(o2.getId()).orderRef("FD-0002").customerName("John Doe").amount(new BigDecimal("1200.00")).paymentMethod("CASH").status("COMPLETED").paidAt(LocalDateTime.now().minusHours(3)).build(),
                Payment.builder().paymentRef("PAY-00003").orderId(o1.getId()).orderRef("FD-0001").customerName("John Doe").amount(new BigDecimal("550.00")).paymentMethod("MOBILE").status("PENDING").paidAt(LocalDateTime.now().minusMinutes(10)).build()
            ));
        }

        if (deliveryPersons.count() == 0) {
            deliveryPersons.saveAll(List.of(
                DeliveryPerson.builder().name("Kasun Jayawardena").phone("0771112233").isAvailable(true).build(),
                DeliveryPerson.builder().name("Dilshan Madushanka").phone("0772223344").isAvailable(true).build()
            ));
        }

        if (deliveryOrders.count() == 0) {
            List<DeliveryPerson> dp = deliveryPersons.findAll();
            deliveryOrders.saveAll(List.of(
                DeliveryOrder.builder().orderRef("FD-0001").customerName("John Doe").deliveryAddress("12 Oak Lane, Colombo").restaurantName("Spice Garden").status("DELIVERED").deliveryPersonId(dp.get(0).getId()).estimatedMinutes(30).build(),
                DeliveryOrder.builder().orderRef("FD-0002").customerName("John Doe").deliveryAddress("12 Oak Lane, Colombo").restaurantName("Pizza Palace").status("ASSIGNED").deliveryPersonId(dp.get(1).getId()).estimatedMinutes(45).build(),
                DeliveryOrder.builder().orderRef("FD-0003").customerName("John Doe").deliveryAddress("12 Oak Lane, Colombo").restaurantName("Burger Hub").status("PENDING").deliveryPersonId(null).estimatedMinutes(null).build()
            ));
        }
    }
}