package com.bouhouch.bootsrap.config;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bouhouch.bootsrap.model.OrderInput;

@Configuration
public class OrderWriterConfig {

    @Bean
    ItemWriter<OrderInput> orderWriter() {
        return (Chunk<? extends OrderInput> chunk) -> {
            System.out.println("\n--- Writing chunk ---");

            for (OrderInput order : chunk.getItems()) {
                System.out.println( "Saving order: " + order.toString());
            }

            System.out.println("---------------------\n");
        };
    }
}
