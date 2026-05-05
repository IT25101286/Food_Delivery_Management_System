package com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant;

public enum OrderStatus {
    PENDING,        // Order placed, waiting for payment
    PAYMENT_SUCCESS, // Payment done, sent to restaurant
    ACCEPTED,       // Restaurant accepted
    DECLINED,       // Restaurant declined
    PREPARING,      // Restaurant is preparing
    OUT_FOR_DELIVERY, // Handed to delivery
    DELIVERED       // Delivered to customer
}