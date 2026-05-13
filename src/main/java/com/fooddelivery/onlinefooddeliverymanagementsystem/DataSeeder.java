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

        if (restaurantRepository.count() == 0) {

            // ==================== Restaurants ====================

            Restaurant burgerKing = new Restaurant();
            burgerKing.setName("Burger King");
            burgerKing.setDescription("Home of the Whopper. Flame-grilled burgers since 1953.");
            burgerKing.setImageUrl("https://www.fbf-bff.be/wp-content/uploads/2021/10/BK-Logo-1.png");
            burgerKing.setDeliveryFee(new BigDecimal("99.00"));
            burgerKing.setOpen(true);
            restaurantRepository.save(burgerKing);

            Restaurant pizzaHut = new Restaurant();
            pizzaHut.setName("Pizza Hut");
            pizzaHut.setDescription("Sri Lanka's favourite pizza place. Fresh, hot and delivered fast.");
            pizzaHut.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/Pizza_Hut_international_logo_2014.svg/3840px-Pizza_Hut_international_logo_2014.svg.png?utm_source=commons.wikimedia.org&utm_campaign=index&utm_content=thumbnail");
            pizzaHut.setDeliveryFee(new BigDecimal("110.00"));
            pizzaHut.setOpen(true);
            restaurantRepository.save(pizzaHut);

            Restaurant dragonWok = new Restaurant();
            dragonWok.setName("Dragon Wok");
            dragonWok.setDescription("Authentic Chinese and Sri Lankan fusion. Rice, noodles and more.");
            dragonWok.setImageUrl("https://static.wixstatic.com/media/4e4aee_14c48be995834d4cb87eab902caf7de3~mv2.png/v1/fit/w_2500,h_1330,al_c/4e4aee_14c48be995834d4cb87eab902caf7de3~mv2.png");
            dragonWok.setDeliveryFee(new BigDecimal("85.00"));
            dragonWok.setOpen(true);
            restaurantRepository.save(dragonWok);

            Restaurant sweetStop = new Restaurant();
            sweetStop.setName("The Sweet Stop");
            sweetStop.setDescription("Desserts, milkshakes and sweet treats delivered to your door.");
            sweetStop.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSx6u23ZNy7bYj5K4ZTvnakcxRXmcIHRH6c-g&s");
            sweetStop.setDeliveryFee(new BigDecimal("75.00"));
            sweetStop.setOpen(true);
            restaurantRepository.save(sweetStop);

            System.out.println("✅ Restaurants seeded successfully!");

            // ==================== Food Items ====================

            if (foodItemRepository.count() == 0) {

                // -------- Burger King — Burgers --------

                FoodItem bk1 = new FoodItem();
                bk1.setName("Crispy Chicken Burger");
                bk1.setDescription("Juicy Crispy chicken with lettuce and mayo");
                bk1.setPrice(new BigDecimal("450.00"));
                bk1.setCategory("Burgers");
                bk1.setImageUrl("https://i0.wp.com/flaevor.com/wp-content/uploads/2022/04/SambalFriedChickenBurger1.jpg?resize=1024%2C830&ssl=1");
                bk1.setAvailable(true);
                bk1.setRestaurant(burgerKing);
                foodItemRepository.save(bk1);

                FoodItem bk2 = new FoodItem();
                bk2.setName("Whopper");
                bk2.setDescription("Flame-grilled beef patty with tomatoes, lettuce and mayo");
                bk2.setPrice(new BigDecimal("650.00"));
                bk2.setCategory("Burgers");
                bk2.setImageUrl("https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=400");
                bk2.setAvailable(true);
                bk2.setRestaurant(burgerKing);
                foodItemRepository.save(bk2);

                FoodItem bk3 = new FoodItem();
                bk3.setName("Double Cheeseburger");
                bk3.setDescription("Two beef patties with double cheese and pickles");
                bk3.setPrice(new BigDecimal("750.00"));
                bk3.setCategory("Burgers");
                bk3.setImageUrl("https://images.unsplash.com/photo-1586816001966-79b736744398?w=400");
                bk3.setAvailable(true);
                bk3.setRestaurant(burgerKing);
                foodItemRepository.save(bk3);

                // -------- Burger King — Sides --------

                FoodItem bk4 = new FoodItem();
                bk4.setName("Garlic Bread");
                bk4.setDescription("Toasted bread with garlic butter");
                bk4.setPrice(new BigDecimal("180.00"));
                bk4.setCategory("Sides");
                bk4.setImageUrl("https://static01.nyt.com/images/2018/12/11/dining/as-garlic-bread/as-garlic-bread-googleFourByThree-v2.jpg");
                bk4.setAvailable(true);
                bk4.setRestaurant(burgerKing);
                foodItemRepository.save(bk4);

                FoodItem bk5 = new FoodItem();
                bk5.setName("French Fries");
                bk5.setDescription("Crispy golden fries lightly salted");
                bk5.setPrice(new BigDecimal("220.00"));
                bk5.setCategory("Sides");
                bk5.setImageUrl("https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=400");
                bk5.setAvailable(true);
                bk5.setRestaurant(burgerKing);
                foodItemRepository.save(bk5);

                FoodItem bk6 = new FoodItem();
                bk6.setName("Onion Rings");
                bk6.setDescription("Crispy battered onion rings with dipping sauce");
                bk6.setPrice(new BigDecimal("250.00"));
                bk6.setCategory("Sides");
                bk6.setImageUrl("https://images.unsplash.com/photo-1639024471283-03518883512d?w=400");
                bk6.setAvailable(true);
                bk6.setRestaurant(burgerKing);
                foodItemRepository.save(bk6);

                // -------- Burger King — Drinks --------

                FoodItem bk7 = new FoodItem();
                bk7.setName("Coca Cola");
                bk7.setDescription("Ice cold Coca Cola — regular size");
                bk7.setPrice(new BigDecimal("150.00"));
                bk7.setCategory("Drinks");
                bk7.setImageUrl("https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400");
                bk7.setAvailable(true);
                bk7.setRestaurant(burgerKing);
                foodItemRepository.save(bk7);

                FoodItem bk8 = new FoodItem();
                bk8.setName("Vanilla Milkshake");
                bk8.setDescription("Creamy vanilla milkshake blended fresh");
                bk8.setPrice(new BigDecimal("320.00"));
                bk8.setCategory("Drinks");
                bk8.setImageUrl("https://www.organicvalley.coop/_next/image?url=https%3A%2F%2Fcdn.sanity.io%2Fimages%2F5dqbssss%2Fproduction-v4%2Fd064c9029aced692680f6faebd047a0ca03fddbf-1356x1576.jpg%3Fw%3D1200&w=3840&q=75");
                bk8.setAvailable(true);
                bk8.setRestaurant(burgerKing);
                foodItemRepository.save(bk8);

                // -------- Pizza Hut — Pizza --------

                FoodItem ph1 = new FoodItem();
                ph1.setName("Margherita Pizza");
                ph1.setDescription("Classic tomato sauce with mozzarella cheese");
                ph1.setPrice(new BigDecimal("1200.00"));
                ph1.setCategory("Pizza");
                ph1.setImageUrl("https://images.unsplash.com/photo-1604068549290-dea0e4a305ca?w=400");
                ph1.setAvailable(true);
                ph1.setRestaurant(pizzaHut);
                foodItemRepository.save(ph1);

                FoodItem ph2 = new FoodItem();
                ph2.setName("BBQ Chicken Pizza");
                ph2.setDescription("Smoky BBQ sauce with grilled chicken and onions");
                ph2.setPrice(new BigDecimal("1450.00"));
                ph2.setCategory("Pizza");
                ph2.setImageUrl("https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400");
                ph2.setAvailable(true);
                ph2.setRestaurant(pizzaHut);
                foodItemRepository.save(ph2);

                FoodItem ph3 = new FoodItem();
                ph3.setName("Pepperoni Pizza");
                ph3.setDescription("Loaded with pepperoni slices and melted cheese");
                ph3.setPrice(new BigDecimal("1550.00"));
                ph3.setCategory("Pizza");
                ph3.setImageUrl("https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400");
                ph3.setAvailable(true);
                ph3.setRestaurant(pizzaHut);
                foodItemRepository.save(ph3);

                // -------- Pizza Hut — Pasta --------

                FoodItem ph4 = new FoodItem();
                ph4.setName("Spaghetti Bolognese");
                ph4.setDescription("Classic spaghetti with rich beef bolognese sauce");
                ph4.setPrice(new BigDecimal("950.00"));
                ph4.setCategory("Pasta");
                ph4.setImageUrl("https://images.unsplash.com/photo-1622973536968-3ead9e780960?w=400");
                ph4.setAvailable(true);
                ph4.setRestaurant(pizzaHut);
                foodItemRepository.save(ph4);

                FoodItem ph5 = new FoodItem();
                ph5.setName("Creamy Chicken Pasta");
                ph5.setDescription("Penne pasta in a rich creamy chicken sauce");
                ph5.setPrice(new BigDecimal("1050.00"));
                ph5.setCategory("Pasta");
                ph5.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCGntZ9S6IY3HBPagMWNTK8HAU0okwEm19cw&s");
                ph5.setAvailable(true);
                ph5.setRestaurant(pizzaHut);
                foodItemRepository.save(ph5);

                // -------- Pizza Hut — Desserts --------

                FoodItem ph6 = new FoodItem();
                ph6.setName("Choco Lava Cake");
                ph6.setDescription("Warm chocolate cake with a molten center");
                ph6.setPrice(new BigDecimal("420.00"));
                ph6.setCategory("Desserts");
                ph6.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSG6MsdTlmao0Xf_ZTlxhShIGYza2wGA-QKcw&s");
                ph6.setAvailable(true);
                ph6.setRestaurant(pizzaHut);
                foodItemRepository.save(ph6);

                // -------- Dragon Wok — Rice --------

                FoodItem dw1 = new FoodItem();
                dw1.setName("Egg Fried Rice");
                dw1.setDescription("Wok fried rice with vegetables and egg");
                dw1.setPrice(new BigDecimal("350.00"));
                dw1.setCategory("Rice");
                dw1.setImageUrl("https://www.cookerru.com/wp-content/uploads/2022/07/egg-fried-rice-main-preview.jpg");
                dw1.setAvailable(true);
                dw1.setRestaurant(dragonWok);
                foodItemRepository.save(dw1);

                FoodItem dw2 = new FoodItem();
                dw2.setName("Chicken Fried Rice");
                dw2.setDescription("Fragrant fried rice with tender chicken pieces");
                dw2.setPrice(new BigDecimal("420.00"));
                dw2.setCategory("Rice");
                dw2.setImageUrl("https://iamhomesteader.com/wp-content/uploads/2025/05/Bang-Bang-Chicken-Fried-Rice-2.jpg");
                dw2.setAvailable(true);
                dw2.setRestaurant(dragonWok);
                foodItemRepository.save(dw2);

                // -------- Dragon Wok — Noodles --------

                FoodItem dw3 = new FoodItem();
                dw3.setName("Chicken Noodles");
                dw3.setDescription("Stir fried noodles with chicken and vegetables");
                dw3.setPrice(new BigDecimal("400.00"));
                dw3.setCategory("Noodles");
                dw3.setImageUrl("https://www.maggi.lk/sites/default/files/styles/home_stage_944_531/public/srh_recipes/7c979378d75d81812f8540563da0e74c.jpg?h=4f5b30f1&itok=764JZNuT");
                dw3.setAvailable(true);
                dw3.setRestaurant(dragonWok);
                foodItemRepository.save(dw3);

                FoodItem dw4 = new FoodItem();
                dw4.setName("Beef Noodles");
                dw4.setDescription("Wok tossed noodles with tender beef strips");
                dw4.setPrice(new BigDecimal("480.00"));
                dw4.setCategory("Noodles");
                dw4.setImageUrl("https://www.halfbakedharvest.com/wp-content/uploads/2022/08/20-Minute-Korean-Beef-Sesame-Noodles-1.jpg");
                dw4.setAvailable(true);
                dw4.setRestaurant(dragonWok);
                foodItemRepository.save(dw4);

                // -------- Dragon Wok — Kottu --------

                FoodItem dw5 = new FoodItem();
                dw5.setName("Beef Kottu");
                dw5.setDescription("Sri Lankan style kottu with beef and spices");
                dw5.setPrice(new BigDecimal("550.00"));
                dw5.setCategory("Kottu");
                dw5.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_MzGtSqdj_U-lO7_1R1yxmOuxtVrsHUyGXQ&s");
                dw5.setAvailable(true);
                dw5.setRestaurant(dragonWok);
                foodItemRepository.save(dw5);

                FoodItem dw6 = new FoodItem();
                dw6.setName("Chicken Kottu");
                dw6.setDescription("Shredded roti with chicken, egg and vegetables");
                dw6.setPrice(new BigDecimal("500.00"));
                dw6.setCategory("Kottu");
                dw6.setImageUrl("https://i.ytimg.com/vi/wy4WV4o25EI/sddefault.jpg");
                dw6.setAvailable(true);
                dw6.setRestaurant(dragonWok);
                foodItemRepository.save(dw6);

                // -------- Dragon Wok — Soup --------

                FoodItem dw7 = new FoodItem();
                dw7.setName("Hot and Sour Soup");
                dw7.setDescription("Tangy and spicy classic Chinese soup");
                dw7.setPrice(new BigDecimal("280.00"));
                dw7.setCategory("Soup");
                dw7.setImageUrl("https://woonheng.com/wp-content/uploads/2021/03/Vegan-Hot-and-Sour-Soup-3.jpg");
                dw7.setAvailable(true);
                dw7.setRestaurant(dragonWok);
                foodItemRepository.save(dw7);

                FoodItem dw8 = new FoodItem();
                dw8.setName("Chicken Corn Soup");
                dw8.setDescription("Smooth and comforting chicken corn soup");
                dw8.setPrice(new BigDecimal("250.00"));
                dw8.setCategory("Soup");
                dw8.setImageUrl("https://www.recipetineats.com/tachyon/2014/06/Chinese-Chicken-Corn-Soup-2.jpg");
                dw8.setAvailable(true);
                dw8.setRestaurant(dragonWok);
                foodItemRepository.save(dw8);

                // -------- Sweet Stop — Milkshakes --------

                FoodItem ss1 = new FoodItem();
                ss1.setName("Chocolate Milkshake");
                ss1.setDescription("Creamy chocolate milkshake with whipped cream");
                ss1.setPrice(new BigDecimal("350.00"));
                ss1.setCategory("Milkshakes");
                ss1.setImageUrl("https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");
                ss1.setAvailable(true);
                ss1.setRestaurant(sweetStop);
                foodItemRepository.save(ss1);

                FoodItem ss2 = new FoodItem();
                ss2.setName("Strawberry Milkshake");
                ss2.setDescription("Fresh strawberry milkshake blended with ice cream");
                ss2.setPrice(new BigDecimal("370.00"));
                ss2.setCategory("Milkshakes");
                ss2.setImageUrl("https://www.thehungrybites.com/wp-content/uploads/2023/06/Strawberry-milkshake-frappuccino-featured-500x500.jpg?");
                ss2.setAvailable(true);
                ss2.setRestaurant(sweetStop);
                foodItemRepository.save(ss2);

                FoodItem ss3 = new FoodItem();
                ss3.setName("Vanilla Milkshake");
                ss3.setDescription("Classic creamy vanilla milkshake");
                ss3.setPrice(new BigDecimal("330.00"));
                ss3.setCategory("Milkshakes");
                ss3.setImageUrl("https://images.unsplash.com/photo-1568901839119-631418a3910d?w=400");
                ss3.setAvailable(true);
                ss3.setRestaurant(sweetStop);
                foodItemRepository.save(ss3);

                // -------- Sweet Stop — Desserts --------

                FoodItem ss4 = new FoodItem();
                ss4.setName("Chocolate Lava Cake");
                ss4.setDescription("Warm chocolate cake with molten center");
                ss4.setPrice(new BigDecimal("320.00"));
                ss4.setCategory("Desserts");
                ss4.setImageUrl("https://www.verybestbaking.com/sites/g/files/jgfbjl326/files/styles/large/public/recipe-thumbnail/116744-bd4b61de9035d59377d72c224a320cbd_Lava_Cake.jpg?itok=k_dLaW-U");
                ss4.setAvailable(true);
                ss4.setRestaurant(sweetStop);
                foodItemRepository.save(ss4);

                FoodItem ss5 = new FoodItem();
                ss5.setName("Cheesecake Slice");
                ss5.setDescription("Creamy New York style cheesecake with berry topping");
                ss5.setPrice(new BigDecimal("380.00"));
                ss5.setCategory("Desserts");
                ss5.setImageUrl("https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=400");
                ss5.setAvailable(true);
                ss5.setRestaurant(sweetStop);
                foodItemRepository.save(ss5);

                FoodItem ss6 = new FoodItem();
                ss6.setName("Brownie Sundae");
                ss6.setDescription("Warm brownie topped with vanilla ice cream and chocolate sauce");
                ss6.setPrice(new BigDecimal("420.00"));
                ss6.setCategory("Desserts");
                ss6.setImageUrl("https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400");
                ss6.setAvailable(true);
                ss6.setRestaurant(sweetStop);
                foodItemRepository.save(ss6);

                // -------- Sweet Stop — Juices --------

                FoodItem ss7 = new FoodItem();
                ss7.setName("Fresh Orange Juice");
                ss7.setDescription("Freshly squeezed orange juice — no added sugar");
                ss7.setPrice(new BigDecimal("280.00"));
                ss7.setCategory("Juices");
                ss7.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_k3dmXxGhAIYtpo1VDWy8SJi30wQqJ5J9Ig&s");
                ss7.setAvailable(true);
                ss7.setRestaurant(sweetStop);
                foodItemRepository.save(ss7);

                FoodItem ss8 = new FoodItem();
                ss8.setName("Watermelon Juice");
                ss8.setDescription("Refreshing cold pressed watermelon juice");
                ss8.setPrice(new BigDecimal("260.00"));
                ss8.setCategory("Juices");
                ss8.setImageUrl("https://www.rebootwithjoe.com/wp-content/uploads/2012/05/watermelon-pineapple-juice.jpg");
                ss8.setAvailable(true);
                ss8.setRestaurant(sweetStop);
                foodItemRepository.save(ss8);

                System.out.println("✅ Food items seeded successfully!");
            }
        }

        System.out.println("✅ Data seeding complete!");
    }
}