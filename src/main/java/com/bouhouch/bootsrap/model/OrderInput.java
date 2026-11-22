package com.bouhouch.bootsrap.model;

import java.time.LocalDate;

public class OrderInput {
    long order_id;
    String customer_id;
    double amount;
    OrderStatus status;
    LocalDate order_date;

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    @Override 
    public String toString() {
        return "OrderRecord{" +
                "order_id=" + order_id +
                ", customer_id=" + customer_id +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", order_date=" + order_date +
                '}';
    }
}
