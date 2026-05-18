package com.fooddelivery.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable {
    private Long restaurantId;
    private String restaurantName;
    private Long itemId;
    private String itemName;
    private BigDecimal price;
    private int quantity;

    public BigDecimal getSubtotal() {
        return price.multiply(new BigDecimal(quantity));
    }
}
