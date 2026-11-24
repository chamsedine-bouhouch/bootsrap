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
            // skip exixting orders
            if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders WHERE order_id = ?", Integer.class,
                    order.getOrder_id()) > 0) {
                System.out.println("Skipping existing order: " + order.getOrder_id());
                continue;
            }
            System.out.println("Saving order: " + order.toString());
            try {
                jdbcTemplate.update(
                        "INSERT INTO orders (order_id, customer_id, order_date, amount, status) VALUES (?, ?, ?, ?, ?)",
                        order.getOrder_id(),
                        order.getCustomer_id(),
                        order.getOrder_date(),
                        order.getAmount(),
                        order.getStatus().name());
            } catch (Exception e) {
                System.err.println("Error saving order: " + order.toString() + ". Error: " + e.getMessage());
                throw e; // Rethrow the exception to let Spring Batch handle it
            }
        }
        System.out.println("---------------------\n");
    }

}
