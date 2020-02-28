package com.example.electricassistant.data.model;

import java.sql.Timestamp;

public class PaymentItem {
    private Timestamp time;
    private Double money;
    private Double amount;

    public PaymentItem(Timestamp time, Double money, Double amount) {
        this.time = time;
        this.money = money;
        this.amount = amount;
    }

    public Timestamp getTime() {
        return time;
    }

    public Double getMoney() {
        return money;
    }

    public Double getAmount() {
        return amount;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
