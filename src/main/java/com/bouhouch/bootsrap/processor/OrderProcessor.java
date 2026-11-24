
package com.bouhouch.bootsrap.processor;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.bouhouch.bootsrap.model.OrderInput;
import com.bouhouch.bootsrap.model.OrderStatus;

@Component
public class OrderProcessor implements ItemProcessor<OrderInput, OrderInput> {

    @Override
    public OrderInput process(OrderInput order) {

        // 1. Validate amount
        if (!Double.isFinite(order.getAmount()) || order.getAmount() <= 0) {
            return reject(order, "Amount must be greater than zero");
        }

        // 2. Validate order date
        if (order.getOrder_date() == null) {
            return reject(order, "Order date is required");
        }

        // 3. Validate customer
        if (order.getCustomer_id() == null ||
                order.getCustomer_id().isBlank()) {
            return reject(order, "Customer ID is required");
        }

        // 4. Validate status
        if (order.getStatus() == null) {
            return reject(order, "Order status is required");
        }

        // 5. Business rule: cancelled orders are rejected
        if (order.getStatus() == OrderStatus.CANCELLED) {
            return reject(order, "Order is cancelled");
        }

        // 6. Valid order
        order.setRejectionReason(null);
        return order;
    }

    private OrderInput reject(OrderInput order, String reason) {

        order.setRejectionReason(reason);

        System.out.println(
            "Rejected order " + order.getOrder_id() + ": " + reason
        );

        return null;
    }
}
