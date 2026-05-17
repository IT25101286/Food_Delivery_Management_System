package com.fooddelivery.config;

import com.fooddelivery.entity.Admin;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.AdminRepository;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin already exists
        if (adminRepository.findByEmail("admin@food.com").isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail("admin@food.com");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            adminRepository.save(admin);
        }

        // Check if restaurants already exist
        if (restaurantRepository.count() == 0) {
            restaurantRepository.save(createRestaurant("Spice Palace", "123 Main St", "555-0101", "Indian", "OPEN", "09:00 AM", "11:00 PM", "Rajesh Kumar"));
            restaurantRepository.save(createRestaurant("Pasta Perfetto", "456 Oak Ave", "555-0102", "Italian", "OPEN", "10:00 AM", "10:00 PM", "Marco Rossi"));
            restaurantRepository.save(createRestaurant("Dragon Wok", "789 Pine Rd", "555-0103", "Chinese", "CLOSED", "11:00 AM", "09:30 PM", "Chen Wei"));
            restaurantRepository.save(createRestaurant("Taco Fiesta", "321 Elm St", "555-0104", "Mexican", "OPEN", "11:00 AM", "11:00 PM", "Maria Garcia"));
            restaurantRepository.save(createRestaurant("Burger Haven", "654 Maple Dr", "555-0105", "American", "SUSPENDED", "08:00 AM", "11:30 PM", "John Smith"));
        }
    }

    private Restaurant createRestaurant(String name, String address, String phone, String cuisineType,
                                       String status, String openingTime, String closingTime, String ownerName) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        restaurant.setCuisineType(cuisineType);
        restaurant.setStatus(status);
        restaurant.setOpeningTime(openingTime);
        restaurant.setClosingTime(closingTime);
        restaurant.setOwnerName(ownerName);
        return restaurant;
    }
}
