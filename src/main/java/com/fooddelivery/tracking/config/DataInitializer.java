package com.fooddelivery.tracking.config;

import com.fooddelivery.tracking.entity.DeliveryOrder;
import com.fooddelivery.tracking.entity.DeliveryPerson;
import com.fooddelivery.tracking.enums.DeliveryStatus;
import com.fooddelivery.tracking.enums.UserRole;
import com.fooddelivery.tracking.repository.DeliveryOrderRepository;
import com.fooddelivery.tracking.repository.DeliveryPersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DeliveryPersonRepository deliveryPersonRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(DeliveryPersonRepository deliveryPersonRepository,
                          DeliveryOrderRepository deliveryOrderRepository,
                          PasswordEncoder passwordEncoder) {
        this.deliveryPersonRepository = deliveryPersonRepository;
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize delivery persons if none exist
        if (deliveryPersonRepository.count() == 0) {
            // Admin user
            DeliveryPerson admin = new DeliveryPerson();
            admin.setName("Admin User");
            admin.setEmail("admin@food.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setPhone("077-0000000");
            admin.setVehicleNumber("ADMIN-001");
            admin.setAvailable(true);
            admin.setRole(UserRole.ROLE_ADMIN);
            deliveryPersonRepository.save(admin);

            // Delivery person 1
            DeliveryPerson person1 = new DeliveryPerson();
            person1.setName("John Doe");
            person1.setEmail("john@delivery.com");
            person1.setPassword(passwordEncoder.encode("John@123"));
            person1.setPhone("077-1234567");
            person1.setVehicleNumber("LK-ABC-1234");
            person1.setAvailable(true);
            person1.setRole(UserRole.ROLE_DELIVERY);
            deliveryPersonRepository.save(person1);

            // Delivery person 2
            DeliveryPerson person2 = new DeliveryPerson();
            person2.setName("Jane Smith");
            person2.setEmail("jane@delivery.com");
            person2.setPassword(passwordEncoder.encode("Jane@123"));
            person2.setPhone("077-9876543");
            person2.setVehicleNumber("LK-XYZ-5678");
            person2.setAvailable(true);
            person2.setRole(UserRole.ROLE_DELIVERY);
            deliveryPersonRepository.save(person2);
        }

        // Initialize orders if none exist
        if (deliveryOrderRepository.count() == 0) {
            DeliveryPerson person1 = deliveryPersonRepository.findByEmail("john@delivery.com").orElse(null);
            DeliveryPerson person2 = deliveryPersonRepository.findByEmail("jane@delivery.com").orElse(null);

            // Order 1 - Pending
            DeliveryOrder order1 = new DeliveryOrder();
            order1.setOrderRef("FD-2045");
            order1.setCustomerName("Alice Brown");
            order1.setCustomerPhone("071-1111111");
            order1.setDeliveryAddress("12 Main Street, Colombo 03");
            order1.setRestaurantName("Pizza Palace");
            order1.setStatus(DeliveryStatus.PENDING);
            order1.setEstimatedMinutes(30);
            deliveryOrderRepository.save(order1);

            // Order 2 - Assigned
            DeliveryOrder order2 = new DeliveryOrder();
            order2.setOrderRef("FD-2046");
            order2.setCustomerName("Bob Wilson");
            order2.setCustomerPhone("071-2222222");
            order2.setDeliveryAddress("45 Galle Road, Colombo 06");
            order2.setRestaurantName("Burger Barn");
            order2.setStatus(DeliveryStatus.ASSIGNED);
            order2.setDeliveryPerson(person1);
            order2.setAssignedAt(LocalDateTime.now().minusHours(1));
            order2.setEstimatedMinutes(25);
            deliveryOrderRepository.save(order2);

            // Order 3 - On the way
            DeliveryOrder order3 = new DeliveryOrder();
            order3.setOrderRef("FD-2047");
            order3.setCustomerName("Carol Davis");
            order3.setCustomerPhone("071-3333333");
            order3.setDeliveryAddress("78 Union Place, Colombo 02");
            order3.setRestaurantName("Sushi Central");
            order3.setStatus(DeliveryStatus.ON_THE_WAY);
            order3.setDeliveryPerson(person2);
            order3.setAssignedAt(LocalDateTime.now().minusMinutes(45));
            order3.setEstimatedMinutes(15);
            deliveryOrderRepository.save(order3);

            // Order 4 - Delivered
            DeliveryOrder order4 = new DeliveryOrder();
            order4.setOrderRef("FD-2048");
            order4.setCustomerName("Dave Martin");
            order4.setCustomerPhone("071-4444444");
            order4.setDeliveryAddress("23 Flower Road, Colombo 07");
            order4.setRestaurantName("Spice Garden");
            order4.setStatus(DeliveryStatus.DELIVERED);
            order4.setDeliveryPerson(person1);
            order4.setAssignedAt(LocalDateTime.now().minusHours(2));
            order4.setDeliveredAt(LocalDateTime.now().minusMinutes(30));
            order4.setEstimatedMinutes(20);
            deliveryOrderRepository.save(order4);

            // Order 5 - Cancelled
            DeliveryOrder order5 = new DeliveryOrder();
            order5.setOrderRef("FD-2049");
            order5.setCustomerName("Eva Turner");
            order5.setCustomerPhone("071-5555555");
            order5.setDeliveryAddress("90 High Level Road, Nugegoda");
            order5.setRestaurantName("Pasta House");
            order5.setStatus(DeliveryStatus.CANCELLED);
            order5.setEstimatedMinutes(25);
            deliveryOrderRepository.save(order5);
        }
    }
}
