package com.fooddelivery.onlinefooddeliverymanagementsystem;

import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItem;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Override
    public void run(String... args) throws Exception {

        // Seed food items only if empty
        if (foodItemRepository.count() == 0) {

            foodItemRepository.save(new FoodItem(null,
                    "Chicken Burger",
                    "Juicy grilled chicken with lettuce and mayo",
                    new BigDecimal("450.00"),
                    "Burgers",
                    "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400",
                    true));

            foodItemRepository.save(new FoodItem(null,
                    "Margherita Pizza",
                    "Classic tomato sauce with mozzarella cheese",
                    new BigDecimal("1200.00"),
                    "Pizza",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqobAq9KzHOv_XO619kK8fN_MAbmYcgIIDBw&s",
                    true));

            foodItemRepository.save(new FoodItem(null,
                    "Fried Rice",
                    "Wok fried rice with vegetables and egg",
                    new BigDecimal("350.00"),
                    "Rice",
                    "https://www.onceuponachef.com/images/2023/12/Fried-Rice-Hero-12.jpg",
                    true));

            foodItemRepository.save(new FoodItem(null,
                    "Chocolate Milkshake",
                    "Creamy chocolate milkshake with whipped cream",
                    new BigDecimal("250.00"),
                    "Drinks",
                    "https://www.myfussyeater.com/wp-content/uploads/2025/06/Ninja-Creami-Chocolate-Milkshake_01.jpg",
                    true));

            foodItemRepository.save(new FoodItem(null,
                    "Garlic Bread",
                    "Toasted bread with garlic butter",
                    new BigDecimal("180.00"),
                    "Sides",
                    "https://static01.nyt.com/images/2018/12/11/dining/as-garlic-bread/as-garlic-bread-googleFourByThree-v2.jpg",
                    true));

            foodItemRepository.save(new FoodItem(null,
                    "Chocolate Lava Cake",
                    "Warm chocolate cake with molten center",
                    new BigDecimal("320.00"),
                    "Desserts",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0mZi1dwTemhhx45piz4bA3M-94B2r8fydaA&s",
                    true));

            foodItemRepository.save(new FoodItem(null,
                    "Chicken Noodles",
                    "Stir fried noodles with chicken and vegetables",
                    new BigDecimal("400.00"),
                    "Noodles",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpTKKzEgHblPewdIZXUbQx4WvdkVHX9U25ow&s",
                    true));

            foodItemRepository.save(new FoodItem(null,
                    "Beef Kottu",
                    "Sri Lankan style kottu with beef and spices",
                    new BigDecimal("550.00"),
                    "Kottu",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHnGztDBA-Z-0GXqKZdktTjAcX4oDkPg1JNQ&s",
                    true));

            System.out.println("✅ Food items seeded successfully!");
        }

        System.out.println("✅ Data seeding complete!");
    }
}