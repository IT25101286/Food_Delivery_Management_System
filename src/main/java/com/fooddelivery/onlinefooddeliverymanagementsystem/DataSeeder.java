package com.fooddelivery.onlinefooddeliverymanagementsystem;

import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItem;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItemRepository;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.Restaurant;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public void run(String... args) throws Exception {

        // Seed restaurants only if empty
        if (restaurantRepository.count() == 0) {

            Restaurant burgerKing = new Restaurant();
            burgerKing.setName("Burger King");
            burgerKing.setDescription("Home of the Whopper. Flame-grilled burgers since 1953.");
            burgerKing.setImageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=600");
            burgerKing.setDeliveryFee(new BigDecimal("99.00"));
            burgerKing.setOpen(true);
            restaurantRepository.save(burgerKing);

            Restaurant pizzaHut = new Restaurant();
            pizzaHut.setName("Pizza Hut");
            pizzaHut.setDescription("Sri Lanka's favourite pizza place. Fresh, hot and delivered fast.");
            pizzaHut.setImageUrl("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=600");
            pizzaHut.setDeliveryFee(new BigDecimal("110.00"));
            pizzaHut.setOpen(true);
            restaurantRepository.save(pizzaHut);

            Restaurant dragonWok = new Restaurant();
            dragonWok.setName("Dragon Wok");
            dragonWok.setDescription("Authentic Chinese and Sri Lankan fusion. Rice, noodles and more.");
            dragonWok.setImageUrl("https://images.unsplash.com/photo-1585032226651-759b368d7246?w=600");
            dragonWok.setDeliveryFee(new BigDecimal("85.00"));
            dragonWok.setOpen(true);
            restaurantRepository.save(dragonWok);

            Restaurant sweetStop = new Restaurant();
            sweetStop.setName("Sweet Stop");
            sweetStop.setDescription("Desserts, milkshakes and sweet treats delivered to your door.");
            sweetStop.setImageUrl("https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=600");
            sweetStop.setDeliveryFee(new BigDecimal("75.00"));
            sweetStop.setOpen(true);
            restaurantRepository.save(sweetStop);

            System.out.println("✅ Restaurants seeded successfully!");

            // Seed food items only if empty
            if (foodItemRepository.count() == 0) {

                // Burger King items
                FoodItem item1 = new FoodItem();
                item1.setName("Chicken Burger");
                item1.setDescription("Juicy grilled chicken with lettuce and mayo");
                item1.setPrice(new BigDecimal("450.00"));
                item1.setCategory("Burgers");
                item1.setImageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
                item1.setAvailable(true);
                item1.setRestaurant(burgerKing);
                foodItemRepository.save(item1);

                FoodItem item2 = new FoodItem();
                item2.setName("Whopper");
                item2.setDescription("Flame-grilled beef patty with tomatoes, lettuce and mayo");
                item2.setPrice(new BigDecimal("650.00"));
                item2.setCategory("Burgers");
                item2.setImageUrl("https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=400");
                item2.setAvailable(true);
                item2.setRestaurant(burgerKing);
                foodItemRepository.save(item2);

                FoodItem item3 = new FoodItem();
                item3.setName("Garlic Bread");
                item3.setDescription("Toasted bread with garlic butter");
                item3.setPrice(new BigDecimal("180.00"));
                item3.setCategory("Sides");
                item3.setImageUrl("https://static01.nyt.com/images/2018/12/11/dining/as-garlic-bread/as-garlic-bread-googleFourByThree-v2.jpg");
                item3.setAvailable(true);
                item3.setRestaurant(burgerKing);
                foodItemRepository.save(item3);

                // Pizza Hut items
                FoodItem item4 = new FoodItem();
                item4.setName("Margherita Pizza");
                item4.setDescription("Classic tomato sauce with mozzarella cheese");
                item4.setPrice(new BigDecimal("1200.00"));
                item4.setCategory("Pizza");
                item4.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqobAq9KzHOv_XO619kK8fN_MAbmYcgIIDBw&s");
                item4.setAvailable(true);
                item4.setRestaurant(pizzaHut);
                foodItemRepository.save(item4);

                FoodItem item5 = new FoodItem();
                item5.setName("BBQ Chicken Pizza");
                item5.setDescription("Smoky BBQ sauce with grilled chicken and onions");
                item5.setPrice(new BigDecimal("1450.00"));
                item5.setCategory("Pizza");
                item5.setImageUrl("https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400");
                item5.setAvailable(true);
                item5.setRestaurant(pizzaHut);
                foodItemRepository.save(item5);

                // Dragon Wok items
                FoodItem item6 = new FoodItem();
                item6.setName("Fried Rice");
                item6.setDescription("Wok fried rice with vegetables and egg");
                item6.setPrice(new BigDecimal("350.00"));
                item6.setCategory("Rice");
                item6.setImageUrl("https://www.onceuponachef.com/images/2023/12/Fried-Rice-Hero-12.jpg");
                item6.setAvailable(true);
                item6.setRestaurant(dragonWok);
                foodItemRepository.save(item6);

                FoodItem item7 = new FoodItem();
                item7.setName("Chicken Noodles");
                item7.setDescription("Stir fried noodles with chicken and vegetables");
                item7.setPrice(new BigDecimal("400.00"));
                item7.setCategory("Noodles");
                item7.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpTKKzEgHblPewdIZXUbQx4WvdkVHX9U25ow&s");
                item7.setAvailable(true);
                item7.setRestaurant(dragonWok);
                foodItemRepository.save(item7);

                FoodItem item8 = new FoodItem();
                item8.setName("Beef Kottu");
                item8.setDescription("Sri Lankan style kottu with beef and spices");
                item8.setPrice(new BigDecimal("550.00"));
                item8.setCategory("Kottu");
                item8.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHnGztDBA-Z-0GXqKZdktTjAcX4oDkPg1JNQ&s");
                item8.setAvailable(true);
                item8.setRestaurant(dragonWok);
                foodItemRepository.save(item8);

                // Sweet Stop items
                FoodItem item9 = new FoodItem();
                item9.setName("Chocolate Milkshake");
                item9.setDescription("Creamy chocolate milkshake with whipped cream");
                item9.setPrice(new BigDecimal("250.00"));
                item9.setCategory("Drinks");
                item9.setImageUrl("https://www.myfussyeater.com/wp-content/uploads/2025/06/Ninja-Creami-Chocolate-Milkshake_01.jpg");
                item9.setAvailable(true);
                item9.setRestaurant(sweetStop);
                foodItemRepository.save(item9);

                FoodItem item10 = new FoodItem();
                item10.setName("Chocolate Lava Cake");
                item10.setDescription("Warm chocolate cake with molten center");
                item10.setPrice(new BigDecimal("320.00"));
                item10.setCategory("Desserts");
                item10.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0mZi1dwTemhhx45piz4bA3M-94B2r8fydaA&s");
                item10.setAvailable(true);
                item10.setRestaurant(sweetStop);
                foodItemRepository.save(item10);

                System.out.println("✅ Food items seeded successfully!");
            }
        }

        System.out.println("✅ Data seeding complete!");
    }
}