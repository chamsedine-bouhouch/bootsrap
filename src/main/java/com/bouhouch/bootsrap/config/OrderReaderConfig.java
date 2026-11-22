package com.bouhouch.bootsrap.config;

import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.bouhouch.bootsrap.model.OrderInput;
import com.bouhouch.bootsrap.model.OrderStatus;

import java.time.LocalDate;
 
@Configuration 
public class OrderReaderConfig {
    @Bean
    FlatFileItemReader<OrderInput> orderReader() {
         return new FlatFileItemReaderBuilder<OrderInput>()
                .name("orderReader")
                .resource(new ClassPathResource("commandes_spring_batch.csv"))
                .delimited()
                .names("order_id", "customer_id", "amount", "status", "order_date")
                .linesToSkip(1)
                .fieldSetMapper(fieldSet -> {
                    OrderInput order = new OrderInput();
                    order.setOrder_id(fieldSet.readLong("order_id"));
                    order.setCustomer_id(fieldSet.readString("customer_id"));
                    order.setAmount(fieldSet.readDouble("amount"));
                    order.setStatus(OrderStatus.valueOf(fieldSet.readString("status")));

                    try {
                        order.setOrder_date(LocalDate.parse(fieldSet.readString("order_date")));
                    } catch (RuntimeException exception) {
                        order.setOrder_date(null);
                    }
                    return order;
                })
                .build();
    } 
    
}
