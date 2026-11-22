
package com.bouhouch.bootsrap.processor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.bouhouch.bootsrap.model.OrderInput;
import com.bouhouch.bootsrap.model.OrderStatus;

@Component
public class OrderProcessor implements ItemProcessor<OrderInput, OrderInput> {

    // Store already processed order IDs
    private final Set<Long> processedOrderIds = new HashSet<>();

    @Override
    public OrderInput process(OrderInput order) throws Exception {

        // 1. Validate amount
        if (order.getAmount() < 0) {
            System.out.println(
                "Skipping invalid amount for order: " + order.getOrder_id()
            );
            throw new IllegalArgumentException(
                "Invalid amount for order: " + order.getOrder_id()
            );
        }

        // 2. Validate order date
        if (order.getOrder_date() == null) {
            System.out.println(
                "Skipping invalid date for order: " + order.getOrder_id()
            );
            throw new IllegalArgumentException(
                "Order date is null for order: " + order.getOrder_id()
            );
        }

        // 3. Validate status
        if (order.getStatus() == null) {
            throw new IllegalArgumentException(
                "Order status is null for order: " + order.getOrder_id()
            );
        }

        // 4. Skip cancelled orders
        if (order.getStatus() == OrderStatus.CANCELLED) {
            System.out.println(
                "Skipping cancelled order: " + order.getOrder_id()
            );
            return null;
        }

        // 5. Detect duplicate orders
        if (!processedOrderIds.add(order.getOrder_id())) {
            System.out.println(
                "Skipping duplicate order: " + order.getOrder_id()
            );
            return null;
        }

        // 6. Return valid order
        return order;
    }
}
