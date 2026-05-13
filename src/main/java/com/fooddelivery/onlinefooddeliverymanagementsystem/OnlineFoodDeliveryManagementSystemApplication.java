package com.fooddelivery.onlinefooddeliverymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class OnlineFoodDeliveryManagementSystemApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OnlineFoodDeliveryManagementSystemApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineFoodDeliveryManagementSystemApplication.class, args);
    }
}