package com.ahmedsalihh.foreignexchange.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
public class Exchange {
    private long id;
    private String fromCurrency;
    private String toCurrency;
    private float quantity;
    private float totalCalculatedAmount;
    private Date transactionDate;

    public Exchange() {
    }

    public Exchange(String fromCurrency, String toCurrency, float quantity, float totalCalculatedAmount, Date transactionDate) {
        super();
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.quantity = quantity;
        this.totalCalculatedAmount = totalCalculatedAmount;
        this.transactionDate = transactionDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    @Column(nullable = false)
    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    @Column(nullable = false)
    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Column(nullable = false)
    public float getTotalCalculatedAmount() {
        return totalCalculatedAmount;
    }

    public void setTotalCalculatedAmount(float totalCalculatedAmount) {
        this.totalCalculatedAmount = totalCalculatedAmount;
    }

    @Column(nullable = false)
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
