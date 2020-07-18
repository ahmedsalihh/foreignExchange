package com.ahmedsalihh.foreignexchange.model;

public class Converted {
    private float amount;
    private Long transactionId;

    public Converted() {
    }

    public Converted(float amount, Long transactionId) {
        this.amount = amount;
        this.transactionId = transactionId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
