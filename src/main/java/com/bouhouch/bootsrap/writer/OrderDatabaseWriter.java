package com.bouhouch.bootsrap.writer;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bouhouch.bootsrap.model.OrderInput;

@Component
public class OrderDatabaseWriter implements ItemWriter<OrderInput> {

    private final JdbcTemplate jdbcTemplate;

    public OrderDatabaseWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends OrderInput> chunk) throws Exception {
        System.out.println("\n--- Writing chunk to database ---");
        for (OrderInput order : chunk.getItems()) {
            System.out.println("Saving order: " + order.toString());
            jdbcTemplate.update(
                    "INSERT INTO orders (order_id, customer_id, order_date, amount, status) VALUES (?, ?, ?, ?, ?)",
                    order.getOrder_id(),
                    order.getCustomer_id(),
                    order.getOrder_date(),
                    order.getAmount(),
                    order.getStatus().name());
        }
        System.out.println("---------------------\n");
    }

}
