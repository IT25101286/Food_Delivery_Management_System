package com.fooddelivery.onlinefooddeliverymanagementsystem.order;

import java.math.BigDecimal;

// Inheritance — BulkOrder extends Order
public class BulkOrder extends Order {

    private static final double BULK_THRESHOLD = 2500.0;
    private static final double DISCOUNT_RATE = 0.10; // 10% discount

    public BulkOrder() {
        super();
    }

    // Polymorphism — overrides getOrderSummary() differently
    public String getOrderSummary() {
        return "Bulk Order #" + getId() +
                " | Customer: " + getCustomerName() +
                " | Total: Rs." + getTotalAmount() +
                " | Discount: 10%" +
                " | Status: " + getStatus();
    }

    public boolean isBulkOrder() {
        return getTotalAmount() != null &&
                getTotalAmount().doubleValue() > BULK_THRESHOLD;
    }

    public BigDecimal getDiscountedTotal() {
        if (getTotalAmount() == null) return BigDecimal.ZERO;
        BigDecimal discount = getTotalAmount()
                .multiply(BigDecimal.valueOf(DISCOUNT_RATE));
        return getTotalAmount().subtract(discount);
    }

    public String getOrderType() {
        return "BULK";
    }
}