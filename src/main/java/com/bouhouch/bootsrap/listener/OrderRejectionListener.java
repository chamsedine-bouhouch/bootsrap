
package com.bouhouch.bootsrap.listener;

import org.springframework.batch.core.listener.ItemProcessListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bouhouch.bootsrap.model.OrderInput;

@Component
public class OrderRejectionListener
        implements ItemProcessListener<OrderInput, OrderInput> {

    private final JdbcTemplate jdbcTemplate;

    public OrderRejectionListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterProcess(OrderInput item, OrderInput result) {

        // A null result means the processor filtered the record.
        if (result == null && item.getRejectionReason() != null) {

            String sql = """
                INSERT INTO order_rejections
                    (order_id, customer_id, order_date,
                     amount, status, rejection_reason)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

            jdbcTemplate.update(
                sql,
                String.valueOf(item.getOrder_id()),
                item.getCustomer_id(),
                item.getOrder_date() != null
                    ? item.getOrder_date().toString() : null,
                String.valueOf(item.getAmount()),
                item.getStatus() != null
                    ? item.getStatus().name() : null,
                item.getRejectionReason()
            );

            System.out.println(
                "Rejection saved for order: " + item.getOrder_id()
            );
        }
    }
}
