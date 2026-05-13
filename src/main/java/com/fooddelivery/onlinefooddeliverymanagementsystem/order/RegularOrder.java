package com.fooddelivery.onlinefooddeliverymanagementsystem.order;

// Inheritance — RegularOrder extends Order
public class RegularOrder extends Order {

    private static final double MAX_AMOUNT = 2500.0;

    public RegularOrder() {
        super();
    }

    // Polymorphism — overrides getOrderSummary()
    public String getOrderSummary() {
        return "Regular Order #" + getId() +
                " | Customer: " + getCustomerName() +
                " | Total: Rs." + getTotalAmount() +
                " | Status: " + getStatus();
    }

    public boolean isCashEligible() {
        return getTotalAmount() != null &&
                getTotalAmount().doubleValue() <= MAX_AMOUNT;
    }

    public String getOrderType() {
        return "REGULAR";
    }
}